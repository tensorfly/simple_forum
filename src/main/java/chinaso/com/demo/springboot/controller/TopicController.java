package chinaso.com.demo.springboot.controller;

import chinaso.com.demo.springboot.Util.DateAndTimeUtil;
import chinaso.com.demo.springboot.entity.Reply;
import chinaso.com.demo.springboot.entity.Topic;
import chinaso.com.demo.springboot.service.ReplyService;
import chinaso.com.demo.springboot.service.TopicService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    ReplyService replyService;

    /**
     * @Description:加载帖子列表
     * @param:title
     * @return:String
     */
    @RequestMapping("/list")
    public String listTopic(
            @RequestParam(value = "title", required = false,defaultValue = "") String title,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
            Model model) {

        PageInfo<Topic> pageInfo= topicService.findAll(title,null,pageNum,pageSize);
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
        return "index_new";
    }

    /**
     * @Description:加载我的帖子列表
     * @param:accountId
     * @return:String
     */
    @RequestMapping("/mylist")
    public String myListTopic(
            @RequestParam(value = "accountId", required = true) String accountId,
            @RequestParam(value = "title", required = false,defaultValue = "") String title,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            Model model) {
        PageInfo<Topic> pageInfo= topicService.findAll(title,accountId,pageNum,pageSize);
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
        model.addAttribute("accountId",accountId);
        model.addAttribute("topics", pageInfo.getList());
        model.addAttribute("headTitle", "我发表的帖子");
        return "topic/mylist";
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
        return "topic/publish_new";
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
    public String save(Model model,int topicId,HttpServletRequest request) {
        Topic topic = topicService.getTopic(topicId);
        model.addAttribute("topic",topic);
        List<Reply> replys = replyService.findAllByTopicId(topicId);
        model.addAttribute("replys",replys);
        return "topic/detail_new";
    }

}
