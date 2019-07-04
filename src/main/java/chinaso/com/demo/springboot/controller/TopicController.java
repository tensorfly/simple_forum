package chinaso.com.demo.springboot.controller;

import chinaso.com.demo.springboot.Util.DateAndTimeUtil;
import chinaso.com.demo.springboot.Util.RelativeDateFormat;
import chinaso.com.demo.springboot.entity.Reply;
import chinaso.com.demo.springboot.entity.Section;
import chinaso.com.demo.springboot.entity.Topic;
import chinaso.com.demo.springboot.service.ReplyService;
import chinaso.com.demo.springboot.service.SectionService;
import chinaso.com.demo.springboot.service.TopicService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * @author fangqian
 * @date 2018/6/27 15:51
 */
@Controller
@RequestMapping("/topic")
public class TopicController {
    private static Logger logger = LoggerFactory.getLogger(TopicController.class);
    @Autowired
    TopicService topicService;

    @Autowired
    ReplyService replyService;

    @Autowired
    SectionService sectionService;

    /**
     * @Description:加载帖子列表
     * @param:title
     * @return:String
     */
    @RequestMapping("/list")
    public String listTopic (
            @RequestParam(value = "title", required = false,defaultValue = "") String title,
            //sectionId为0表示查询全部
            @RequestParam(value = "sectionId", required = false,defaultValue = "0") int sectionId,
            //默认查询审核通过的帖子
            @RequestParam(value = "state", defaultValue = "0") int state,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
            Model model) {

        //title不为空时查solr，为空的时候查数据库

        PageInfo<Topic> pageInfo= topicService.findAll(title,sectionId,null,pageNum,pageSize,state);
        List<Topic> topics = pageInfo.getList();
        try {
            for(int i=0;i<topics.size();i++){
                String time =RelativeDateFormat.format(topics.get(i).getCreatetime());
                topics.get(i).setCreatetime(time);
            }
        }catch (ParseException e){
            logger.error("时间转换异常",e);
        }
        List<Section> sections = sectionService.getSections();
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
        model.addAttribute("topics", topics);
        model.addAttribute("sections", sections);
        model.addAttribute("sectionId", sectionId);
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
            //默认查询审核通过的帖子
            @RequestParam(value = "state", defaultValue = "0") int state,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            Model model) {
        PageInfo<Topic> pageInfo= topicService.findAll(title,0,accountId,pageNum,pageSize,state);
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
        List<Section> sections = sectionService.getSections();
        model.addAttribute("flag",flag);
        model.addAttribute("accountId",accountId);
        model.addAttribute("sections", sections);
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
        //默认审核通过
        topic.setState(0);
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
        List<Reply> replys = replyService.findAllByTopicId(topicId);
        try {
            if(null != topic){
                String topicTime = RelativeDateFormat.format(topic.getCreatetime());
                topic.setCreatetime(topicTime);
            }
            for(int i=0;i<replys.size();i++){
                String time =RelativeDateFormat.format(replys.get(i).getCreatetime());
                replys.get(i).setCreatetime(time);
            }
        }catch (ParseException e){
            logger.error("时间转换异常",e);
        }
        model.addAttribute("topic",topic);
        model.addAttribute("replys",replys);
        return "topic/detail_new";
    }

}
