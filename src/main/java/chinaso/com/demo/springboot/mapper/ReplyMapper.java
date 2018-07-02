package chinaso.com.demo.springboot.mapper;

import chinaso.com.demo.springboot.entity.Reply;
import chinaso.com.demo.springboot.entity.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author fangqian
 * @date 2018/6/29 9:37
 */
@Mapper
public interface ReplyMapper {
    @Select("SELECT * FROM reply WHERE replyId = #{replyId}")
    Reply getReply(@Param("replyId") int replyId);

    @Insert("INSERT INTO reply(content,accountId,topicId,createtime,updatetime) VALUES(#{content}, #{accountId}, #{topicId}, #{createtime}, #{updatetime})")
    @Options(useGeneratedKeys=true, keyProperty="replyId", keyColumn="replyId")
    void insert(Reply reply);

    @Select("SELECT * FROM reply WHERE topicId = #{topicId} order by createtime desc")
    List<Reply> getAllReply(@Param("topicId") int topicId);

    @Select("SELECT topicId FROM reply WHERE accountId = #{accountId}")
    List<Integer> getTopicIds(@Param("accountId") String accountId);
}
