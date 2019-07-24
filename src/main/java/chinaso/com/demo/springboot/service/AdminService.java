package chinaso.com.demo.springboot.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fangqian
 * @date 2018/11/16 15:21
 */
public interface AdminService {
    String login(String username,String password,HttpServletRequest request);
}
