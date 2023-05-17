package hmfb.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.F0600100Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0600200Dto;
import hmfb.core.service.BaseService;
import hmfb.db.TcpDao;
/**
 *	거래명세통지정보 수신
 */
public class F0600100Service implements BaseService {
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    @Override
    public void process(FirmReturnDto dto) {
    	F0600100Dto receiveDto = (F0600100Dto)dto.getRtnObj();
    	T0600200Dto input = new T0600200Dto();
		input.setRegDate(dto.getCommonDto().getTranDt());
		input.setTelemsgNo(dto.getCommonDto().getTlgmSeqNo());
		input.setDelngDe(receiveDto.getDelngDe());                    //거래일자
		input.setDelngSn(receiveDto.getDelngSn());                    //거래일련번호
		input.setAcnutNo(receiveDto.getAcnutNo());                    //거래계좌번호
		input.setSeCode(receiveDto.getSeCode());                      //거래구분코드
		input.setDelngAmount(new BigDecimal(receiveDto.getDelngAmount()));            //거래금액
		input.setBlceSmbol(receiveDto.getBlceSmbol());                //잔액부호
		input.setBlce(new BigDecimal(receiveDto.getBlce()));                          //계좌잔액
		input.setDelngSumry(receiveDto.getDelngSumry());              //거래적요
		input.setDelngTime(receiveDto.getDelngTime());                //거래시간
		input.setBankCode(receiveDto.getBankCode());                  //거래은행코드
		input.setDelngBhfCode(receiveDto.getDelngBhfCode());          //거래점코드
		input.setCmsCode(receiveDto.getCmsCode());                    //입금인 번호(CMS 코드)
		input.setCheckBilNo(receiveDto.getCheckBilNo());              //수표/어음번호
		input.setCsrccAmount(new BigDecimal(receiveDto.getCsrccAmount()));            //자기앞수표 금액
		input.setPrsnlchkAmount(new BigDecimal(receiveDto.getPrsnlchkAmount()));      //가계수표 금액
		input.setGenchkAmount(new BigDecimal(receiveDto.getGenchkAmount()));          //일반수표 금액
		input.setDelngMediaTy(receiveDto.getDelngMediaTy());          //거래매체유형
		input.setRspnsCode(dto.getCommonDto().getRecvCode());
		input.setRspnsMssage("");
		input.setSendYn("Y");
		TcpDao.getDao().insert("T0600100.insertT0600100", input);
		
    	dto.getCommonDto().setSndRcvDvcd("S");
        dto.getCommonDto().setRecvCode("0000");
        
        CLOG.info("Service 호출됨..!"+receiveDto.getMessage());
    }
}
