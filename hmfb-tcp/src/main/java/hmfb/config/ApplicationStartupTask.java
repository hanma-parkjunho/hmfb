package hmfb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import hmfb.socket.NettyServerConfig.NettyServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartupTask implements ApplicationListener<ApplicationReadyEvent>{
//    private final NettyServerSocket nettyServerSocket;
//    private final EcoServer ecoServer;
    
    @Autowired
    @Qualifier("firmServer")
    NettyServer firmServer;
    @Autowired
    @Qualifier("virtualActServer")
    NettyServer virtualActServer;
    
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// TODO Auto-generated method stub
		//nettyServerSocket.start();
//		ecoServer.start();
		
		new Thread(new Runnable() {			
			@Override
			public void run() {
				firmServer.start();
			}
		}).start();
		
		new Thread(new Runnable() {			
			@Override
			public void run() {
				virtualActServer.start();
			}
		}).start();
		
	} 
}
