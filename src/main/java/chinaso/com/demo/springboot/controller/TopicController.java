package chinaso.com.demo.springboot.controller;

import chinaso.com.demo.springboot.Util.DateAndTimeUtil;
import chinaso.com.demo.springboot.entity.Topic;
import chinaso.com.demo.springboot.service.TopicService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listTopic(
            @RequestParam(value = "title", required = false,defaultValue = "") String title,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize,
            Model model) {
        List<Topic> topics;

        PageInfo<Topic> pageInfo = new PageInfo<Topic>();
            pageInfo= topicService.findAll(title,pageNum,pageSize);
        //获得当前页
        model.addAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        model.addAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        model.addAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        model.addAttribute("isLastPage", pageInfo.isIsLastPage());
        model.addAttribute("title",title);
        model.addAttribute("topics", pageInfo.getList());
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
     * @Description:帖子详情页
     * @param:topicId
     * @return:String
     */
    @RequestMapping("/detail")
    public String save(Model model,int topicId) {
        Topic topic = topicService.getTopic(topicId);
        model.addAttribute("topic",topic);
        return "topic/detail";
    }

}
