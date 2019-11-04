package com.linwl.weatherforecast.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/2 11:50
 * @description ：
 * @modified By：
 */
@ToString
@Data
public class WeatherEntity {

    /**
     * 城市编码
     */
    private String cityid;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 天气情况
     */
    private String wea;

    /**
     * 湿度
     */
    private String humidity;

    /**
     * 当前温度
     */
    private String tem;

    /**
     * 高温/白天温度
     */
    private String tem1;

    /**
     * 低温/晚上温度
     */
    private String tem2;

    /**
     * 风向(早/晚)
     */
    private String win;

    /**
     * 风速等级
     */
    @JSONField(defaultValue = "win_speed")
    private String winSpeed;

    /**
     * 风速
     */
    @JSONField(defaultValue = "win_meter")
    private String winMeter;

    /**
     * 气压
     */
    private String pressure;

    /**
     * 能见度
     */
    private String visibility;

    /**
     * PM
     */
    @JSONField(defaultValue = "air_pm25")
    private String airPm25;

    /**
     * 星期
     */
    private String week;

    /**
     * 空气质量
     */
    private String air;

    /**
     * 空气等级
     */
    @JSONField(defaultValue = "air_level")
    private String airLevel;

    /**
     * 空气质量描述
     */
    @JSONField(defaultValue = "air_tips")
    private String airTips;

    /**
     * 更新时间
     */
    @JSONField(defaultValue = "update_time")
    private String updateTime;

}
