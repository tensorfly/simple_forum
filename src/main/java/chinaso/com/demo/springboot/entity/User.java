package chinaso.com.demo.springboot.entity;

import java.io.Serializable;

/**
 * @author fangqian
 * @date 2018/6/27 17:30
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    //登录账号
    private String accountId;
    //登录密码
    private String password;
    //用户昵称
    private String nickname;
    //注册邮箱
    private String email;
    //性别
    private String sex;
    //年龄
    private String age;
    //状态
    private int status;
    //激活码
    private String code;
    //创建时间
    private String createtime;
    //更新时间
    private String updatetime;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
