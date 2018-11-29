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
     PageInfo findAll(String title,int sectionId, String accountId,Integer page, Integer pageSize,int state);

     List<Topic> findByAccountId(String accountId);

     int addTopic(Topic topic);

     Topic getTopic(int topicId);

    String deleteTopicByTopicId(int topicId);

    String batchDeleteTopics(String topicIds);

    String batchUpdateState(String topicIds,int state);

    String findTopicsBySectionId(int sectionId,int pageNo, int pageSize,int state);

    String updateState(int topicId,int state);
}
