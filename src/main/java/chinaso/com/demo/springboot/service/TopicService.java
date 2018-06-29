package chinaso.com.demo.springboot.service;

import chinaso.com.demo.springboot.entity.Topic;

import java.util.List;

/**
 * @author fangqian
 * @date 2018/6/27 16:44
 */
public interface TopicService {
    public List<Topic> findAll();

    public List<Topic> findByAccountId(String accountId);

    public List<Topic> findByTitle(String title);

    public int addTopic(Topic topic);

    public Topic getTopic(int topicId);
}
