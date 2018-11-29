package chinaso.com.demo.springboot.service.impl;

import chinaso.com.demo.springboot.Util.Common;
import chinaso.com.demo.springboot.service.AdminService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author fangqian
 * @date 2018/11/16 15:22
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    @Qualifier("commonUtil")
    private Common commonUtil;

    @Override
    public String login(String username, String password) {
        if(StringUtil.isEmpty(username)){
            return JSON.toJSONString(commonUtil.result("50", "用户名不允许为空", null));
        }
        if(StringUtil.isEmpty(password)){
            return JSON.toJSONString(commonUtil.result("60", "密码不允许为空", null));
        }
        if(!username.equals("admin") || !password.equals("admin123")){
            return JSON.toJSONString(commonUtil.result("70", "用户名或密码错误", null));
        }
        return JSON.toJSONString(commonUtil.result("0", "登陆成功", null));
    }
}
