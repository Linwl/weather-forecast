package com.linwl.weatherforecast.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/2 10:18
 * @description ：消息接收人
 * @modified By：
 */
@Data
@ToString
public class RecipientEntity {


    public RecipientEntity(String name,String email,WeatherEntity weather)
    {
        this.name =name;
        this.email =email;
        this.weather = weather;
    }

    /**
     * 名字
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 通知的天气内容
     */
    private WeatherEntity weather;
}
