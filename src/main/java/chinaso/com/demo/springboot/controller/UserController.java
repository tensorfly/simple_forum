package chinaso.com.demo.springboot.controller;

import chinaso.com.demo.springboot.Util.DateAndTimeUtil;
import chinaso.com.demo.springboot.Util.MD5Util;
import chinaso.com.demo.springboot.Util.MailUtil;
import chinaso.com.demo.springboot.entity.User;
import chinaso.com.demo.springboot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author fangqian
 * @date 2018/6/27 11:25
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @Description:进入登陆页面
     * @param:
     * @return:String
     */
    @RequestMapping("/toLogin")
    public String loginIndex(Model model){
        model.addAttribute("message","");
        return "login";
    }

    @RequestMapping("/")
    public String index(){
        return "redirect:/topic/list";
    }

    /**
     * @Description:用户登陆
     * @param: name
     * @param:password
     * @param:session
     * @param:attributes
     * @return:String
     */
    @RequestMapping("/login")
    public String login(@RequestParam String accountId,
                        @RequestParam  String password,
                        HttpSession session
                        ){
        String message = "";
        if(accountId == null || StringUtils.isEmpty(accountId)){
            message="用户名不能为空";
        }
        if(password == null || StringUtils.isEmpty(password)){
            message="密码不能为空";
        }

        password = MD5Util.md5(password);

        User user = userService.getUser(accountId,password);
        if(null != user){
            if(user.getStatus()==1){
                message = "登陆成功";
                session.setAttribute("message",message);
                session.setAttribute("user",user);
                return "redirect:/";
            }else{
                message = "账号未激活";
            }
        }else{
            message="用户名或密码错误，请重新登录";
        }
        session.setAttribute("message",message);

        return "login";
    }


    /**
     * @Description:进入注册页面
     * @param:
     * @return:String
     */
    @RequestMapping("/toRegister")
    public String insert() {
        return "register";
    }

    /**
     * @Description:用户注册信息
     * @param: User
     * @return:String
     */
    @RequestMapping("/register")
    public String save(User user,Model model) {
        boolean flag = false;
        String message = "";
        // 获取用户名
        String accountId = user.getAccountId();
        User selectUser =  userService.getUserByAccountId(accountId);
        if(null != selectUser ){
            message = "该账号已被注册";
            model.addAttribute("flag",flag);
            model.addAttribute("message",message);
            return "register";
        }
        //密码进行MD5加密
        user.setPassword(MD5Util.md5(user.getPassword()));
        //生成激活码
        String uuidCode=UUID.randomUUID().toString().replace("-", "");
        user.setCode(uuidCode);
        user.setCreatetime(DateAndTimeUtil.getStringCurrentTime());
        user.setUpdatetime(DateAndTimeUtil.getStringCurrentTime());
        int count = userService.addUser(user);
        if(count>0){
            flag = true;
            message = "注册成功，请去邮箱激活账号";
            //注册成功则通过线程的方式给用户发送一封邮件
            new Thread(new MailUtil(user.getEmail(), uuidCode,user.getAccountId())).start();
        }else{
            message = "注册失败";
        }
        model.addAttribute("flag",flag);
        model.addAttribute("message",message);
       return "register";
    }

    //激活账号
    @RequestMapping(value = "/activation/{accountId}/{code}",method = RequestMethod.GET)
    public String activation(@PathVariable("accountId") String accountId, @PathVariable("code") String code,Model model) {
        String message = "";
        boolean flag = false;
        if(accountId == null || StringUtils.isEmpty(accountId) || code == null || StringUtils.isEmpty(code)){
            message="请勿非法激活";
            model.addAttribute("flag",flag);
            model.addAttribute("message",message);
            return "activation";
        }
        User user = userService.getUserByCode(accountId,code);
        if(null != user){
            if(user.getStatus()==1){
                message="请勿重复激活";
            }else{
                user.setStatus(1);
                user.setUpdatetime(DateAndTimeUtil.getStringCurrentTime());
                int count = userService.updateUserStatus(user);
                if(count>0){
                    flag = true;
                    message = "激活成功";
                }else{
                    message = "激活失败，请重新激活";
                }
            }
        }else{
            message="请勿非法激活";
        }
        model.addAttribute("flag",flag);
        model.addAttribute("message",message);
        return "activation";
    }

}

