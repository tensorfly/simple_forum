package chinaso.com.demo.springboot.service;

import chinaso.com.demo.springboot.entity.Section;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fangqian
 * @date 2018/11/15 10:32
 */
public interface SectionService {

    List<Section> getSections();

    String getSectionsJson();

    String deleteBySectionId(int sectionId);

}
