package hmfb.web.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.dto.InstDto;
import hmfb.core.dto.LinkDto;
import hmfb.core.exception.HmfbException;
import hmfb.core.utils.DateUtil;
import hmfb.web.info.manage.InfoAdminDAO;

@Service
public class InfoAdminService {
	
	@Autowired
	private InfoAdminDAO dao;

	/* 기관정보관리 */
	public int getInstInfoTotCnt(InstDto dto) {
		
		return dao.selectOne("instInfo.getInstInfoTotCnt", dto);
	}
	
	public List<InstDto> getInstInfoList(InstDto dto) {
		
		return dao.selectList("instInfo.getListInstInfo", dto);
	}
	
	public int insertInstInfo(InstDto input) throws HmfbException  {
    	String dttm = DateUtil.getCurrentDttm();
		input.setRegDt(dttm.substring(0, 8));
		input.setRegTm(dttm.substring(8));
		return dao.insert("instInfo.insertInstInfo", input);
	}
	
	public InstDto selectInstInfo(String input) {
		return dao.selectOne("instInfo.selectInstInfo", input);
	}
	
	public int updateInstInfo(InstDto input) {
    	String dttm = DateUtil.getCurrentDttm();
		input.setUpdDt(dttm.substring(0, 8));
		input.setUpdTm(dttm.substring(8));
		return dao.update("instInfo.updateInstInfo", input);
	}
	
	public int deleteInstInfo(InstDto input) {
		return dao.delete("instInfo.deleteInstInfo", input);
	}
	
	/* 연계정보관리 */
	public int getLinkInfoTotCnt(LinkDto dto) {
		
		return dao.selectOne("linkInfo.getLinkInfoTotCnt", dto);
	}
	
	public List<LinkDto> getLinkInfoList(LinkDto dto) {
		
		return dao.selectList("linkInfo.getListLinkInfo", dto);
	}
	
	public int insertLinkInfo(LinkDto input) throws HmfbException  {
    	String dttm = DateUtil.getCurrentDttm();
		input.setRegDt(dttm.substring(0, 8));
		input.setRegTm(dttm.substring(8));
		return dao.insert("linkInfo.insertLinkInfo", input);
	}
	
	public LinkDto selectLinkInfo(String input) {
		return dao.selectOne("linkInfo.selectLinkInfo", input);
	}
	
	public int updateLinkInfo(LinkDto input) {
    	String dttm = DateUtil.getCurrentDttm();
		input.setUpdDt(dttm.substring(0, 8));
		input.setUpdTm(dttm.substring(8));
		return dao.update("linkInfo.updateLinkInfo", input);
	}
	
	public int deleteLinkInfo(LinkDto input) {
		return dao.delete("linkInfo.deleteLinkInfo", input);
	}
}
