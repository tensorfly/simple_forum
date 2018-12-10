package chinaso.com.demo.springboot.mapper;

import chinaso.com.demo.springboot.entity.Section;
import chinaso.com.demo.springboot.entity.Topic;
import chinaso.com.demo.springboot.entity.User;
import org.apache.ibatis.annotations.*;

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

    @Insert("INSERT INTO section(title,createtime,updatetime) VALUES(#{title},#{createtime}, #{updatetime})")
    @Options(useGeneratedKeys=true, keyProperty="sectionId", keyColumn="sectionId")
    void addSection(Section section);

    @Delete("delete from section where sectionId=#{sectionId}")
    void deleteBySectionId(@Param("sectionId") int sectionId);

    @Delete("delete from section where sectionId=#{sectionId}")
    void deleteBySectionIds(int[] sectionIds);

    @Select("SELECT * FROM section where title=#{title}")
    List<Section> getSectionsByTitlle(@Param("title") String title);

    @Select("SELECT * FROM section where sectionId=#{sectionId}")
    Section getSectionsBySectionId(@Param("sectId") int  sectionId);

    @Update("UPDATE section SET title=#{title} WHERE sectionId =#{sectionId}")
    void updateSection(@Param("sectionId") int  sectionId,@Param("title") String title);
}
