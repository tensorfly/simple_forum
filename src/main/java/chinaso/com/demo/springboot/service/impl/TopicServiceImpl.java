package chinaso.com.demo.springboot.service.impl;

import chinaso.com.demo.springboot.entity.Topic;
import chinaso.com.demo.springboot.entity.User;
import chinaso.com.demo.springboot.mapper.TopicMapper;
import chinaso.com.demo.springboot.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fangqian
 * @date 2018/6/29 9:35
 */
@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicMapper topicMapper;

    @Override
    public List<Topic> findAll() {
        return topicMapper.getAllTopic();
    }

    @Override
    public List<Topic> findByAccountId(String accountId) {
        return topicMapper.getTopicByAccountId(accountId);
    }

    @Override
    public List<Topic> findByTitle(String title) {
        return topicMapper.getTopicByTitle(title);
    }

    @Override
    public int addTopic(Topic topic) {
        topicMapper.insert(topic);
        Topic newTopic = topicMapper.getTopic(topic.getTopicId());
        if(null != newTopic){
            //发帖成功
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public Topic getTopic(int topicId) {
        return topicMapper.getTopic(topicId);
    }
}
