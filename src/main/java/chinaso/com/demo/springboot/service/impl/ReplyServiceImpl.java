package chinaso.com.demo.springboot.service.impl;

import chinaso.com.demo.springboot.entity.Reply;
import chinaso.com.demo.springboot.entity.Topic;
import chinaso.com.demo.springboot.mapper.ReplyMapper;
import chinaso.com.demo.springboot.service.ReplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fangqian
 * @date 2018/7/2 14:06
 */
@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyMapper replyMapper;

    @Override
    public List<Reply> findAllByTopicId(int topicId) {
        return replyMapper.getAllReply(topicId);
    }

    @Override
    public int addReply(Reply reply) {
        replyMapper.insert(reply);
        Reply newReply = replyMapper.getReply(reply.getTopicId());
        if(null != newReply){
            //发帖成功
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public PageInfo<Integer> getTopicIds(String accountId,Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Integer> topicIds = replyMapper.getTopicIds(accountId);
        return new PageInfo<>(topicIds);
    }
}
