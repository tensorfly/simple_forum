package chinaso.com.demo.springboot.entity;

/**
 * @author fangqian
 * @date 2018/6/27 17:35
 */
public class Reply {

    //回帖评论ID
    private int replyId;
    //回帖内容
    private String content;
    //帖子ID
    private int topicId;
    //发帖人ID
    private String accountId;
    //创建时间
    private String createtime;
    //更新时间
    private String updatetime;

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
}
