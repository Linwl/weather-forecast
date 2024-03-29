package com.linwl.weatherforecast.service;

import com.linwl.weatherforecast.utils.YamlReader;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * @author ：linwl
 * @date ：Created in 2019/11/2 9:25
 * @description ：
 * @modified By：
 */
@Slf4j
public class EmailService {

  /** smtp服务器 */
  private String host;
  /** 发件人地址 */
  private String from;
  /** 收件人地址 */
  private String to;
  /** 附件地址 */
  private String affix;
  /** 附件名称 */
  private String affixName;
  /** 用户名 */
  private String user;
  /** 密码 */
  private String pwd;
  /** 邮件标题 */
  private String subject;

  /** 邮件内容 */
  private String text;

  /** 是否发送附近 */
  private boolean annexed;

  private Session session;

  private Transport transport;

  private EmailService() {
    if (EmailService.SingletonHolder.instance != null) {
      throw new IllegalStateException();
    }
  }

  static {
    try {
      SingletonHolder.instance.host =
          YamlReader.getInstance().getValueByPath("server.email.host").toString();
      SingletonHolder.instance.user =
          YamlReader.getInstance().getValueByPath("server.email.user").toString();
      SingletonHolder.instance.pwd =
          YamlReader.getInstance().getValueByPath("server.email.passwd").toString();
      SingletonHolder.instance.from = SingletonHolder.instance.user;
      SingletonHolder.instance.annexed = false;
      Properties props = new Properties();
      // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
      props.put("mail.smtp.host", SingletonHolder.instance.host);
      // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.socketFactory.port", "465");
      props.put("mail.smtp.port", "465");
      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

      // 用刚刚设置好的props对象构建一个session
      SingletonHolder.instance.session = Session.getDefaultInstance(props);
      // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
      // 用（你可以在控制台（console)上看到发送邮件的过程）
      boolean debuged = Boolean.parseBoolean(YamlReader.getInstance().getValueByPath("server.email.debug").toString());
      SingletonHolder.instance.session.setDebug(debuged);
      SingletonHolder.instance.transport = SingletonHolder.instance.session.getTransport(new URLName("smtp", SingletonHolder.instance.host, 465, null, SingletonHolder.instance.user, SingletonHolder.instance.pwd));
      // 连接服务器的邮箱
      SingletonHolder.instance.transport.connect();
      log.info("连接163邮箱服务器成功!");

    } catch (Exception e) {
      log.error(MessageFormat.format("初始化邮件配置异常:{0}!", e.getMessage()));
    }
  }

  public static EmailService getInstance() {
    return SingletonHolder.instance;
  }

  private static class SingletonHolder {
    private static EmailService instance = new EmailService();
  }

  public synchronized void send(String to, String subject, String text) throws Exception {
    this.text = text;
    this.to = to;
    this.subject = subject;
    sendEmail();
  }

  public synchronized void send(String to, String subject, String text, String from)
      throws Exception {
    this.text = text;
    this.to = to;
    this.subject = subject;
    this.from = from;
    log.info(MessageFormat.format("准备发送邮件给用户<{0}>!", to));
    sendEmail();
  }

  private void sendEmail() throws MessagingException {

    // 用session为参数定义消息对象
    MimeMessage message = new MimeMessage(session);
    // 加载发件人地址
    message.setFrom(new InternetAddress(from));
    // 加载收件人地址
    InternetAddress[] toAddr = new InternetAddress[2];
    toAddr[0] = new InternetAddress(user); // 自己的邮箱
    toAddr[1] = new InternetAddress(to); // 对方的邮箱

    // 3. To: 收件人（可以增加多个收件人、抄送、密送）
    message.setRecipients(MimeMessage.RecipientType.TO, toAddr);
    // 加载标题
    message.setSubject(subject);

    // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
    Multipart multipart = new MimeMultipart();

    // 设置邮件的文本内容
    BodyPart contentPart = new MimeBodyPart();
    contentPart.setContent(text, "text/html; charset=utf-8");
    multipart.addBodyPart(contentPart);
    // 添加附件
    if (annexed) {
      BodyPart messageBodyPart = new MimeBodyPart();
      DataSource source = new FileDataSource(affix);
      // 添加附件的内容
      messageBodyPart.setDataHandler(new DataHandler(source));
      // 添加附件的标题
      // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
      sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
      messageBodyPart.setFileName("=?GBK?B?" + enc.encode(affixName.getBytes()) + "?=");
      multipart.addBodyPart(messageBodyPart);
    }

    // 将multipart对象放到message中
    message.setContent(multipart);
    // 保存邮件
    message.saveChanges();
    // 发送邮件
    if(transport.isConnected()){
      transport.sendMessage(message, message.getAllRecipients());
    }
    else {
      transport.connect();
      transport.sendMessage(message, message.getAllRecipients());
    }
    log.info(MessageFormat.format("发送邮件<{0}>成功!", to));
  }
}
