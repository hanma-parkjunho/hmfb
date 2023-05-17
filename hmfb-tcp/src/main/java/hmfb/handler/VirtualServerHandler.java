package hmfb.handler;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hmfb.core.dto.FirmCommonDto;
import hmfb.core.dto.TcpHeader;
import hmfb.core.dto.VReturnDto;
import hmfb.core.dto.VirtualCommonDto;
import hmfb.core.log.service.LogService;
import hmfb.core.service.BaseMessage;
import hmfb.core.service.VBaseService;
import hmfb.core.utils.MessageParserHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class VirtualServerHandler extends ChannelInboundHandlerAdapter {
	@Autowired
	LogService logService;
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            // 전송메시지:0600DHRTCPIN        0600VASINST01  20800110000000001000012320230114000000202301140000000000                              1240000001199999999                                                                   08800000000012023011400110000001                                                                                                                                    
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
            buff.getBytes(20, commonBuf, 200);
            
            VirtualCommonDto vDto = new VirtualCommonDto();
            MessageParserHelper.parseMessage(commonBuf.toString(Charset.forName("euc-kr")), vDto);
            
            // 본문 Parsing
            ByteBuf teleBuf = Unpooled.buffer(buff.readableBytes());
            buff.getBytes(220, teleBuf, buff.readableBytes());

            // 전문번호
            String telegNum = MessageParserHelper.fillBeforeZeroSpace(vDto.getTlgDivCd(), 4) + MessageParserHelper.fillBeforeZeroSpace(vDto.getTranDivCd(), 4);
            
            // dto
            Class<?> targetDto = Class.forName("hmfb.core.dto.V"+telegNum+"Dto");
            BaseMessage dto = (BaseMessage)targetDto.newInstance();
            
            // Service
            Class<?> targetService = Class.forName("hmfb.service.V"+telegNum+"Service");
            VBaseService service = (VBaseService)targetService.newInstance();

            // buffer 사이즈를 지정하지 않으면 default로 256까지만 지정하고 그 이상이면 오류 발생
            //ByteBuf bodyBuff = Unpooled.buffer(buff.readableBytes());
            //buff.getBytes(20, bodyBuff, buff.readableBytes());

            String strBody = teleBuf.toString(Charset.forName("euc-kr"));
            MessageParserHelper.parseMessage(strBody, dto);
            
            // 서비스 호출
            //BaseService baseService = (BaseService) service;
            //baseService.process((BaseMessage)dto);
            VReturnDto rtnDto = new VReturnDto();
            rtnDto.setCommonDto(vDto);
            rtnDto.setRtnObj(dto);
            
            //LogService logService = new LogService();
            // TCP 거래로그 INPUT
            logService.log(telegNum + "_IN", rtnDto);
            
            service.process(rtnDto);
            
            StringBuffer returnSb = new StringBuffer();
            returnSb.append(tcpDto.getMessage());
            returnSb.append(rtnDto.getCommonDto().getMessage());
            returnSb.append(rtnDto.getRtnObj().getMessage());

            ByteBuf rtnBuf = Unpooled.buffer(buff.readableBytes());
            rtnBuf.writeBytes(returnSb.toString().getBytes());

            // TCP 거래로그 OUTPUT
            logService.log(telegNum + "_OUT", rtnDto);
            
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
