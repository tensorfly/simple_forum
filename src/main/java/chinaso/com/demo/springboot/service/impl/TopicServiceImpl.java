package chinaso.com.demo.springboot.service.impl;

import chinaso.com.demo.springboot.Util.Common;
import chinaso.com.demo.springboot.Util.Page;
import chinaso.com.demo.springboot.entity.Topic;
import chinaso.com.demo.springboot.mapper.TopicMapper;
import chinaso.com.demo.springboot.service.TopicService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fangqian
 * @date 2018/6/29 9:35
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private static Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    @Qualifier("commonUtil")
    private Common commonUtil;

    @Override
    public PageInfo findAll(String title, int sectionId, String accountId, Integer page, Integer pageSize, int state) {
        PageHelper.startPage(page, pageSize);
        List<Topic> topics = new ArrayList<Topic>();
        topics = topicMapper.getAllTopic(title, accountId, sectionId, state);
        return new PageInfo<>(topics);
    }

    @Override
    public List<Topic> findByAccountId(String accountId) {
        return topicMapper.getTopicByAccountId(accountId);
    }

    @Override
    public int addTopic(Topic topic) {
        topicMapper.insert(topic);
        Topic newTopic = topicMapper.getTopic(topic.getTopicId());
        if (null != newTopic) {
            //发帖成功
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Topic getTopic(int topicId) {
        return topicMapper.getTopic(topicId);
    }

    @Override
    public String deleteTopicByTopicId(int topicId) {
        try {
            topicMapper.deleteByTopicId(topicId);
            return JSON.toJSONString(commonUtil.result("0", "删除成功", null));
        } catch (Exception e) {
            logger.error("删除失败", e);
            return JSON.toJSONString(commonUtil.result("200", "删除失败", null));
        }
    }

    @Override
    public String batchUpdateState(String topicIds, int state) {
        try {
            List<String> strs = Arrays.asList(topicIds.split(","));
            topicMapper.batchUpdateState(strs,state);
            return JSON.toJSONString(commonUtil.result("0", "批量审核成功", null));
        } catch (Exception e) {
            logger.error("批量审核失败", e);
            return JSON.toJSONString(commonUtil.result("200", "批量审核失败", null));
        }
    }

    @Override
    public String batchDeleteTopics(String topicIds) {
        try {
            List<String> strs = Arrays.asList(topicIds.split(","));
            topicMapper.batchDeleteTopics(strs);
            return JSON.toJSONString(commonUtil.result("0", "批量删除成功", null));
        } catch (Exception e) {
            logger.error("批量删除失败", e);
            return JSON.toJSONString(commonUtil.result("200", "批量删除失败", null));
        }
    }

    @Override
    public String findTopicsBySectionId(int sectionId, int pageNo, int pageSize,int state) {
        try {
            PageHelper.startPage(pageNo, pageSize);
            List<Topic> topics = topicMapper.getAllTopic("", null, sectionId, state);
            Page page = null;
                PageInfo pageInfo = new PageInfo<>(topics);
                page = new Page();
                page.setList(pageInfo.getList());
                page.setPageNo(pageInfo.getPageNum());
                page.setPageSize(pageInfo.getPageSize());
                page.setTotalCount(pageInfo.getTotal());
                page.setTotalPage(pageInfo.getPages());
                page.setHasPreviousPage(pageInfo.isHasPreviousPage());
                page.setHasNextPage(pageInfo.isHasNextPage());
            return JSON.toJSONString(commonUtil.result("0", "获取版块列表页数据成功", page));
        } catch (Exception e) {
            logger.error("获取版块id:" + sectionId + "列表页数据失败:", e);
            return JSON.toJSONString(commonUtil.result("200", "获取版块列表页数据失败", null));
        }
    }

    @Override
    public String updateState(int topicId, int state) {
        try {
            topicMapper.updateState(topicId,state);
            return JSON.toJSONString(commonUtil.result("0", "审核成功", null));
        } catch (Exception e) {
            logger.error("更新帖子id:" + topicId + "状态失败:", e);
            return JSON.toJSONString(commonUtil.result("200", "更新帖子状态失败", null));
        }
    }


    public int[] StringArrayToIntArray(String[] arrs){
        int[] ints = new int[arrs.length];
        for(int i=0;i<arrs.length;i++){
            ints[i] = Integer.parseInt(arrs[i]);
        }
        return ints;
    }
}
