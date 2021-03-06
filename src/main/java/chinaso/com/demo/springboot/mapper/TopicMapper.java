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
    @Select("SELECT a.*,b.title as sectionName FROM topic a,section b WHERE a.sectionId =b.sectionId and  a.topicId = #{topicId}")
    @Results({
            @Result(property = "topicId",  column = "topicId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content",  column = "content"),
            @Result(property = "accountId", column = "accountId"),
            @Result(property = "createtime",  column = "createtime"),
            @Result(property = "updatetime", column = "updatetime"),
    })
    Topic getTopic(@Param("topicId") int topicId);

    @Insert("INSERT INTO topic(title,content,accountId,sectionId,state,isTop,toptime,createtime,updatetime) VALUES(#{title}, #{content}, #{accountId},#{sectionId},#{state},#{isTop},#{toptime}, #{createtime}, #{updatetime})")
    @Options(useGeneratedKeys=true, keyProperty="topicId", keyColumn="topicId")
    void insert(Topic topic);

    @Select("<script>"+
            "SELECT * FROM topic"+
            "<where> 1=1"+
            "<bind name='title' value=\"'%' + title + '%'\" />"+
            "<if test='title != null'> AND title like #{title}</if>"+
            "<if test='sectionId != 0'> AND sectionId = #{sectionId}</if>"+
            "<if test='accountId != null'> AND accountId = #{accountId}</if>"+
            "<if test='state != 9'> AND state = #{state}</if>"+
            "</where>"+
            "order by istop desc,toptime desc"+
            "</script>")
    List<Topic> getAllTopic(@Param("title") String title,@Param("accountId") String accountId,@Param("sectionId") int sectionId,@Param("state") int state);

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
            @Result(property = "sectionId", column = "sectionId"),
            @Result(property = "state", column = "state"),
            @Result(property = "createtime",  column = "createtime"),
            @Result(property = "updatetime", column = "updatetime"),
    })
    List<Topic> getTopicByAccountId(@Param("accountId") String accountId);


    @Delete("delete from topic where topicId=#{topicId}")
    int deleteByTopicId(@Param("topicId") int topicId);

    @DeleteProvider(type = SQLTopicProvider.class, method = "deleteTopics")
    int batchDeleteTopics(@Param("topicIds")  List<String> topicIds);

    @Update("update topic set state = #{state} where topicId=#{topicId}")
    int updateState(@Param("topicId") int topicId,@Param("state") int state);

    @UpdateProvider(type = SQLTopicProvider.class, method = "batchUpdateState")
    int batchUpdateState(@Param("topicIds") List<String> topicIds,@Param("state") int state);

    @Update("update topic set isTop = #{isTop},toptime = #{toptime} where topicId=#{topicId}")
    int updateTopState(@Param("topicId") int topicId,@Param("isTop") int isTop,@Param("toptime") String toptime);

    //查出超级置顶那条数据
    @Select("SELECT * FROM topic WHERE isTop = 10")
    Topic getTopOne();

}
