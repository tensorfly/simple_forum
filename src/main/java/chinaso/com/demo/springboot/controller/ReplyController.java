package chinaso.com.demo.springboot.controller;

import chinaso.com.demo.springboot.Util.DateAndTimeUtil;
import chinaso.com.demo.springboot.Util.RelativeDateFormat;
import chinaso.com.demo.springboot.entity.Reply;
import chinaso.com.demo.springboot.entity.Topic;
import chinaso.com.demo.springboot.service.ReplyService;
import chinaso.com.demo.springboot.service.TopicService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fangqian
 * @date 2018/6/27 15:51
 */
@Controller
@RequestMapping("/reply")
public class ReplyController {
    private static Logger logger = LoggerFactory.getLogger(TopicController.class);
    @Autowired
    ReplyService replyService;

    @Autowired
    TopicService topicService;
    /**
     * @Description:发布帖子
     * @param: Reply
     * @return:String
     */
    @RequestMapping("")
    public String save(Reply reply, Model model) {
        reply.setCreatetime(DateAndTimeUtil.getStringCurrentTime());
        reply.setUpdatetime(DateAndTimeUtil.getStringCurrentTime());
        int count = replyService.addReply(reply);
        Topic topic = topicService.getTopic(reply.getTopicId());
        List<Reply> replys = replyService.findAllByTopicId(reply.getTopicId());
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

    /**
     * @Description:加载我的回帖
     * @param:accountId
     * @return:String
     */
    @RequestMapping("/mylist")
    public String myListTopic(
            @RequestParam(value = "accountId", required = true) String accountId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            Model model) {

        PageInfo<Integer> pageInfo =  replyService.getTopicIds(accountId,pageNum,pageSize);
        List<Topic> topics = new ArrayList<Topic>();
        for(int i=0;i<pageInfo.getList().size();i++){
            Topic topic = topicService.getTopic(pageInfo.getList().get(i));
            topics.add(topic);
        }
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
        model.addAttribute("topics", topics);
        model.addAttribute("headTitle", "我参与的帖子");
        return "topic/mylist";
    }


}
