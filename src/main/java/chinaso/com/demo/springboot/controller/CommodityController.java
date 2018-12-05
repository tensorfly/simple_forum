package chinaso.com.demo.springboot.controller;

import chinaso.com.demo.springboot.mapper.FileMapper;
import chinaso.com.demo.springboot.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author fangqian
 * @date 2018/11/30 11:02
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController {

    private static final Logger logger = LoggerFactory.getLogger("CommodityController.class");

    @Autowired
    FileStorageService fileStorageService;

    @Value("${fileUploadDir}")
    private String fileUploadDir;

    @Autowired
    FileMapper fileMapper;

    @RequestMapping(value = "/upload/editor/img", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String uploadEditorImg(
            @RequestParam("img") MultipartFile file,
            @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
            HttpServletRequest request, HttpServletResponse response){

        String result = fileStorageService.uploadFileAndSrorage(file);
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }

    //显示指定的文件
    @RequestMapping(value = "/image/view/{fid}", method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void serveFile(
            @PathVariable int fid,
            HttpServletRequest request, HttpServletResponse response) {
//使用字节流读取本地图片
        ServletOutputStream out=null;
        BufferedInputStream buf=null;
        //创建一个文件对象，对应的文件就是python把词云图片生成后的路径以及对应的文件名
        chinaso.com.demo.springboot.entity.File fileInfo = fileMapper.getFileInfo(fid);
            String filePath = fileUploadDir + fileInfo.getName() + fileInfo.getSuffixName();
            File file = new File(filePath);
        try {
            //使用输入读取缓冲流读取一个文件输入流
            buf=new BufferedInputStream(new FileInputStream(file));
            //利用response获取一个字节流输出对象
            out=response.getOutputStream();
            //定义个数组，由于读取缓冲流中的内容
            byte[] buffer=new byte[1024];
            //while循环一直读取缓冲流中的内容到输出的对象中
            while(buf.read(buffer)!=-1) {
                out.write(buffer);
            }
            //写出到请求的地方
            out.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
