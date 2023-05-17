package hmfb.core.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.FirmCommonDto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.TcpHeader;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.core.log.service.LogService;
import hmfb.core.utils.MessageFactoryHelper;
import hmfb.core.utils.MessageParserHelper;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class IntfService {
    // Header에 builder pattern 적용
	
    @Value("${hmfb.firm.id}")
    private String firmId;
    
    @Value("${hmfb.target.server}")
    private String serverIp;
    
    @Value("${hmfb.target.port}")
    private int serverPort; 	
    
	@Autowired
	LogService logService;
    
	
	/** Tcp 전문 송신
     * @param <T>, 전문일련번호
     * @param interfceMapping
     * @param reqObj
     * @return
     * @throws Exception
     */
    public <T> FirmReturnDto sendTcp(InterfaceMapping interfceMapping, T reqObj, String telemsgNo) {
        // 거래로그 INPUT
    	logService.log(interfceMapping.getInterfaceId() + "_IN", reqObj);
    	
    	FirmReturnDto rtnDto = new FirmReturnDto();
        
        // 전체 전문용
        StringBuilder sendMessage = new StringBuilder();
        
        int reqSize = 100; // 공통부 size
        String reqBodyStr = "";
        try {
	        if (reqObj != null) {
	            BaseMessage reqBaseMessage = (BaseMessage)reqObj;
	            reqBodyStr = reqBaseMessage.getMessage();
	            
	            reqSize += reqBodyStr.getBytes("euc-kr").length;	                            
	        }
        } catch (UnsupportedEncodingException e) {
        	throw new RuntimeException(new HmfbException(ErrorCode.E110, e));
        } 
        
        // Tcp헤더생성
        TcpHeader tcpHeader = createTcpHeader(reqSize);
        
        
        // 공통헤더생성
        FirmCommonDto commonDto = createHeaderStr(reqSize, interfceMapping);
        
        //전문일련번호
        commonDto.setTlgmSeqNo(telemsgNo);
        
        sendMessage.append(tcpHeader.getMessage());
        
        sendMessage.append(commonDto.getMessage());
        
        sendMessage.append(reqBodyStr);
  
        // 실제 전송
        try (
                Socket socket = new Socket(serverIp, serverPort);
                // OutputStream output = socket.getOutputStream();
                // PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("euc-kr")), true);
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("euc-kr")));
            )  
        {
            //writer.println(sendMessage.toString().getBytes("euc-kr"));
        	writer.println(sendMessage.toString());
            String line;
            String rtnMessage = "";
            while ((line = reader.readLine()) != null ) {
                rtnMessage = line;
            }


            byte[] headerData = Arrays.copyOfRange(rtnMessage.getBytes("euc-kr"), 20, 120);
            // tcp헤더, 공통헤더 역parsing
            FirmCommonDto rtnHeaderDto = new FirmCommonDto();
            MessageParserHelper.parseMessage(new String(headerData, "euc-kr"), rtnHeaderDto);
            
            if (true) {
                byte[] bodyData = Arrays.copyOfRange(rtnMessage.getBytes("euc-kr"), 120, rtnMessage.getBytes("euc-kr").length);
                String resultMessage = new String(bodyData, "euc-kr");
                BaseMessage rtnObj = parse(resultMessage, interfceMapping);
                
                rtnDto.setRtnObj(rtnObj);
                rtnDto.setCommonDto(rtnHeaderDto);
            } else {
                // 오류처리
            	// TODO : 오류처리
            }
        } catch (UnsupportedEncodingException e) {
        	throw new RuntimeException(new HmfbException(ErrorCode.E110, e));
        } catch (IOException e) {        	
//        	서버 연결 중 오류 
        	throw new RuntimeException(new HmfbException(ErrorCode.E111, e));
        } 
        
        // 거래로그 Output
        logService.log(interfceMapping.getInterfaceId() + "_OUT", rtnDto);
        return rtnDto;
    }
    
    /** Tcp 전문 송신
     * @param <T>
     * @param interfceMapping
     * @param reqObj
     * @return
     * @throws Exception
     */
    public <T> FirmReturnDto sendTcp(InterfaceMapping interfceMapping, T reqObj) {
        // 거래로그 INPUT
    	logService.log(interfceMapping.getInterfaceId() + "_IN", reqObj);
    	
    	FirmReturnDto rtnDto = new FirmReturnDto();
        
        // 전체 전문용
        StringBuilder sendMessage = new StringBuilder();
        
        int reqSize = 100; // 공통부 size
        String reqBodyStr = "";
        try {
	        if (reqObj != null) {
	            BaseMessage reqBaseMessage = (BaseMessage)reqObj;
	            reqBodyStr = reqBaseMessage.getMessage();
	            
	            reqSize += reqBodyStr.getBytes("euc-kr").length;	                            
	        }
        } catch (UnsupportedEncodingException e) {
        	throw new RuntimeException(new HmfbException(ErrorCode.E110, e));
        } 
        
        // Tcp헤더생성
        TcpHeader tcpHeader = createTcpHeader(reqSize);
        
        
        // 공통헤더생성
        FirmCommonDto commonDto = createHeaderStr(reqSize, interfceMapping);
        
        sendMessage.append(tcpHeader.getMessage());
        sendMessage.append(commonDto.getMessage());
        
        sendMessage.append(reqBodyStr);
  
        // 실제 전송
        try (
                Socket socket = new Socket(serverIp, serverPort);
                // OutputStream output = socket.getOutputStream();
                // PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("euc-kr")), true);
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("euc-kr")));
            )  
        {
            //writer.println(sendMessage.toString().getBytes("euc-kr"));
        	writer.println(sendMessage.toString());
            String line;
            String rtnMessage = "";
            while ((line = reader.readLine()) != null ) {
                rtnMessage = line;
            }


            byte[] headerData = Arrays.copyOfRange(rtnMessage.getBytes("euc-kr"), 20, 120);
            // tcp헤더, 공통헤더 역parsing
            FirmCommonDto rtnHeaderDto = new FirmCommonDto();
            MessageParserHelper.parseMessage(new String(headerData, "euc-kr"), rtnHeaderDto);
            
            if (true) {
                byte[] bodyData = Arrays.copyOfRange(rtnMessage.getBytes("euc-kr"), 120, rtnMessage.getBytes("euc-kr").length);
                String resultMessage = new String(bodyData, "euc-kr");
                BaseMessage rtnObj = parse(resultMessage, interfceMapping);
                
                rtnDto.setRtnObj(rtnObj);
                rtnDto.setCommonDto(rtnHeaderDto);
            } else {
                // 오류처리
            	// TODO : 오류처리
            }
        } catch (UnsupportedEncodingException e) {
        	throw new RuntimeException(new HmfbException(ErrorCode.E110, e));
        } catch (IOException e) {        	
//        	서버 연결 중 오류 
        	throw new RuntimeException(new HmfbException(ErrorCode.E111, e));
        } 
        
        // 거래로그 Output
        logService.log(interfceMapping.getInterfaceId() + "_OUT", rtnDto);
        return rtnDto;
    }
    
    // 같은 dto인데 아래 처리가 필요할지??
    /**
     * @param text
     * @param interfaceMappping
     * @return
     */
    public BaseMessage parse(final String text, InterfaceMapping interfaceMappping) {
        final BaseMessage message = MessageFactoryHelper.createMessage(interfaceMappping);
        MessageParserHelper.parseMessage(text, message);
        return message;
    }
    
    /** 전문 공통헤더
     * @param reqSize
     * @param interfceMapping
     * @return
     */
    private FirmCommonDto createHeaderStr(int reqSize, InterfaceMapping interfceMapping) {
    	// TODO : 펌뱅킹ID, 전문일련번호 채번규칙(UNIQ?) IDGEN사용가능여부 판단
		Date nowDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HHmmss");

    	FirmCommonDto firmCommonDto = new FirmCommonDto();
    	firmCommonDto.setDistinCode("");                             // 식별코드
    	firmCommonDto.setFirmId(firmId);                             // 펌뱅킹ID
    	firmCommonDto.setSndRcvDvcd("R");                            // 요청응답구분코드
    	firmCommonDto.setBnkCode("088");                             // 은행코드(신한은행)
    	firmCommonDto.setTlgmCode(interfceMapping.getInterfaceId()); // 전문코드
    	firmCommonDto.setBizCode(interfceMapping.getTranId());       // 업무구분
    	firmCommonDto.setTlgmSeqNo("000001");                        // 전문일련번호
    	firmCommonDto.setTranDt(simpleDateFormat.format(nowDate));   // 거래일자
    	firmCommonDto.setTranTm(simpleDateFormat1.format(nowDate));  // 거래시간
    	return firmCommonDto;
    }
    
    /** TCP 헤더
     * @param reqSize
     * @return
     */
    private TcpHeader createTcpHeader(int reqSize) {
    	TcpHeader header = new TcpHeader();
    	header.setTotLength(Integer.toString(reqSize));
    	header.setBizDvcd("DHRTCP");
    	header.setLegthDvcd("EX");
    	return header;
    }
    
}
