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

    @Select("<script>"+
            "SELECT * FROM topic"+
            "<where> 1=1"+
            "<bind name='title' value=\"'%' + title + '%'\" />"+
            "<if test='title != null'> AND title like #{title}</if>"+
            "<if test='accountId != null'> AND accountId = #{accountId}</if>"+
            "</where>"+
            "order by createtime desc"+
            "</script>")
    List<Topic> getAllTopic(@Param("title") String title,@Param("accountId") String accountId);

    /*
     * 查出所有帖子数量
     */
    @Select("SELECT count(*) FROM topic")
    int countTopics();

    @Select("SELECT * FROM topic WHERE accountId = #{accountId} order by createtime desc")
    @Results({
            @Result(property = "topicId",  column = "topicId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content",  column = "content"),
            @Result(property = "accountId", column = "accountId"),
            @Result(property = "createtime",  column = "createtime"),
            @Result(property = "updatetime", column = "updatetime"),
    })
    List<Topic> getTopicByAccountId(@Param("accountId") String accountId);

}
