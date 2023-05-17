package hmfb.framework.batch;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.ExtractorLineAggregator;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.BatchJobDTO;
import hmfb.core.dto.FileIOMetaDTO;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.constant.BatchConstants;
import hmfb.framework.batch.prop.BatchProps;
import hmfb.framework.batch.task.ChunkBatchItemProcessor;
import hmfb.framework.batch.task.DummyItemWriter;

@Configuration
public class ChunkFlowComposer {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Autowired
	BatchProps batchProps;
	
	IChunkBatchJob batchJob = null;
	BatchJobDTO batchJobDto = null;
	
	public Step compose(BatchJobDTO batchJobDto) {

		this.batchJobDto = batchJobDto;
		/*
		 * PagingItemReader 의 page size 와 chunk size 는 다른 의미.
		 * page size  : 한번에 조회할 item size
		 * chunk size : tx size. 
		 * 둘의 값을 일치시켜야 한다. 1000 이내로 가자. jpa 일 경우 두 값이 다르면 persistance 가 꺠진다.
		 * 참고 : step execution 에 있는 commitCount 는 커밋을 실행한 횟수이다. 
		 */
		SimpleStepBuilder chunkStepBuilder = stepBuilderFactory.get(batchJobDto.getJobCode()+".process").chunk( batchJobDto.getCommitCount());
//		예외 skip 처리 
		if (StringUtils.equals(batchJobDto.getExceptionPolicy(), BatchConstants.SKIP_YES)) {
			chunkStepBuilder = chunkStepBuilder.faultTolerant().skipLimit(Integer.MAX_VALUE).skip(Throwable.class);
		}
//		예외 발생 지점 전까지 commit 수행이 필요한데 아래 noRollback 은 아님.
		/*
		 * if (StringUtils.equals(batchJobDto.getExceptionPolicy(),
		 * UncConstants.SKIP_ABORT)) { chunkStepBuilder =
		 * chunkStepBuilder.faultTolerant().noRollback(Throwable.class); }
		 */
		String jobType = batchJobDto.getJobType();
		String readerType = StringUtils.substring(jobType, 0, 1);
		String writerType = StringUtils.substring(jobType, StringUtils.length(jobType)-1);
		
//		File to DB : replaceFileName() checkedFile()
//		DB to File : setQueryConditions() setupOutFile())			
//		DB to DB : setQueryConditions())
		ItemReader reader = null;
		if (StringUtils.equals(readerType, BatchConstants.JOB_IO_TYPE_FILE)) {
			reader = fileItemReader(null);
		} else if (StringUtils.equals(readerType, BatchConstants.JOB_IO_TYPE_DB)) {
			reader = dbItemReader(null);
		} else {
			throw new RuntimeException(new HmfbException(ErrorCode.E117, 
					String.format("배치유형 구분코드를 확인하세요. 입력된 구분코드[%s]", jobType)));
		}

		ItemWriter writer = null;
		if (StringUtils.isBlank(batchJobDto.getOutputDataSelector())) {
			writer = new DummyItemWriter();
		} else {
			if (StringUtils.equals(writerType, BatchConstants.JOB_IO_TYPE_FILE)) {
				writer = fileItemWriter(null);
			} else if (StringUtils.equals(writerType, BatchConstants.JOB_IO_TYPE_DB)) {
				writer = dbItemWriter(null);
			} else {
				throw new RuntimeException(new HmfbException(ErrorCode.E117, 
						String.format("배치유형 구분코드를 확인하세요. 입력된 구분코드[%s]", jobType)));
			}
		}
		return chunkStepBuilder
					.reader(reader)
					.processor(new ChunkBatchItemProcessor())
					.writer(writer)
					.build();		
	}
	
	private Step partiitionStep(Step target, int partitionCount) {

//		org.springframework.batch.core.partition.support.SimplePartitioner
		return stepBuilderFactory.get("masterStep")
				.partitioner(target.getName(), new HashPartitioner())
				.step(target)
				.gridSize(partitionCount)
				.taskExecutor(new SimpleAsyncTaskExecutor())
				.build();
	}
	
	/**
	 * 조회 범위 또는 key 를 각 파티션의 executionContext 에 저장하고 
	 * itemReader 에서 조회 시 조회 조건으로 사용하는 메카니즘이네.. 음...원하는 건 마스터가 worker 에 배분이었는데...
	 * @author KDK
	 *
	 */
	class HashPartitioner implements Partitioner {

		@Override
		public Map<String, ExecutionContext> partition(int gridSize) {
			
			Map<String, ExecutionContext> map = new HashMap<String, ExecutionContext>();
			for (int idx=0; idx<gridSize; idx++) {
				ExecutionContext executionContext = new ExecutionContext();
//				ExecutionContext 에 고유 파티션 번호를 설정한 후 ItemReader 에서 해당 파티션 번호를 조회 조건으로 
//				쿼리를 실행하면 쓰레드 간 경합을 막을 수 있다.
				executionContext.putInt("partitionNo", idx);
				map.put("partition"+idx, executionContext);
			}
			return map;
		}
		/**
		 * uniqueKey (예를들면 계좌번호) fmf 
		 * @param uniqueKey
		 * @param gridSize
		 * @return
		 */
		private String getWorkerNo(String uniqueKey, int gridSize) {
			
			String workerNo = "01";
			
			int no = Math.abs(uniqueKey.hashCode()) % gridSize;
			workerNo = StringUtils.leftPad(String.valueOf(no+1), 2, '0');
			
			return workerNo;
		}
	}
	
	@Bean
	@StepScope
	/*
	 * StepScope 를 이용하면 해당 리턴 타입의 proxy 객체를 만들기 때문에 ItemReader 를 리턴하면
	 * read() 만 갖는 프록시가 생성되어 ItemStream 의 open, close 등이 없다. 다음과 같은 오류 발생.
	 * org.springframework.batch.item.ReaderNotOpenException: Reader must be open before it can be read.
	 * 조치 : 구체적인 타입을 명시. FlatFileItemReader
	 */
	FlatFileItemReader fileItemReader(@Value("#{jobExecutionContext[BatchJobContext]}") BatchJobContext ctx) {
		
		FileIOMetaDTO ioMeta = ctx.getIoMeta();
		if (ioMeta == null) {
			throw new RuntimeException(new HmfbException(ErrorCode.E819, 
					String.format("배치 프로그램을 확인하세요. 프로그램명[%s]", ctx.getProgramName())));
		}
		String filePath = ctx.getInputDataSelector();
		
		if (Files.notExists(Paths.get(filePath))) {
			throw new RuntimeException(new HmfbException(ErrorCode.E820, 
					String.format("배치 기본 정보의 입력데이터식별자의 값을 확인하세요. 파일경로[%s]", filePath)));
		}
		FlatFileItemReader fileReader = new FlatFileItemReader<>();
		fileReader.setResource(new FileSystemResource(filePath));
		   
//		라인 매퍼 설정
		DefaultLineMapper lineMapper = new DefaultLineMapper<>();
		
		LineTokenizer tokenizer = null;
		if (StringUtils.equals(ioMeta.getFileType(), BatchConstants.FILE_TYPE_DELIM)) {
			tokenizer = delimitedLineTokenizer(ioMeta);
		} else if (StringUtils.equals(ioMeta.getFileType(), BatchConstants.FILE_TYPE_FIXED)) {
			tokenizer = fixedLengthTokenizer(ioMeta);
		} else {
			throw new RuntimeException(new HmfbException(ErrorCode.E117, 
					String.format("배치 프로그램에서 파일유형 구분코드를 확인하세요. 입력된 구분코드[%s]", ioMeta.getFileType())));
		}
		lineMapper.setLineTokenizer(tokenizer);
		
//		저장할 타입 설정
		Class inputClazz = null;
		try {
			inputClazz = Class.forName(ioMeta.getInputClassName());
		} catch (ClassNotFoundException e) {
//			입력 Class 가 존재하지 않음 
			throw new RuntimeException(e);
		} 
		BeanWrapperFieldSetMapper fsMapper = new BeanWrapperFieldSetMapper<>();
		fsMapper.setTargetType(inputClazz);
		
		lineMapper.setFieldSetMapper(fsMapper);		
		fileReader.setLineMapper(lineMapper);
		
		fileReader.setEncoding(ioMeta.getFileEncoding()); 
		// csv 이고 첫번째 행이 헤더 라인이면 skip 
		fileReader.setLinesToSkip(ioMeta.getHeaderRows());
		
//		TODO : fileReader.getlinecount 가 없네. '\n' 을 세야 하나.
		
		return fileReader;		
	}
		
	private DelimitedLineTokenizer delimitedLineTokenizer(FileIOMetaDTO ioMeta) {
	
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(ioMeta.getDelimeter());
		
		tokenizer.setNames(ioMeta.getInputAttributeNames());
		
		return tokenizer;
	}
	
	private FixedLengthTokenizer fixedLengthTokenizer(FileIOMetaDTO ioMeta) {
		
		FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();

		tokenizer.setNames(ioMeta.getInputAttributeNames()); 
		int[][] iRanges = ioMeta.getInputFixedLenInfo();
		
		Range[] ranges = new Range[iRanges.length];
		for (int i=0; i<iRanges.length; i++) {
			ranges[i] = new Range(iRanges[i][0],iRanges[i][1]);
		}
		tokenizer.setColumns(ranges);
		
		return tokenizer;
	}
	
	@Bean
	@StepScope
	FlatFileItemWriter fileItemWriter(@Value("#{jobExecutionContext[BatchJobContext]}") BatchJobContext ctx) {
		
		FileIOMetaDTO ioMeta = ctx.getIoMeta();
		if (ioMeta == null) {
			throw new RuntimeException(new HmfbException(ErrorCode.E819, 
					String.format("배치 프로그램을 확인하세요. 프로그램명[%s]", ctx.getProgramName())));
		}
		String filePath = ctx.getOutputDataSelector();
		
		FlatFileItemWriter fileWriter = new FlatFileItemWriter();
		fileWriter.setEncoding(ioMeta.getFileEncoding());
		fileWriter.setResource(new FileSystemResource(filePath));

		ExtractorLineAggregator aggregator = null;
		if (StringUtils.equals(ioMeta.getFileType(), BatchConstants.FILE_TYPE_DELIM)) {
			DelimitedLineAggregator delimAggregator = new DelimitedLineAggregator();
			delimAggregator.setDelimiter(ioMeta.getDelimeter()); 
			aggregator = delimAggregator;
		} else if (StringUtils.equals(ioMeta.getFileType(), BatchConstants.FILE_TYPE_FIXED)) {
		    FormatterLineAggregator lineAggregator = new FormatterLineAggregator();
		    lineAggregator.setFormat(ioMeta.getOutputLineFormat());
		    aggregator = lineAggregator;
		} else {
			throw new RuntimeException(new HmfbException(ErrorCode.E117, 
					String.format("배치 프로그램에서 파일유형 구분코드를 확인하세요. 입력된 구분코드[%s]", ioMeta.getFileType())));
		}
		
		BeanWrapperFieldExtractor fieldExtractor = new BeanWrapperFieldExtractor();
		fieldExtractor.setNames(ioMeta.getOutputAttributeNames());
		aggregator.setFieldExtractor(fieldExtractor);
		
		fileWriter.setLineAggregator(aggregator);
		
		return fileWriter;
	}
	
	@Bean
	@StepScope
	MyBatisPagingItemReader dbItemReader(@Value("#{jobExecutionContext[BatchJobContext]}") BatchJobContext ctx) {
		
		String sqlId = ctx.getInputDataSelector();
		return new MyBatisPagingItemReaderBuilder()
        .sqlSessionFactory(sqlSessionFactory)
        .queryId(sqlId)
        .parameterValues(ctx.getConditions())
        .pageSize(1000)	// 1000 건씩 페이징 처리. selectList 로 처리되도 괜찮음 
        .build();
	}
	
	/*
	 * MyBatisBatchItemWriter 은 ItemProcessor 에서 가공한 item 을 writer 의 param 으로 넘긴다.
	 */
	@Bean
	@StepScope
	MyBatisBatchItemWriter dbItemWriter(@Value("#{jobExecutionContext[BatchJobContext]}") BatchJobContext ctx) {
		
		String sqlId = ctx.getOutputDataSelector();
		return new MyBatisBatchItemWriterBuilder().sqlSessionFactory(sqlSessionFactory)
				.statementId(sqlId)
				.build();
	}
	
}
