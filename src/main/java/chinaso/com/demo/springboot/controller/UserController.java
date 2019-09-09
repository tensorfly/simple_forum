package chinaso.com.demo.springboot.controller;

import chinaso.com.demo.springboot.Util.DateAndTimeUtil;
import chinaso.com.demo.springboot.Util.FindPasswordMailUtil;
import chinaso.com.demo.springboot.Util.MD5Util;
import chinaso.com.demo.springboot.Util.MailUtil;
import chinaso.com.demo.springboot.entity.User;
import chinaso.com.demo.springboot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author fangqian
 * @date 2018/6/27 11:25
 */

@Controller
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * @Description:进入普通用户登陆页面
     * @param:
     * @return:String
     */
    @RequestMapping("/toLogin")
    public String loginIndex(){
//        return "login_new";
        return "libra_login";
    }

    /**
     * @Description:退出
     * @param:
     * @return:String
     */
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        /*return "redirect:/";*/
        return "redirect:/topic/list";
    }

    @RequestMapping("/")
    public String index(){
        /*return "redirect:/topic/list";*/
        return "redirect:/common/libra_index.html";
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
                        HttpServletRequest request,
                        Model model
                        ){
        boolean flag = false;
        String message = "";
        if(accountId == null || StringUtils.isEmpty(accountId)){
            message="用户名不能为空";
            model.addAttribute("flag",flag);
            model.addAttribute("message",message);
            return "libra_login";
        }
        if(password == null || StringUtils.isEmpty(password)){
            message="密码不能为空";
            model.addAttribute("flag",flag);
            model.addAttribute("message",message);
            return "libra_login";
        }

        password = MD5Util.md5(password);

        User user = userService.getUser(accountId,password);
        if(null != user){
            if(user.getStatus()==1){
                message = "登陆成功";
                model.addAttribute("flag",flag);
                model.addAttribute("message",message);
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(600);
                session.setAttribute("user",user);
                /*return "redirect:/";*/
                return "redirect:/topic/list";
            }else{
                message = "账号未激活,非法登录";
            }
        }else{
            message="用户名或密码错误，请重新登录";
        }
        model.addAttribute("flag",flag);
        model.addAttribute("message",message);
        return "libra_login";
    }


    /**
     * @Description:进入注册页面
     * @param:
     * @return:String
     */
    @RequestMapping("/toRegister")
    public String insert(HttpServletRequest request) {
        return "libra_login_reg";
    }

    /**
     * @Description:用户注册信息
     * @param: User
     * @return:String
     */
    @RequestMapping("/register")
    public String save(User user,Model model,HttpServletRequest request) {
        boolean flag = false;
        String message = "";
        // 获取用户名
        String accountId = user.getAccountId();
        User selectUser =  userService.getUserByAccountId(accountId);
        if(null != selectUser ){
            message = "该账号已被注册";
            model.addAttribute("flag",flag);
            model.addAttribute("message",message);
            return "libra_login_reg";
        }
        User newUser = userService.getUserByEmail(user.getEmail());
        if(null != newUser ){
            message = "该邮箱已被注册";
            model.addAttribute("flag",flag);
            model.addAttribute("message",message);
            return "libra_login_reg";
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
            //服务器地址
            String url="http://" + request.getServerName()+ ":" + request.getServerPort();
            new Thread(new MailUtil(user.getEmail(), uuidCode,user.getAccountId(),url)).start();
        }else{
            message = "注册失败";
        }
        model.addAttribute("flag",flag);
        model.addAttribute("message",message);
       return "libra_login_reg";
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
                    message = "帐号已被激活，";
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


    //重置密码
    @RequestMapping(value = "/modify/password",method = RequestMethod.POST)
    public String modifyPassword(@RequestParam String accountId, @RequestParam String code,
            @RequestParam  String password,
            HttpServletRequest request,
            Model model) {
        String message = "";
        boolean flag = false;
        //根据用户id获取用户信息
        User user = userService.getUser(accountId,code);
        if(null != user){
            if(user.getStatus()==0){
                message="账号未激活，请联系管理员";
            }else{
                user.setPassword(MD5Util.md5(password));
                user.setUpdatetime(DateAndTimeUtil.getStringCurrentTime());
                int count = userService.updateUserPassword(user);
                if(count>0){
                    flag = true;
                    message = "修改密码成功，请登录";
                }else{
                    message = "修改密码失败，请稍后再试";
                }
            }
        }else{
            message="该账户不存在，修改密码失败";
        }
        model.addAttribute("flag",flag);
        model.addAttribute("message",message);
        return "activation";
    }

    //跳转至重置密码页面
    @RequestMapping(value = "/resetPassword/{accountId}/{code}",method = RequestMethod.GET)
    public String resetPassword(@PathVariable("accountId") String accountId, @PathVariable("code") String code,Model model) {
        boolean reasonable = false;
        String  info= "";
        User user = userService.getUser(accountId,code);
        if(null != user){
            //进入重置密码页面
            reasonable = true;
        }else{
            reasonable = false;
            info = "非法入侵者" ;
        }
        model.addAttribute("accountId",accountId);
        model.addAttribute("code",code);
        model.addAttribute("reasonable",reasonable);
        model.addAttribute("info",info);
        return "resetPassword";
    }




    /**
     * 忘记密码--找回密码
     */
    @RequestMapping(value = "/forgetPassword")
    public String forgetPassword(
            @RequestParam(value = "email",required = true) String email,
            HttpServletRequest request,
            Model model){
        boolean flag = false;
        String message = "";
        if(email == null || StringUtils.isEmpty(email)){
            message="邮箱不允许为空";
            model.addAttribute("flag",flag);
            model.addAttribute("message",message);
            return "forgetPassword";
        }
        User user = userService.getUserByEmail(email);
        if(null != user){
            flag = true;
            message = "找回密码成功，请去邮箱重置密码";
            //找回密码成功则通过线程的方式给用户发送一封邮件
            //服务器地址
            String url="http://" + request.getServerName()+ ":" + request.getServerPort();
            new Thread(new FindPasswordMailUtil(email, user.getPassword(),user.getAccountId(),url)).start();

        }else{
            message="该邮箱不存在，请输入正确的邮箱账号";
        }
        model.addAttribute("flag",flag);
        model.addAttribute("message",message);
        return "forgetPassword";

    }


    /**
     * @Description:进入忘记密码页面
     * @param:
     * @return:String
     */
    @RequestMapping("/toForgetPasswordPage")
    public String toForgetPasswordPage(){
        return "forgetPassword";
    }


}

