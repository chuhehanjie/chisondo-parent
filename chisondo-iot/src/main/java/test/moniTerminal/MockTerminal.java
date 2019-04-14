package test.moniTerminal;
import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.common.constant.Constant;
import com.chisondo.iot.device.request.DevStatusReportReq;
import com.chisondo.model.http.resp.DevStatusMsgResp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import test.CountHelper;

/**
 * 客户端
 * @author ding.zhong
 * @date 2017年12月29日 
 * @version V1.0
 */
public class MockTerminal {
	private static EventLoopGroup work = new NioEventLoopGroup();

	private static Bootstrap config(){
		//客户这边只需要创建一个线程组
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(work)
		.channel(NioSocketChannel.class)
		.option(ChannelOption.SO_KEEPALIVE, true)
		.option(ChannelOption.SO_SNDBUF, 32*1024)
		.option(ChannelOption.SO_RCVBUF, 32*1024)
		
		//这里方法名与服务端不一样，其他一致
		.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				sc.pipeline().addLast(new StringDecoder());
				sc.pipeline().addLast(new MockTerminalHandler());
			}
		});
		return bootstrap;
	}
	private static void startClient(Bootstrap bootstrap, String deviceId) throws InterruptedException{
		//服务端是绑定到服务器某个端口就行，但是客户端是需要连接到指定ip+指定端口的 因此方法不一样
				ChannelFuture channelFuture=bootstrap.connect("127.0.0.1", 16888).sync();
				int count = 0;
				while (channelFuture.channel().isActive()) {
				    // 只要与服务端连接，就上报设备信息
                    String msg = getDevStatusInfo(deviceId);
                    channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(msg + "\n", CharsetUtil.UTF_8));
                    Thread.sleep(8000);
                    /*if (++count > 0) {
                        break;
                    }*/
                }
				channelFuture.channel().closeFuture().sync();
				work.shutdownGracefully();
	}

    private static String getDevStatusInfo(String deviceId) {
        DevStatusReportReq reportReq = new DevStatusReportReq();
        reportReq.setAction("statuspush");
        reportReq.setActionFlag(Constant.DevStatus.HEART_BEAT);
        reportReq.setDeviceID(deviceId); // "32839884"
        DevStatusMsgResp msg = new DevStatusMsgResp();
        msg.setWorkstatus(Constant.WorkStatus.MAKING_TEA);
        msg.setWarmstatus(Constant.WarmStatus.KEEPING_WARM);
        msg.setTaststatus(Constant.ConcentrationStatus.MIDDLE);
        msg.setTemperature(70);
        msg.setSoak(100);
        msg.setWaterlevel(150);
        msg.setRemaintime("580");
        msg.setErrorstatus(Constant.ErrorStatus.NORMAL);
        msg.setNowwarm(65);
        reportReq.setMsg(msg);
        return JSONObject.toJSONString(reportReq);
    }

    public static void main(String[] args) throws InterruptedException {
		//mockClientDev("32839884");
		System.out.println(getDevStatusInfo("18170964"));
	}

	/**
	 * 模拟终端启动
	 */
	public static void mockClientDev(String deviceId) {
		final Bootstrap bootstrap = config();
		ExecutorService serExecutorService = Executors.newFixedThreadPool(CountHelper.ThreadNum);
		for(int i=0 ; i < CountHelper.ThreadNum ;i++){
			serExecutorService.execute(() ->{
				try {
					startClient(bootstrap, deviceId);//阻塞运行 需要开线程启动
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
