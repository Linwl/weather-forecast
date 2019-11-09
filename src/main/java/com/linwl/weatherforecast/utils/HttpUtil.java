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

  private HttpUtil() {}

  public static <T> T syncGet(String url, Class<?> clazz) throws Exception {
    T result = null;
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
   *
   * @return
   */
  public static String getWeatherUrl(String cityid) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append(YamlReader.getInstance().getValueByPath("weather.url").toString());
    sb.append("?appid=");
    sb.append(YamlReader.getInstance().getValueByPath("weather.appid").toString());
    sb.append("&appsecret=");
    sb.append(YamlReader.getInstance().getValueByPath("weather.appsecret").toString());
    sb.append("&version=");
    sb.append(YamlReader.getInstance().getValueByPath("weather.version").toString());
    sb.append("&cityid=");
    sb.append(cityid);
    return sb.toString();
  }
}
