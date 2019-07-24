package chinaso.com.demo.springboot.controller;

import chinaso.com.demo.springboot.entity.Section;
import chinaso.com.demo.springboot.service.AdminService;
import chinaso.com.demo.springboot.service.SectionService;
import chinaso.com.demo.springboot.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fangqian
 * @date 2018/11/16 10:14
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    SectionService sectionService;

    @Autowired
    TopicService topicService;

    /**
     * @Description:进入超级管理员登陆页面
     * @param:
     * @return:String
     */
    @RequestMapping("/toLogin")
    public String adminLoginIndex(){
        return "admin/login";
    }

    @RequestMapping(value = "/login", method = {
            RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String adminLogin(
            @RequestParam(value = "username",required = true) String username,
            @RequestParam(value = "password",required = true) String password,
            @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
            HttpServletRequest request, HttpServletResponse response){
        String result = adminService.login(username,password,request);
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }

    @RequestMapping("/section/listPage")
    public String sectionListPage(Model model){
        return "section/list";
    }

    @RequestMapping("/topic/listPage")
    public String topicListPage(Model model){
        return "topic/list";
    }

    @RequestMapping(value = "/section/list", method = {
            RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object sectionList(
            @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
            HttpServletResponse response, HttpServletRequest request) {

        String result = sectionService.getSectionsJson();
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }

    //删除版块
    @RequestMapping(value = "/section/delete/{sectionId}", method = {
            RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deleteSection(
            @PathVariable(value = "sectionId",required = true) int sectionId,
            @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
            HttpServletRequest request, HttpServletResponse response){
        String result = sectionService.deleteBySectionId(sectionId);
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }


    //新增版块
    @RequestMapping(value = "/section/add", method = {
            RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addSection(
            @RequestParam(value = "title",required = true) String title,
            @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
            HttpServletRequest request, HttpServletResponse response){
        String result = sectionService.addSection(title);
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }

    //编辑版块
    @RequestMapping(value = "/section/edit", method = {
            RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String editSection(
            @RequestParam(value = "sectionId",required = true) int sectionId,
            @RequestParam(value = "title",required = true) String title,
            @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
            HttpServletRequest request, HttpServletResponse response){
        String result = sectionService.editSection(sectionId,title);
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }


    //单条删除
    @RequestMapping(value = "/topic/delete/{topicId}", method = {
            RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deleteTopic(
            @PathVariable(value = "topicId",required = true) int topicId,
            @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
            HttpServletRequest request, HttpServletResponse response){
        String result = topicService.deleteTopicByTopicId(topicId);
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }

    //管理员根据版块id获取话题
    @RequestMapping(value = "/getTopics/{sectionId}", method = {
            RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getTopicBySectionId( @PathVariable(value = "sectionId",required = true) int sectionId,
                                       @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                       @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                       //默认查询所有帖子
                                       @RequestParam(value = "state", defaultValue = "9") int state,
                                       @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
                                       HttpServletRequest request, HttpServletResponse response){
        String result = topicService.findTopicsBySectionId(sectionId,pageNo,pageSize,state);
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }

    //批量删除
    @RequestMapping(value = "/topic/batch/delete", method = {
            RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String batchDeleteTopic( @RequestParam(value = "topicIds",required = true) String topicIds,
                                   @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
                                   HttpServletRequest request, HttpServletResponse response){
        String result = topicService.batchDeleteTopics(topicIds);
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }

    //审核0：审核通过；1：审核不通过；2：默认待审核
    @RequestMapping(value = "/topic/check/{topicId}", method = {
            RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String checkTopic(
            @PathVariable(value = "topicId",required = true) int topicId,
            @RequestParam(value = "state",required = true) int state,
            @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
            HttpServletRequest request, HttpServletResponse response){
        String result = topicService.updateState(topicId,state);
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }

    //审核0：审核通过；1：审核不通过；2：默认待审核
    @RequestMapping(value = "/topic/batch/check", method = {
            RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String batchCheckTopic(
            @RequestParam(value = "topicIds",required = true) String topicIds,
            @RequestParam(value = "state",required = true) int state,
            @RequestParam(value = "jsonpcallback", required = false) String jsonpcallback,
            HttpServletRequest request, HttpServletResponse response){

        String result = topicService.batchUpdateState(topicIds,state);
        if (jsonpcallback != null) {
            return jsonpcallback + "(" + result + ")";
        }
        return result;
    }

}
