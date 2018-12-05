package chinaso.com.demo.springboot.service.impl;

import chinaso.com.demo.springboot.Util.CeateUUIDUtil;
import chinaso.com.demo.springboot.Util.Common;
import chinaso.com.demo.springboot.Util.FileUtil;
import chinaso.com.demo.springboot.Util.WangEditor;
import chinaso.com.demo.springboot.entity.File;
import chinaso.com.demo.springboot.mapper.FileMapper;
import chinaso.com.demo.springboot.service.FileStorageService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Random;

/**
 * @author fangqian
 * @date 2018/11/30 14:58
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger("FileStorageServiceImpl.class");

    @Autowired
    @Qualifier("commonUtil")
    Common commonUtil;

    @Value("${fileUploadDir}")
    private String fileUploadDir;

    @Value("${imageViewUrl}")
    private String imageViewUrl;

    @Autowired
    FileMapper fileMapper;

    @Override
    public String uploadFileAndSrorage(MultipartFile file) {
        try {
            //判断文件是否为空
            if (file.isEmpty()) {
                return JSON.toJSONString(commonUtil.result("200", "文件为空", null));
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            //重命名文件名
            String saveFileNme = CeateUUIDUtil.generate();

            // 设置文件存储路径
            String path = fileUploadDir + saveFileNme + suffixName;

            boolean flag = FileUtil.uploadFile(file,path);
            if(flag){
                File uploadFileInfo = new File();
                Date now = new Date();
                uploadFileInfo.setName(saveFileNme);
                uploadFileInfo.setRealname(fileName);
                uploadFileInfo.setFilelength(file.getSize());
                uploadFileInfo.setSuffixName(suffixName);
                uploadFileInfo.setCreatetime(now);
                uploadFileInfo.setUpdatetime(now);
                fileMapper.insert(uploadFileInfo);
                String url = imageViewUrl + uploadFileInfo.getFid();
                uploadFileInfo.setUrl(url);
                String [] str = {url};
                WangEditor we = new WangEditor(str);
                return JSON.toJSONString(we);
            }else {
                return JSON.toJSONString(commonUtil.result("80", "文件上传失败", null));
            }

        } catch (Exception e) {
            logger.error("编辑器上传文件失败", e);
            return JSON.toJSONString(commonUtil.result("200", "上传文件失败", null));
        }
    }
}
