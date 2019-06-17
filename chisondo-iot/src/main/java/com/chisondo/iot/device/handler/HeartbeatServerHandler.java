package com.chisondo.iot.device.handler;

import com.chisondo.iot.common.redis.RedisUtils;
import com.chisondo.iot.common.utils.SpringContextUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 说明：心跳服务器处理器
 *
 */
@Slf4j
public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

	// Return a unreleasable view on the given ByteBuf
	// which will just ignore release and retain calls.
	private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
			.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat\n\r",
					CharsetUtil.UTF_8));

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {

		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			String type = "";
			if (event.state() == IdleState.READER_IDLE) {
				log.error("设备在指定时间内未上报请求，自动离线，设备IP = [{}]", ctx.channel().remoteAddress());
				throw new RuntimeException("设备未在指定时间内上报设备状态");
				// 超过指定时间没有上报设备状态，则认为设备已经掉线
				/*Channel channel = ctx.channel();
				String deviceId = DevTcpChannelManager.removeByChannel(channel);
				globalDevChannels.remove(channel);
				this.getRedisUtils().updateStatus4Dev(deviceId);
				log.error("从 redis 中删除设备[{}]", deviceId);*/
			} else if (event.state() == IdleState.WRITER_IDLE) {
				type = "write idle";
			} else if (event.state() == IdleState.ALL_IDLE) {
				type = "all idle";
			}

			ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(
					ChannelFutureListener.CLOSE_ON_FAILURE);
			log.error( ctx.channel().remoteAddress()+"超时类型：" + type);
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	private RedisUtils getRedisUtils() {
		return (RedisUtils) SpringContextUtils.getBean("redisUtils");
	}
}
