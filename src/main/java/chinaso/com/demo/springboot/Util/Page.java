package chinaso.com.demo.springboot.Util;

import java.io.Serializable;
import java.util.List;

/**
 * @author fangqian
 * @date 2018/11/9 15:34
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    //当前页码
    private int pageNo;
    //当前页码数量
    private int pageSize;
    //总数量
    private Long totalCount;
    //总页数
    private int totalPage;
    //是否有下一页
    private boolean hasNextPage;
    //是否有上一页
    private boolean hasPreviousPage;
    //list
    private List<T> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }
}
