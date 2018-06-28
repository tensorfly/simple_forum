package chinaso.com.demo.springboot.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fangqian
 * @date 2018/6/28 13:55
 */
public class DateAndTimeUtil {
    /*
     * 获取系统当前时间
     */
    public static String  getStringCurrentTime(){
        Date time=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }
}
