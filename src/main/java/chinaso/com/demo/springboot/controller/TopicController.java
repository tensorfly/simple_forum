package chinaso.com.demo.springboot.controller;

import chinaso.com.demo.springboot.entity.Topic;
import chinaso.com.demo.springboot.entity.User;
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
     * @param:
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


    //发表帖子
    /**
     * @Description:进入发帖页面
     * @param:
     * @return:String
     */
    @RequestMapping("/toPublish")
    public String publishTopic() {
        return "topic/pulish";
    }

    /**
     * @Description:发布帖子
     * @param: Topic
     * @return:String
     */
    @RequestMapping("/publish")
    public String save(Topic topic, Model model) {
        return null;
    }


}
