package chinaso.com.demo.springboot.service;

import chinaso.com.demo.springboot.entity.Topic;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author fangqian
 * @date 2018/6/27 16:44
 */
public interface TopicService {
    public PageInfo findAll(String title,Integer page, Integer pageSize);

    public List<Topic> findByAccountId(String accountId);

    public int addTopic(Topic topic);

    public Topic getTopic(int topicId);
}
