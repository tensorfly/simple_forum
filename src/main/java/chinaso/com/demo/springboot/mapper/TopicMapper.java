package chinaso.com.demo.springboot.mapper;

import chinaso.com.demo.springboot.entity.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author fangqian
 * @date 2018/6/29 9:37
 */
@Mapper
public interface TopicMapper {
    @Select("SELECT * FROM topic WHERE topicId = #{topicId}")
    @Results({
            @Result(property = "topicId",  column = "topicId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content",  column = "content"),
            @Result(property = "accountId", column = "accountId"),
            @Result(property = "createtime",  column = "createtime"),
            @Result(property = "updatetime", column = "updatetime"),
    })
    Topic getTopic(@Param("topicId") int topicId);

    @Insert("INSERT INTO topic(title,content,accountId,createtime,updatetime) VALUES(#{title}, #{content}, #{accountId}, #{createtime}, #{updatetime})")
    @Options(useGeneratedKeys=true, keyProperty="topicId", keyColumn="topicId")
    void insert(Topic topic);

    @Select("SELECT * FROM topic order by createtime")
    @Results({
            @Result(property = "topicId",  column = "topicId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content",  column = "content"),
            @Result(property = "accountId", column = "accountId"),
            @Result(property = "createtime",  column = "createtime"),
            @Result(property = "updatetime", column = "updatetime"),
    })
    List<Topic> getAllTopic();

    @Select("SELECT * FROM topic WHERE accountId = #{accountId} order by createtime")
    @Results({
            @Result(property = "topicId",  column = "topicId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content",  column = "content"),
            @Result(property = "accountId", column = "accountId"),
            @Result(property = "createtime",  column = "createtime"),
            @Result(property = "updatetime", column = "updatetime"),
    })
    List<Topic> getTopicByAccountId(@Param("accountId") String accountId);

    @Select("SELECT * FROM topic WHERE title like '%${title}%' order by createtime")
    @Results({
            @Result(property = "topicId",  column = "topicId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content",  column = "content"),
            @Result(property = "accountId", column = "accountId"),
            @Result(property = "createtime",  column = "createtime"),
            @Result(property = "updatetime", column = "updatetime"),
    })
    List<Topic> getTopicByTitle(@Param("title") String title);

}
