package cn.yu.util.response;

import java.util.List;

/**
 * @author bin.yu
 * @create 2018-07-18 8:38
 **/
public class PageInfo<T> {

    //对象列表
    private List<T> list;

    public Long pageSize;
    public Long pageNo;
    public Long totalNum;
    public Long totalPage;


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getTotalPage() {
        if (totalNum != null && pageSize != null) {
            totalPage = (long)Math.ceil( (double)totalNum/(double)this.pageSize);
        }
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }
}
