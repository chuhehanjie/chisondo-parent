package com.chisondo.iot.device.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class DeviceDelimiterDecoder extends DelimiterBasedFrameDecoder {

    public DeviceDelimiterDecoder(int maxFrameLength, ByteBuf... delimiters) {
        super(maxFrameLength, delimiters);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        Object decoded = super.decode(ctx, buffer);
        if (decoded instanceof ByteBuf ) {
            ByteBuf msg = (ByteBuf) decoded;
            if (msg.maxCapacity() == 0) {
                return null;
            }
        }
        return decoded;
    }
}
