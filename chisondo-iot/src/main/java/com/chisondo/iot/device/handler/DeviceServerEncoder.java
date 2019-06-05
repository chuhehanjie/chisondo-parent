package com.chisondo.iot.device.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.List;

@Slf4j
public class DeviceServerEncoder extends StringEncoder {

    public DeviceServerEncoder(Charset charset) {
        super(charset);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
        log.error("encode msg 发送请求到设备 = {}", msg);
        super.encode(ctx, msg, out);
    }
}
