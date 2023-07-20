package hmfb.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hmfb.handler.FirmServerHandler;
import hmfb.handler.StdFirmServerHandler;
import hmfb.handler.VirtualServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class NettyServerConfig {
	
	/*
	 * 표준 펌뱅킹 포트
	 */
	@Value("${hmfb.socket.server.port-Stdfirm}")
    private int port_Stdfirm;
	
	@Value("${hmfb.socket.server.port-firm}")
    private int port_firm;
    
    @Value("${hmfb.socket.server.port-virtual}")
    private int port_virtual;
    
    /*
	 * 표준 펌뱅킹 handler
	 */
    @Autowired
    StdFirmServerHandler StdfirmServerHandler;
    
    @Autowired
    FirmServerHandler firmServerHandler;
    
    @Autowired
    VirtualServerHandler virtualServerHandler;
    
    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;
    
    /*
	 * 표준 펌뱅킹 StdfirmServer
	 */
    @Bean
    public NettyServer StdfirmServer() {
    	return new NettyServer(port_Stdfirm, StdfirmServerHandler);    	
    }
    
    @Bean
    public NettyServer firmServer() {
    	return new NettyServer(port_firm, firmServerHandler);    	
    }
    
    @Bean
    public NettyServer virtualActServer() {
    	return new NettyServer(port_virtual, virtualServerHandler);    	
    }
    
	public class NettyServer {
		
		private int port ;
		private ChannelInboundHandlerAdapter handler;
		private ChannelFuture chFuture = null;
		
		public NettyServer(int port, ChannelInboundHandlerAdapter handler) {
			this.port = port;
			this.handler = handler;
		}
		/**
		 * 
		 * @return
		 */
		public void start() {
			
		    bossGroup = new NioEventLoopGroup(1);
		    workerGroup = new NioEventLoopGroup();
		    	    
		    try {
		        ServerBootstrap b = new ServerBootstrap();
		        b.group(bossGroup, workerGroup)
		         .channel(NioServerSocketChannel.class)
		         .childHandler(new ChannelInitializer<SocketChannel>() {
		            @Override
		            public void initChannel(SocketChannel ch) {
		                ChannelPipeline p = ch.pipeline();
		                p.addLast(new LoggingHandler(LogLevel.DEBUG));
		                p.addLast(handler);
		            }
		        });
		        		        
		        chFuture = b.bind(port).sync();
		        chFuture.channel().closeFuture().sync();
		        
		    } catch (Exception e) {	    	
		    	log.error("", e);
		    	//shutdown();  // 처리 중 exception 발생하더라도 shutdown이 되지 않도록 막음
		    	throw new RuntimeException(e);
		    }
		}
				
		public void shutdown() {
			log.info("closing channel..");
			if (chFuture != null) {
				chFuture.channel().close();
			}
	    	log.info("netty server gracefully shutdown..");
	        if (workerGroup != null) workerGroup.shutdownGracefully();
	        if (bossGroup != null) bossGroup.shutdownGracefully();
		}
	}
	
}
