package com.linwl.weatherforecast.builder;

import com.linwl.weatherforecast.entity.RecipientEntity;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/2 10:22
 * @description ：
 * @modified By：
 */
public class RecipientBuilder {

    /**
     * 名字
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 城市编码
     */
    private String cityId;

    public RecipientBuilder name(String name)
    {
        this.name =name;
        return this;
    }

    public RecipientBuilder email(String email)
    {
        this.email =email;
        return this;
    }

    public RecipientBuilder cityId(String cityId)
    {
        this.cityId =cityId;
        return this;
    }


    public RecipientEntity build()
    {
        return new RecipientEntity(name,email,cityId);
    }
}
