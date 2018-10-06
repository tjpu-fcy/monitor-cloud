package com.common.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

//邮件发送服务器配置

@Component
public class SendMail {

    private static  String mailHost;   //邮件服务器

    private static String  mailPort;       // 端口

    private static  String userName ;    // 用户名

    private static  String password ;    // 密码

    @Value("${mail.host}")
    public void setMailHost(String mailHost) {
        SendMail.mailHost = mailHost;
    }

    @Value("${mail.port}")
    public void setMailPort(String mailPort) {
        SendMail.mailPort = mailPort;
    }

    @Value("${mail.user}")
    public void setUserName(String userName) {
        SendMail.userName = userName;
    }

    @Value("${mail.password}")
    public void setPassword(String password) {
        SendMail.password = password;
    }



    public void sendSimpleEmail(String email,String subject,String text ){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);//指定用来发送Email的邮件服务器主机名
        mailSender.setPort(Integer.parseInt(mailPort));//默认端口，标准的SMTP端口
        mailSender.setUsername(userName);//用户名
        mailSender.setPassword(password);//密码

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");//开启认证
        properties.setProperty("mail.smtp.timeout", "3000");//设置链接超时
        properties.setProperty("mail.smtp.port", mailPort);//设置端口
        properties.setProperty("mail.smtp.socketFactory.port", mailPort);
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(properties);

        SimpleMailMessage message = new SimpleMailMessage();//消息构造器
        message.setFrom(userName);//发件人
        message.setTo(email);//收件人
        message.setSubject(subject);//主题
        message.setText(text);//正文
        mailSender.send(message);
    }
}
