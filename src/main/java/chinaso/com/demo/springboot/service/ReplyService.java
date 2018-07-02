package chinaso.com.demo.springboot.service;

import chinaso.com.demo.springboot.entity.Reply;
import chinaso.com.demo.springboot.entity.Topic;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author fangqian
 * @date 2018/6/27 16:44
 */
public interface ReplyService {

    public List<Reply> findAllByTopicId(int topicId);

    public int addReply(Reply reply);

    public PageInfo<Integer> getTopicIds(String accountId, Integer page, Integer pageSize);

}
