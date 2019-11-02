package com.linwl.weatherforecast.builder;

import com.linwl.weatherforecast.entity.RecipientEntity;
import lombok.Data;

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


    public RecipientEntity build()
    {
        return new RecipientEntity(name,email);
    }
}
