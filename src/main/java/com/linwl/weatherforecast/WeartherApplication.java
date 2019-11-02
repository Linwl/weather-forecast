package com.linwl.weatherforecast;

import com.linwl.weatherforecast.builder.RecipientBuilder;
import com.linwl.weatherforecast.entity.RecipientEntity;
import com.linwl.weatherforecast.task.WorkTask;
import com.linwl.weatherforecast.utils.TaskThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/1 17:45
 * @description ：
 * @modified By：
 */
@Slf4j
public class WeartherApplication {

    private static BlockingQueue<Runnable> taskQueue =new ArrayBlockingQueue<>(1000);

    private static List<RecipientEntity> recipientEntities = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        log.info("天气预报服务启动中...");
        boolean exited =false;
        recipientEntities.add(new RecipientBuilder().name("linwl").email("304115325@qq.com").build());
        ExecutorService taskPool =new ThreadPoolExecutor(4,8,5, TimeUnit.SECONDS,taskQueue,new TaskThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        log.info("天气预报服务启动完毕！");
        while (!exited)
        {
            try {
                int exhour = LocalDateTime.now().getHour();
                if(exhour == 11)
                {
                    for (RecipientEntity recipientEntity : recipientEntities) {
                        taskPool.submit(new WorkTask(recipientEntity));
                    }
                }
            }
            catch (Exception e)
            {
                log.error(MessageFormat.format("天气预报服务发生异常:{0}!",e.getMessage()));
                exited = true;
                taskPool.shutdown();
            }
            finally {
                log.info("主线程发布任务完毕,开始进行睡眠！");
                Thread.sleep(5000);
            }
        }
    }
}
