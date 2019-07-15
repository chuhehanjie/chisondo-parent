package test.moniTerminal;
import com.chisondo.model.constant.DeviceConstant;
import com.chisondo.model.http.req.SetDevChapuParamHttpReq;
import com.chisondo.model.http.resp.DeviceMsgResp;
import com.chisondo.model.http.req.StartWorkHttpReq;
import com.chisondo.model.http.resp.DevParamMsg;
import com.chisondo.model.http.resp.*;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.req.QryDeviceInfoHttpReq;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;


/**
 * 客户端处理服务端返回信息的处理器
 * @author ding.zhong
 * @date 2017年12月29日 
 * @version V1.0
 */
@Slf4j
public class MockTerminalHandler extends SimpleChannelInboundHandler<String>{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("模拟终端与网关通道建立。。。。。。");
		System.out.println("模拟终端本地ip：" + ctx.channel().localAddress());
		String data = "{\"action\":\"statuspush\",\"actionFlag\":1,\"deviceID\":\"7788520\",\"msg\":{\"errorstatus\":0,\"nowwarm\":65,\"remaintime\":\"1234\",\"soak\":100,\"taststatus\":2,\"temperature\":70,\"warmstatus\":0,\"waterlevel\":150,\"workstatus\":0}}\\n";
//		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!\n", CharsetUtil.UTF_8));
		ctx.writeAndFlush(Unpooled.copiedBuffer("{\"name\":\"chris\"}\n", CharsetUtil.UTF_8));
        System.out.println("客户端 active 了");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("出现异常。。。");
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		// TODO Auto-generated method stub
		Channel channel = ctx.channel();
		String json = msg;
		log.info("端设备 [{}] 收到消息 = [{}]", channel.remoteAddress(), json);
		try {
			if (this.isQueryDevStatusReq(json)) {
				QryDeviceInfoHttpReq req = JSONObject.parseObject(json, QryDeviceInfoHttpReq.class);
				log.info("获取查询设备状态信息请求，设备ID = {}", req.getDeviceID());
				DevStatusReportResp resp = this.buildDevStatusResp(req.getDeviceID());
				channel.writeAndFlush(Unpooled.copiedBuffer(JSONObject.toJSONString(resp) + "\n", CharsetUtil.UTF_8));
				log.info("发送设备状态信息响应到 TCP 服务");
			} else if (this.isQueryDevSettingReq(json)) {
				QryDeviceInfoHttpReq req = JSONObject.parseObject(json, QryDeviceInfoHttpReq.class);
				log.info("获取查询设备设备参数请求，设备ID = {}", req.getDeviceID());
				DevSettingHttpResp resp = this.buildDevSettingResp(req.getDeviceID());
				channel.writeAndFlush(Unpooled.copiedBuffer(JSONObject.toJSONString(resp) + "\n", CharsetUtil.UTF_8));
				log.info("发送设备状态信息响应到 TCP 服务");
			} else if (this.isStartWorkReq(json)) {
				StartWorkHttpReq req = JSONObject.parseObject(json, StartWorkHttpReq.class);
				log.info("获取设备启动沏茶/洗茶/烧水请求，设备ID = {}", req.getDeviceID());
				DeviceHttpResp resp = this.buildDevHttpResp(req.getDeviceID());
				channel.writeAndFlush(Unpooled.copiedBuffer(JSONObject.toJSONString(resp) + "\n", CharsetUtil.UTF_8));
				log.info("发送设备启动沏茶/洗茶/烧水响应到 TCP 服务");
			} else if (this.isSetChapuParamReq(json)) {
				SetDevChapuParamHttpReq req = JSONObject.parseObject(json, SetDevChapuParamHttpReq.class);
				log.info("获取设置设备茶谱参数请求，设备ID = {}", req.getDeviceID());
				DeviceHttpResp resp = this.buildDevHttpResp(req.getDeviceID());
				channel.writeAndFlush(Unpooled.copiedBuffer(JSONObject.toJSONString(resp) + "\n", CharsetUtil.UTF_8));
				log.info("发送设置设备茶谱参数响应到 TCP 服务");
			} else {
				System.out.println("未处理的消息");
			}
		} catch (Exception e) {
			log.error("发生异常了！", e);
			channel.writeAndFlush(Unpooled.copiedBuffer("{\"retn\":500,\"desc\":\"error\"}\n", CharsetUtil.UTF_8));
		}

	}

	private DeviceHttpResp buildDevHttpResp(String deviceId) {
		DeviceHttpResp resp = new DeviceHttpResp();
		resp.setAction("startworkok");
		resp.setDeviceID(deviceId);

		DeviceMsgResp msg = new DeviceMsgResp();
		msg.setState(1);
		msg.setStateinfo(1);
		resp.setMsg(msg);

		resp.setRetn(MockData.SC_OK);
		resp.setDesc(MockData.REQ_SUCCESS);

		return resp;
	}

	private DevSettingHttpResp buildDevSettingResp(String deviceId) {
		DevSettingHttpResp resp = new DevSettingHttpResp();
		resp.setAction("qrydevparmok");
		resp.setDeviceID(deviceId);

		DevSettingMsgResp msg = new DevSettingMsgResp();
		msg.setState(0);
		msg.setStateinfo(0);
		msg.setVolflag(1);
		msg.setGmsflag(1);
		resp.setMsg(msg);

		DevParamMsg washteaMsg = new DevParamMsg();
		washteaMsg.setTemperature(80);
		washteaMsg.setSoak(30);
		washteaMsg.setWaterlevel(200);
		resp.setWashteamsg(washteaMsg);

		DevParamMsg boilwaterMsg = new DevParamMsg();
		boilwaterMsg.setTemperature(85);
		boilwaterMsg.setSoak(35);
		boilwaterMsg.setWaterlevel(250);
		resp.setBoilwatermsg(boilwaterMsg);

		TeaSpectrumMsgResp teaspectrumMsg = new TeaSpectrumMsgResp();
		teaspectrumMsg.setIndex(1);
		teaspectrumMsg.setChapuid(3);
		teaspectrumMsg.setChapuname("乌龙茶-浓");
		teaspectrumMsg.setMaketimes(1);
		DevParamMsg msg2 = new DevParamMsg();
		msg2.setTemperature(77);
		msg2.setSoak(33);
		msg2.setWaterlevel(555);
		teaspectrumMsg.setTeaparm(msg2);
		resp.setChapumsg(Arrays.asList(teaspectrumMsg));

		resp.setRetn(MockData.SC_OK);
		resp.setDesc(MockData.REQ_SUCCESS);
		return resp;
	}

	private DevStatusReportResp buildDevStatusResp(String deviceId) {
		DevStatusReportResp resp = new DevStatusReportResp();
		resp.setRetn(MockData.SC_OK);
		resp.setDesc(MockData.REQ_SUCCESS);
		resp.setAction("qrydevicestateok");
		resp.setActionFlag(DeviceConstant.DevStatus.HEART_BEAT);
		resp.setDeviceID(deviceId);
		DevStatusMsgResp msg = new DevStatusMsgResp();
		msg.setWorkstatus(DeviceConstant.WorkStatus.MAKING_TEA);
		msg.setWarmstatus(DeviceConstant.WarmStatus.KEEPING_WARM);
		msg.setTaststatus(DeviceConstant.ConcentrationStatus.MIDDLE);
		msg.setTemperature(70);
		msg.setSoak(100);
		msg.setWaterlevel(150);
		msg.setRemaintime(580);
		msg.setErrorstatus(DeviceConstant.ErrorStatus.NORMAL);
		msg.setNowwarm(65);
		resp.setMsg(msg);
		return resp;
	}

	private boolean isQueryDevStatusReq(String json) {
		return json.contains("\"action\":\"qrydevicestate\"");
	}

	private boolean isQueryDevSettingReq(String json) {
		return json.contains("\"action\":\"qrydevparm\"");
	}

	private boolean isStartWorkReq(String json) {
		return json.contains("\"action\":\"startwork\"");
	}

	private boolean isSetChapuParamReq(String json) {
		return json.contains("\"action\":\"setchapuparm\"");
	}

}
