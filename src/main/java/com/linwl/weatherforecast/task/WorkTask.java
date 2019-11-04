package com.linwl.weatherforecast.task;

import com.linwl.weatherforecast.entity.RecipientEntity;
import com.linwl.weatherforecast.service.EmailService;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            log.info(MessageFormat.format("{0}接收到用户<{1}>的邮件<{2}>发送任务!",worker,recipient.getName(),recipient.getEmail()));
            EmailService emailService =EmailService.getInstance();
            emailService.send(recipient.getEmail(),MessageFormat.format("{0}的天气预报信息", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),recipient.getWeather());
            log.info(MessageFormat.format("{0}执行完用户<{1}>的邮件<{2}>发送任务!",worker,recipient.getName(),recipient.getEmail()));
        }
        catch (Exception e)
        {
            log.error(MessageFormat.format("{0}执行任务发生异常:{1}!",worker,e.getMessage()));
        }
    }
}
