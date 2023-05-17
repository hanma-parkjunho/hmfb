package hmfb.handler;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hmfb.core.dto.FirmCommonDto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.TcpHeader;
import hmfb.core.log.service.LogService;
import hmfb.core.service.BaseMessage;
import hmfb.core.service.BaseService;
import hmfb.core.utils.MessageParserHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class FirmServerHandler extends ChannelInboundHandlerAdapter {

	@Autowired
	LogService logService;
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            // 전송메시지:0100DHRTCPIN                 FIRMID12R0880100100000001202301031300000000                                                                                                                                                   
            // 
            ByteBuf buff = (ByteBuf) msg;
            
            // TcpHeader Parsing
            ByteBuf tcpHeaderBuf = Unpooled.buffer();
            buff.getBytes(0, tcpHeaderBuf, 20);
            String strTcpHeader = tcpHeaderBuf.toString(Charset.defaultCharset());
            TcpHeader tcpDto = new TcpHeader();
            MessageParserHelper.parseMessage(strTcpHeader, tcpDto);

            // 공통Dto Parsing
            ByteBuf commonBuf = Unpooled.buffer();
            buff.getBytes(20, commonBuf, 100);
            
            FirmCommonDto fDto = new FirmCommonDto();
            MessageParserHelper.parseMessage(commonBuf.toString(Charset.forName("euc-kr")), fDto);
            
            // 본문 Parsing
            ByteBuf teleBuf = Unpooled.buffer(buff.readableBytes());
            buff.getBytes(120, teleBuf, buff.readableBytes());

            // 전문번호
            String telegNum = MessageParserHelper.fillBeforeZeroSpace(fDto.getTlgmCode(), 4) + MessageParserHelper.fillBeforeZeroSpace(fDto.getBizCode(), 3);
            
            // dto
            Class<?> targetDto = Class.forName("hmfb.core.dto.F"+telegNum+"Dto");
            BaseMessage dto = (BaseMessage)targetDto.newInstance();
            
            // Service
            Class<?> targetService = Class.forName("hmfb.service.F"+telegNum+"Service");
            BaseService service = (BaseService)targetService.newInstance();

            /*
            ByteBuf bodyBuff = Unpooled.buffer();
            buff.getBytes(20, bodyBuff, buff.readableBytes());
            */
            
            String strBody = teleBuf.toString(Charset.defaultCharset());
            
            MessageParserHelper.parseMessage(strBody, dto);
            
            // 서비스 호출
            //BaseService baseService = (BaseService) service;
            //baseService.process((BaseMessage)dto);
            FirmReturnDto returnDto = new FirmReturnDto();
            returnDto.setCommonDto(fDto);
            returnDto.setRtnObj(dto);
            
            // TCP 거래로그 INPUT
            //LogService logService = new LogService();
            logService.log(telegNum + "_IN", returnDto);
            service.process(returnDto);
            
            
            StringBuffer returnSb = new StringBuffer();
            returnSb.append(tcpDto.getMessage());
            returnSb.append(returnDto.getCommonDto().getMessage());
            returnSb.append(returnDto.getRtnObj().getMessage());

            ByteBuf rtnBuf = Unpooled.buffer(buff.readableBytes());
            rtnBuf.writeBytes(returnSb.toString().getBytes("euc-kr"));
            
            // TCP 거래로그 OUTPUT
            logService.log(telegNum + "_OUT", returnDto);
            
            final ChannelFuture f = ctx.writeAndFlush(rtnBuf);
            f.addListener(ChannelFutureListener.CLOSE);       
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //ctx.flush();
    	ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
