package com.linwl.weatherforecast.task;

import com.linwl.weatherforecast.entity.RecipientEntity;
import com.linwl.weatherforecast.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/2 9:28
 * @description ：工作任务
 * @modified By：
 */
@Slf4j
public class WorkTask implements Runnable {

    private RecipientEntity recipient;

    public WorkTask(RecipientEntity recipient)
    {
        this.recipient = recipient;

    }

    @Override
    public void run() {
        String worker = Thread.currentThread().getName();
        try
        {
            log.info(MessageFormat.format("{0}接收到用户<{1}>的发送任务!",worker,recipient.getName()));

            HttpUtil.syncGet(HttpUtil.getWeatherUrl());
        }
        catch (Exception e)
        {
            log.error(MessageFormat.format("{0}执行任务发生异常:{1}!",worker,e.getMessage()));
        }
    }
}
