package hmfb.web.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import hmfb.core.exception.HmfbException;
import hmfb.web.server.prop.ServerInfo;
import hmfb.web.server.prop.ServerInfoProperties;
import hmfb.web.server.service.ServerServiceImpl;

/**

 * @FileName : ServerController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : ServerController 클레스 

 * @변경이력 :

 */

@Controller
public class ServerController {
	
	@Autowired
	private ServerInfoProperties serverProps;
	
	@Autowired
	private ServerServiceImpl serverService;
	/**
    
     * @Method Name : userInfo
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자관리 - 사용자정보 페이지 호출
   
     * @변경이력 : 
   
     */
	@RequestMapping("/server/serverInfo")
    public String serverInfo(Model model) throws HmfbException {
		List<ServerInfo> infos = serverProps.getServerInfoList();
		infos.forEach(info->info.setStatus(serverService.doHealthCheck(info)));
		model.addAttribute("serverList", infos);
        return "server/serverInfo";
    }
	
}
