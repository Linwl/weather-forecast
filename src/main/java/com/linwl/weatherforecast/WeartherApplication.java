package com.linwl.weatherforecast;

import com.linwl.weatherforecast.builder.RecipientBuilder;
import com.linwl.weatherforecast.entity.RecipientEntity;
import com.linwl.weatherforecast.task.WorkTask;
import com.linwl.weatherforecast.utils.Common;
import com.linwl.weatherforecast.utils.TaskThreadFactory;
import com.linwl.weatherforecast.utils.YamlReader;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/1 17:45
 * @description ：
 * @modified By：
 */
@Slf4j
public class WeartherApplication {

  private static final String ZERO_TIME = "00:00";
  private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(1000);

  public static void main(String[] args) throws Exception {
    log.info("天气预报服务启动中...");
    boolean exited = false;
    boolean enableSend = true;
    int poolSize =
        Integer.parseInt(
            YamlReader.getInstance().getValueByPath("server.pool.pool-size").toString());
    int maxPoolSize =
        Integer.parseInt(
            YamlReader.getInstance().getValueByPath("server.pool.max-pool-size").toString());
    int keepAliveTime =
        Integer.parseInt(
            YamlReader.getInstance().getValueByPath("server.pool.keep-alive-time").toString());
    ExecutorService taskPool =
        new ThreadPoolExecutor(
            poolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.MILLISECONDS,
            taskQueue,
            new TaskThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());
    log.info("天气预报服务启动完毕！");
    while (!exited) {
      try {
        String exTime = YamlReader.getInstance().getValueByPath("server.send.time").toString();
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        // 0点重置发送标志
        if (nowTime.equals(ZERO_TIME)) {
          enableSend = true;
        }
        if (nowTime.equals(exTime) && enableSend) {
          log.info("主线程发布任务时间到,开始进行发布任务！");
          List<Map<String, Object>> receiverList =
              (List<Map<String, Object>>) YamlReader.getInstance().getValueByPath("receivers");
          for (Map<String, Object> receiver : receiverList) {
            RecipientEntity recipientEntity =
                new RecipientBuilder()
                    .name(receiver.getOrDefault("name", "").toString())
                    .email(receiver.getOrDefault("email", "").toString())
                    .cityId(receiver.getOrDefault("city-id", "").toString())
                    .build();
            taskPool.submit(new WorkTask(recipientEntity));
          }
          enableSend = false;
        }
      } catch (Exception e) {
        log.error(MessageFormat.format("天气预报服务发生异常:{0}!", e.getMessage()));
        exited = true;
        taskPool.shutdown();
      } finally {
        Thread.sleep(Common.getTime(1, "second"));
      }
    }
  }
}
