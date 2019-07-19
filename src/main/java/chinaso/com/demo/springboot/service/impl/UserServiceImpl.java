package chinaso.com.demo.springboot.service.impl;

import chinaso.com.demo.springboot.entity.User;
import chinaso.com.demo.springboot.mapper.UserMapper;
import chinaso.com.demo.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fangqian
 * @date 2018/6/28 10:54
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(String accountId,String password) {
        return userMapper.getUser(accountId,password);
    }

    @Override
    public User getUserByAccountId(String accountId) {
        return userMapper.getUserByAccountId(accountId);
    }

    @Override
    public int addUser(User user) {
        userMapper.insert(user);
        User newUser = userMapper.getUserByAccountId(user.getAccountId());
        if(null != newUser){
            //注册成功
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public int updateUserStatus(User user) {
        userMapper.update(user);
        User newUser = userMapper.getUserByAccountId(user.getAccountId());
        if(null != newUser && newUser.getStatus()==1){
            //激活成功
            return 1;
        }
        return 0;
    }

    @Override
    public int updateUserPassword(User user) {
        userMapper.updatePw(user);
        return 1;
    }

    @Override
    public User getUserByCode(String accountId, String code) {
        return userMapper.getUserByCode(accountId,code);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }
}
