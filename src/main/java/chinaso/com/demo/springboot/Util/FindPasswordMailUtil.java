package chinaso.com.demo.springboot.Util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author fangqian
 * @date 2018/6/28 14:06
 */
public class FindPasswordMailUtil implements Runnable {
    private String email;// 收件人邮箱
    private String code;// 激活码
    private String accountId;//账号
    private String url;//服务器地址

    public FindPasswordMailUtil(String email, String code, String accountId, String url) {
        this.email = email;
        this.code = code;
        this.accountId = accountId;
        this.url = url;
    }

    public void run() {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        // 3.发送一封激活邮件
        String from = "274561387@qq.com";// 发件人电子邮箱
        String host = "smtp.qq.com"; // 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)

        Properties properties = System.getProperties();// 获取系统属性

        properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true");// 打开认证

        try {
            //QQ邮箱需要下面这段代码，163邮箱不需要
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);


            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("274561387@qq.com", "zqtdrjgchrqdbjdd"); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(from));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 2.3设置邮件主题
            message.setSubject("重置密码");
            // 2.4设置邮件内容
            String content = "<html><head></head><body><h1>这是一封邮件,确认重置密码请点击以下链接</h1><h3><a href='"+ url + "/resetPassword/" + accountId + "/"
                    + code + "'>"+ url + "/resetPassword/" + accountId + "/" + code
                    + "</href></h3></body></html>";
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            Transport.send(message);
            System.out.println("邮件成功发送!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
