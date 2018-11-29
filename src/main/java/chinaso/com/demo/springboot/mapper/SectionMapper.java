package chinaso.com.demo.springboot.mapper;

import chinaso.com.demo.springboot.entity.Section;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 版块管理
 * @author fangqian
 * @date 2018/11/15 10:28
 */
@Mapper
public interface SectionMapper {

    //获取所有版块信息
    @Select("SELECT * FROM section order by createtime desc")
    List<Section> getSections();

    @Delete("delete from section where sectionId=#{sectionId}")
    void deleteBySectionId(@Param("sectionId") int sectionId);

    @Delete("delete from section where sectionId=#{sectionId}")
    void deleteBySectionIds(int[] sectionIds);
}
