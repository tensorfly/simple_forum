package chinaso.com.demo.springboot.Util;

import org.springframework.stereotype.Service;

/**
 * @author fangqian
 * @date 2018/8/31 16:07
 */
@Service("commonUtil")
public class Common {

    public Result result(String status, String msg,Object data){
        //定义返回结果
        Result result = new Result();
        result.setMsg(msg);
        result.setStatus(status);
        if(null == data){
            data = new Object();
        }
        result.setData(data);
        return result;
    }
}
