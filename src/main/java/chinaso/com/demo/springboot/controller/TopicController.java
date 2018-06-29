package chinaso.com.demo.springboot.controller;

import chinaso.com.demo.springboot.Util.DateAndTimeUtil;
import chinaso.com.demo.springboot.entity.Topic;
import chinaso.com.demo.springboot.service.TopicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author fangqian
 * @date 2018/6/27 15:51
 */
@Controller
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    TopicService topicService;

    /**
     * @Description:加载帖子列表
     * @param:title
     * @return:String
     */
    @RequestMapping("/list")
    public String listTopic(Model model,String title) {
        List<Topic> topics;
        if(title == null || StringUtils.isEmpty(title)){
            title = "";
            topics= topicService.findAll();
        }else {
            topics = topicService.findByTitle(title);
        }
        model.addAttribute("title",title);
        model.addAttribute("topics", topics);
        return "index";
    }


    /**
     * @Description:进入发帖页面
     * @param:accountId
     * @return:String
     */
    @RequestMapping("/toPublish")
    public String publishTopic(Model model,String accountId) {
        boolean flag = true;
        if(accountId == null || StringUtils.isEmpty(accountId)){
            flag = false;
        }
        model.addAttribute("flag",flag);
        model.addAttribute("accountId",accountId);
        return "topic/publish";
    }

    /**
     * @Description:发布帖子
     * @param: Topic
     * @return:String
     */
    @RequestMapping("/publish")
    public String save(Topic topic, Model model) {
        topic.setCreatetime(DateAndTimeUtil.getStringCurrentTime());
        topic.setUpdatetime(DateAndTimeUtil.getStringCurrentTime());
        int count = topicService.addTopic(topic);
        return "redirect:/";
    }

    /**
     * @Description:帖子详情
     * @param:
     * @return:String
     */
    @RequestMapping("/detail")
    public String save(Model model,int topicId) {
        Topic topic = topicService.getTopic(topicId);
        model.addAttribute("topic",topic);
        return "topic/detail";
    }

}
