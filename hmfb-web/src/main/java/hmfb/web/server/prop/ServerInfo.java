package hmfb.web.server.prop;

import lombok.Data;

@Data
public class ServerInfo {
	
	private String sys;
	private String name;
	private String title;
	private String bootJarPath;
	private String ip;
	private String port;
	private String remote;
	private String status;
}
