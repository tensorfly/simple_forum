package chinaso.com.demo.springboot.dao;

import chinaso.com.demo.springboot.entity.User;

/**
 * @author fangqian
 * @date 2018/6/28 10:52
 */
public interface UserDao {

    //获取用户信息
    public User getUser(User user);
    //添加用户
    public int addUser(User user);
}
