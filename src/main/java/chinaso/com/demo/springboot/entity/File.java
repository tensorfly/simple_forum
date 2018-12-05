package chinaso.com.demo.springboot.entity;

import java.util.Date;

/**
 * @author fangqian
 * @date 2018/11/30 14:47
 */
public class File {
    //自增id
    private int fid;
    //文件名
    private String name;
    //文件真实名
    private String realname;
    //文件大小
    private Long filelength;
    //文件后缀
    private String suffixName;
    //图片url;下载地址
    private String url;
    //创建时间
    private Date createtime;
    //更新时间
    private Date updatetime;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getFilelength() {
        return filelength;
    }

    public void setFilelength(Long filelength) {
        this.filelength = filelength;
    }

    public String getSuffixName() {
        return suffixName;
    }

    public void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
