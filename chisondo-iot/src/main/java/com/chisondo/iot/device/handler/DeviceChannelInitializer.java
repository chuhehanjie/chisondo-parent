/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chisondo.iot.device.handler;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

/**
 * Channel Initializer
 * @author zhong.ding
 */
@Slf4j
@Component
@Qualifier("deviceChannelInitializer")
public class DeviceChannelInitializer extends ChannelInitializer<SocketChannel> {
    private SslContext sslCtx;

    public DeviceChannelInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    public DeviceChannelInitializer() throws SSLException, CertificateException {
        /*SelfSignedCertificate ssc = new SelfSignedCertificate();
        SslContext sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        this.sslCtx = sslCtx;*/
    }

    //心跳
    private static final int READ_IDEL_TIME_OUT = 170; // 读超时
    private static final int WRITE_IDEL_TIME_OUT = 216;// 写超时
    private static final int ALL_IDEL_TIME_OUT = 300; // 所有超时

    private static final StringDecoder DECODER = new DeviceServerDecoder();
    private static final StringEncoder ENCODER = new DeviceServerEncoder();

    @Autowired
    @Qualifier("deviceServerHandler")
    private ChannelInboundHandlerAdapter deviceServerHandler;


    @Autowired
    @Qualifier("msglength")
    private Integer msglength;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //pipeline.addLast(sslCtx.newHandler(socketChannel.alloc()));
        pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
        pipeline.addLast(new HeartbeatServerHandler());

        // Add the text line codec combination first,
        pipeline.addLast(new DelimiterBasedFrameDecoder(msglength, Delimiters.lineDelimiter()));//tcp 分包,定义分隔符号// TODO: 16/5/9
        // the encoder and decoder are static as these are sharable
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        pipeline.addLast(this.deviceServerHandler);
        log.debug("SimpleChatClient:{} 连接上！", socketChannel.remoteAddress());

    }
}
