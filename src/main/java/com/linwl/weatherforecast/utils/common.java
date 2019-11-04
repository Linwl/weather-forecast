package com.linwl.weatherforecast.utils;

import com.linwl.weatherforecast.entity.WeatherEntity;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/2 15:09
 * @description ：
 * @modified By：
 */
public class common {

    private common(){}

    /**
     * 获取时间
     * @param duration
     * @param format
     * @return
     */
    public static long getTime(int duration,String format)
    {
        long result;
        switch (format)
        {
            case "hour":
                result = TimeUnit.HOURS.toMillis(duration);
               break;
            case "minute":
                result = TimeUnit.MINUTES.toMillis(duration);
                break;
            case "second":
                result = TimeUnit.SECONDS.toMillis(duration);
                break;
            default:
                result =0;
        }
        return result;
    }

    /**
     * 获取天气邮件模板
     * @param weather
     * @return
     */
    public static String getContent(WeatherEntity weather)
    {
        StringBuilder content = new StringBuilder();
        //表格版本
        content.append("<!DOCTYPE html>");
        content.append("<html>");
        content.append("<head>");
        content.append("<meta charset=\"UTF-8\">");
        content.append("<title>");
        content.append(weather.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        content.append("的").append(weather.getCity());
        content.append("天气信息");
        content.append("</title>").append("<body leftmargin=\"8\" marginwidth=\"0\" topmargin=\"8\" marginheight=\"4\" offset=\"0\">");
        content.append("<table border=\"1\">");
        content.append("<thead>");
        content.append("<tr>");
        content.append("<th>城市</th>");
        content.append("<th>星期</th>");
        content.append("<th>天气情况</th>");
        content.append("<th>当前温度</th>");
        content.append("<th>高温/白天温度</th>");
        content.append("<th>低温/晚上温度</th>");
        content.append("</tr>");
        content.append("</thead>");
        content.append("<tbody>");
        content.append("<tr>");
        content.append("<td>"+weather.getCity()+"</td>");
        content.append("<td>"+weather.getWeek()+"</td>");
        content.append("<td>"+weather.getWea()+"</td>");
        content.append("<td>"+weather.getTem()+"</td>");
        content.append("<td>"+weather.getTem1()+"</td>");
        content.append("<td>"+weather.getTem2()+"</td>");
        content.append("</tr>");
        content.append("</tbody>");
        content.append("<thead>");
        content.append("<tr>");
        content.append("<th>风向(早/晚)</th>");
        content.append("<th>风速</th>");
        content.append("<th>风速等级</th>");
        content.append("<th>空气质量</th>");
        content.append("<th>空气等级</th>");
        content.append("<th>空气PM</th>");
        content.append("</tr>");
        content.append("</thead>");
        content.append("<tbody>");
        content.append("<tr>");
        content.append("<td>"+weather.getWin()+"</td>");
        content.append("<td>"+weather.getWinSpeed()+"</td>");
        content.append("<td>"+weather.getWinMeter()+"</td>");
        content.append("<td>"+weather.getAir()+"</td>");
        content.append("<td>"+weather.getAirLevel()+"</td>");
        content.append("<td>"+weather.getAirPm25()+"</td>");
        content.append("</tr>");
        content.append("</tbody>");
        content.append("<thead>");
        content.append("<tr>");
        content.append("<th>湿度</th>");
        content.append("<th>能见度</th>");
        content.append("<th>气压</th>");
        content.append("<th colspan=\"3\">更新时间</th>");
        content.append("</tr>");
        content.append("</thead>");
        content.append("<tbody>");
        content.append("<tr>");
        content.append("<td>"+weather.getHumidity()+"</td>");
        content.append("<td>"+weather.getVisibility()+"</td>");
        content.append("<td>"+weather.getPressure()+"</td>");
        content.append("<td colspan=\"3\">" + weather.getUpdateTime() + "</td>");
        content.append("</tr>");
        content.append("</tbody>");
        content.append("</table>");
        content.append("<hr>");
        content.append("<span style=\"font-size:17px\">").append(weather.getAirTips());
        content.append("</span></body>");
        content.append("</html>");
        return content.toString();
    }
}
