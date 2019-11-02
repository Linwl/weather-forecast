package com.linwl.weatherforecast.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/2 11:24
 * @description ：
 * @modified By：
 */
public class HttpUtil {

    private HttpUtil(){}

    public static <T> T syncGet(String url,Class<?> clazz) throws Exception
    {
        T result =null;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String body = Objects.requireNonNull(response.body()).string();
            result = JSONObject.parseObject(body, (Type) clazz);
        }
        return result;
    }

    /**
     * 获取请求天气url
     * @return
     */
    public static String getWeatherUrl()
    {
        StringBuilder sb =new StringBuilder();
        sb.append("https://www.tianqiapi.com/api?");
        sb.append("appid=");
        sb.append("83423352");
        sb.append("&appsecret=");
        sb.append("6m1wkvcp");
        sb.append("&version=");
        sb.append("v6");
        sb.append("&cityid=");
        sb.append("101280800");
        return sb.toString();
    }
}
