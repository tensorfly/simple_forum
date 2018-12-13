package chinaso.com.demo.springboot.entity;

/**
 * @author fangqian
 * @date 2018/6/27 17:35
 */
public class Topic {

    //帖子ID
    private int topicId;
    //帖子标题
    private String title;
    //帖子内容
    private String content;
    //发帖人ID
    private String accountId;
    //版块Id
    private int sectionId;
    //版块名称
    private String sectionName;
    //状态 0：默认审核通过；1：审核不通过
    private int state;
    //创建时间
    private String createtime;
    //更新时间
    private String updatetime;

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
