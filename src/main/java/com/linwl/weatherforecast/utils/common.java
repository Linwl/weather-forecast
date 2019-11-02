package com.linwl.weatherforecast.utils;

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
}
