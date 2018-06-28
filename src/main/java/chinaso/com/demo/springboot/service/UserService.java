package chinaso.com.demo.springboot.service;

import chinaso.com.demo.springboot.entity.User;

/**
 * @author fangqian
 * @date 2018/6/27 16:44
 */
public interface UserService {

    //获取用户信息
    public User getUser(String accountId,String password);
    //获取用户信息
    public User getUserByAccountId(String accountId);
    //添加用户
    public int addUser(User user);
    //添加用户
    public int updateUserStatus(User user);

    public User getUserByCode(String accountId,String code);
}
