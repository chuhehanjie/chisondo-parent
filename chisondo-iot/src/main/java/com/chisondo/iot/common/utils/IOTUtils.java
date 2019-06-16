package com.chisondo.iot.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.constant.DevReqURIConstant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.util.CharsetUtil;
import org.springframework.http.MediaType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class IOTUtils {

    public static List<String> reqUriList;

    static {
        reqUriList = new ArrayList<>();
        reqUriList.add(DevReqURIConstant.START_WORK);
        reqUriList.add(DevReqURIConstant.QRY_DEV_PARAM);
        reqUriList.add(DevReqURIConstant.QRY_DEV_CHAPU);
        reqUriList.add(DevReqURIConstant.QRY_DEV_STATUS);
        reqUriList.add(DevReqURIConstant.START_KEEP_WARM);
        reqUriList.add(DevReqURIConstant.SET_DEV_CHAPU_PARAM);
        reqUriList.add(DevReqURIConstant.SET_DEV_OTHER_PARAM);
        reqUriList.add(DevReqURIConstant.STOP_WORK);
        reqUriList.add(DevReqURIConstant.LOCK_OR_UNLOCK_DEV);
        reqUriList.add(DevReqURIConstant.SET_DEV_PARAM);
    }

    public static List<String> getReqUriList() {
        return reqUriList;
    }

    public static String convertByteBufToString(ByteBuf buf) {
        String str;
        if(buf.hasArray()) { // 处理堆缓冲区
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }

    public static ByteBuf convertString2ByteBuf(String str) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(str, CharsetUtil.UTF_8);
        return byteBuf;
    }

    public static FullHttpResponse buildResponse(Object obj) {
        return buildResponse(HttpResponseStatus.OK, obj);
    }
    public static FullHttpResponse buildResponse(HttpResponseStatus status, Object obj) {
        return buildResponse(status, JSONObject.toJSONString(obj));
    }
    public static FullHttpResponse buildResponse(HttpResponseStatus status, String data) {
        ByteBuf content = Unpooled.copiedBuffer(data, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        if (content != null) {
            response.headers().set(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.headers().set(org.springframework.http.HttpHeaders.CONTENT_LENGTH, response.content().readableBytes());
        }
        return response;
    }

    /*
    * 获取GET方式传递的参数
    */
    public static Map<String, Object> getGetParamsFromChannel(FullHttpRequest fullHttpRequest) {

        Map<String, Object> params = new HashMap<String, Object>();
        // 处理get请求
        QueryStringDecoder decoder = new QueryStringDecoder(fullHttpRequest.uri());
        Map<String, List<String>> paramList = decoder.parameters();
        for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
            params.put(entry.getKey(), entry.getValue().get(0));
        }
        return params;
    }

    /*
     * 获取POST方式传递的参数
     */
    public static Map<String, Object> getPostParamsFromRequest(FullHttpRequest fullHttpRequest) {
        Map<String, Object> params = new HashMap<>();

        if (fullHttpRequest.method() == HttpMethod.POST) {
            // 处理POST请求
            String strContentType = fullHttpRequest.headers().get("Content-Type").trim();
            if (strContentType.contains("x-www-form-urlencoded")) {
                params  = getFormParams(fullHttpRequest);
            } else if (strContentType.contains("application/json")) {
                String json = getJSONFromRequest(fullHttpRequest);
                params = JSONObject.parseObject(json, Map.class);
            } else {
                return null;
            }
            return params;
        } else {
            return null;
        }
    }

    /*
     * 解析from表单数据（Content-Type = x-www-form-urlencoded）
     */
    public static Map<String, Object> getFormParams(FullHttpRequest fullHttpRequest) {
        Map<String, Object> params = new HashMap<String, Object>();

        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), fullHttpRequest);
        List<InterfaceHttpData> postData = decoder.getBodyHttpDatas();

        for (InterfaceHttpData data : postData) {
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                MemoryAttribute attribute = (MemoryAttribute) data;
                params.put(attribute.getName(), attribute.getValue());
            }
        }

        return params;
    }

    /*
     * 解析json数据（Content-Type = application/json）
     */
    public static String getJSONFromRequest(FullHttpRequest fullHttpRequest) {
        ByteBuf content = fullHttpRequest.content();
        byte[] reqContent = new byte[content.readableBytes()];
        content.readBytes(reqContent);
        String json = new String(reqContent, CharsetUtil.UTF_8);
        return json;
    }

    public static void main(String[] args) {
        String json = "{\"action\":\"statuspush\",\"actionFlag\":1,\"deviceID\":\"7788520\",\"msg\":\"errorstatus\":0,\"nowwarm\":65,\"remaintime\":\"580\",\"soak\":100,\"taststatus\":2,\"temperature\":70,\"warmstatus\":1,\"waterlevel\":150,\"workstatus\":1},\"oK\":false,\"retn\":0}" + "\n";
        System.out.println(HexUtils.str2HexStr(json));
        System.out.println(ByteBuffer.wrap(json.getBytes()).toString());
    }
}
