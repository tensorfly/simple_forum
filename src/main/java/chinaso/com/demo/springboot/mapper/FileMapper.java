package chinaso.com.demo.springboot.mapper;

import chinaso.com.demo.springboot.entity.File;
import chinaso.com.demo.springboot.entity.Reply;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * @author fangqian
 * @date 2018/11/30 15:30
 */
@Mapper
public interface FileMapper {

    @Insert("INSERT INTO file(name,realname,filelength,suffixName,createtime,updatetime) VALUES(#{name}, #{realname}, #{filelength},#{suffixName},#{createtime}, #{updatetime})")
    @Options(useGeneratedKeys=true, keyProperty="fid", keyColumn="fid")
    void insert(File file);

    @Select("SELECT * FROM file WHERE fid = #{fid}")
    File getFileInfo(@Param("fid") int fid);
}
