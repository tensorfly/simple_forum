package chinaso.com.demo.springboot.Util;

import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author fangqian
 * @date 2018/6/28 14:06
 */
public class MailUtil implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(MailUtil.class);
    private String email;// 收件人邮箱
    private String code;// 激活码
    private String accountId;//账号
    private String url;//服务器地址

    public MailUtil(String email, String code,String accountId,String url) {
        this.email = email;
        this.code = code;
        this.accountId = accountId;
        this.url = url;
    }

    public void run() {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        // 3.发送一封激活邮件
        String from = "service@tensorfly.cn";// 发件人电子邮箱
        String host = "smtp.exmail.qq.com"; // 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)

        Properties properties = System.getProperties();// 获取系统属性

        properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true");// 打开认证

        try {
            //QQ邮箱需要下面这段代码，163邮箱不需要
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            //qq授权码 20190719
//            成功开启POP3/SMTP服务,在第三方客户端登录时，密码框请输入以下授权码：
//            zqtdrjgchrqdbjdd
//            成功开启IMAP/SMTP服务,在第三方客户端登录时，密码框请输入以下授权码：
//            hmgbnbmzijiqcade

            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("service@tensorfly.cn", "Forum2019"); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(from));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 2.3设置邮件主题
            message.setSubject("账号激活");
            // 2.4设置邮件内容
            String content = "<html><head></head><body><h1>这是Libra中文社区给你发送的账号激活邮件,激活请点击以下链接</h1><h3><a href='"+ url + "/activation/" + accountId + "/"
                    + code + "'>"+ url + "/activation/" + accountId + "/" + code
                    + "</href></h3></body></html>";
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            Transport.send(message);
            logger.info("给用户"+accountId+"发送邮件成功！！！");
            System.out.println("邮件成功发送!");
        } catch (Exception e) {
            logger.error("邮件发送失败",e);
            e.printStackTrace();
        }
    }
}
