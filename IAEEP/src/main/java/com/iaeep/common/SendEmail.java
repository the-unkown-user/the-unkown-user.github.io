package com.iaeep.common;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {
    /**
     *发送邮件
     * @param email(收件人邮箱),emailContent（发送邮件正文）
     * @return
     */
    public static boolean sendEmailCode(String email, String emailContent ){
        //登录qq邮箱服务器
        //配置文件对象
        Properties properties=new Properties();
        //设置必要参数
        properties.setProperty("mail.host","smtp.qq.com");
        properties.setProperty("mail.transport.protocol","smtp");
        //mail的session对象，创建一个维护与qq邮箱服务器连接的会话
        Session session= Session.getDefaultInstance(properties);
        //定义一个传输对象，
        Transport ts=null;
        try {
            //获取传输对象，用于发送邮件
            ts=session.getTransport();
            ts.connect("2861817978@qq.com","kzfwnnzdlkwddhce");
            //构建邮件
            MimeMessage mimeMessage=new MimeMessage(session);
            //发件人
            mimeMessage.setFrom(new InternetAddress("2861817978@qq.com"));
            //收件人
            mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
            //主题
            mimeMessage.setSubject("创新创业交流服务平台");
            //正文
            mimeMessage.setContent(emailContent,"text/html;charset=UTF-8");

            //发送邮件
            try{
                ts.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(ts!=null){
                    ts.close();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

}
