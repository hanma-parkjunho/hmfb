package hmfb.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDecoder extends ByteToMessageDecoder {
    private int DATA_LENGTH = 23;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 코덱의 Decode역할 : Data를 여러번 나눠서 받을 때 사용..
    	
    	/*
    	if (in.readableBytes() < DATA_LENGTH) {
            return;
        }
        */

        out.add(in.readBytes(DATA_LENGTH));
    	//out.add(in.readInt());
    }
}