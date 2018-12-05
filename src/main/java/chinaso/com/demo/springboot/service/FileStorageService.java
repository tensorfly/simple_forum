package chinaso.com.demo.springboot.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author fangqian
 * @date 2018/11/30 14:56
 */
public interface FileStorageService {
    String uploadFileAndSrorage(MultipartFile file);
}
