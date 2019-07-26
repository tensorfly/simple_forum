package chinaso.com.demo.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author fangqian
 * @date 2019/7/26 10:06
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger("CommonController.class");

    @Value("${staticFileDir}")
    private String staticFileDir;

    /**
     *下载资源文件
     * @param fileName 文件名
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/download/{fileName}", method = { RequestMethod.GET, RequestMethod.POST }
            , produces = { "application/octet-stream;charset=UTF-8" })
    @ResponseBody
    public Object getDownloadFile(
            @PathVariable("fileName") String fileName,
            HttpServletRequest request, HttpServletResponse response) {
        String path = staticFileDir;
        logger.info("下载路径path:"+path);
        if (fileName != null) {
            //设置文件路径
            File file = new File(path , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                response.setContentLengthLong(file.length());
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        logger.error(fileName+"资源文件不存在");
        return "资源文件不存在";
    }

}
