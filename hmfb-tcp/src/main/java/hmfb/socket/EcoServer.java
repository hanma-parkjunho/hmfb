package hmfb.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import hmfb.handler.FirmServerHandler;
import hmfb.handler.VirtualServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EcoServer {
	
    @Value("${hmfb.socket.server.port-firm}")
    private int port_firm;
    
    @Value("${hmfb.socket.server.port-virtual}")
    private int port_virtual;
    
    @Autowired
    FirmServerHandler firmServerHandler;
    
    @Autowired
    VirtualServerHandler virtualServerHandler;
	
	public void start() {
		firmStart();
		
		//virtualStart();
	}
	
	private void firmStart() {
	    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	    EventLoopGroup workerGroup = new NioEventLoopGroup();
	    	    
	    
	    try {
	        ServerBootstrap b = new ServerBootstrap();
	        b.group(bossGroup, workerGroup)
	         .channel(NioServerSocketChannel.class)
	         .childHandler(new ChannelInitializer<SocketChannel>() {
	            @Override
	            public void initChannel(SocketChannel ch) {
	                ChannelPipeline p = ch.pipeline();
	                p.addLast(new LoggingHandler(LogLevel.DEBUG));
                	//p.addLast(new FirmServerHandler());
	                p.addLast(firmServerHandler);
	            }
	        });
	        
	        //b.bind(port_firm).sync();
	        
	        
	        ServerBootstrap b1 = new ServerBootstrap();
	        b1.group(bossGroup, workerGroup)
	         .channel(NioServerSocketChannel.class)
	         .childHandler(new ChannelInitializer<SocketChannel>() {
	            @Override
	            public void initChannel(SocketChannel ch) {
	                ChannelPipeline p = ch.pipeline();
	                p.addLast(new LoggingHandler(LogLevel.DEBUG));
                	//p.addLast(new VirtualServerHandler());
                	p.addLast(virtualServerHandler);
	            }
	        });
	
	        //b1.bind(port_firm).sync();
	        

	        ChannelFuture f1;
	        ChannelFuture f2;

        	
	        // 펌뱅킹 port 바인딩
	        f1 = b.bind(port_firm).sync();
        	// 가상계좌 port 바인딩
	        f2 = b1.bind(port_virtual).sync();
	       
	        // 아래 closeFutuer는 하나만 실행됨.
	        // TODO : 두개 다 실행될 수 있는 방법 확인 필요. (찾아봐도 방법이 없음.)
        	f1.channel().closeFuture().sync();
	        f2.channel().closeFuture().sync();
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    finally {
	        workerGroup.shutdownGracefully();
	        bossGroup.shutdownGracefully();
	    }
	}

}
