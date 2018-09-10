package com.coder520.common.page;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:分页前端传递参数  一个每页多少条数据   用户点击的第几页
 * 后端拿到  根据查询条件 计算总条目数  根据总条目数和 每页多少条数据 算出一共分为几页
 * 查询数据 用户点击的第几页 乘 每页多少条 算出limit的起始值 每页多少条数据
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/9 0:10
 */
public class PageQueryBean {

    private static final int DEFAULT_PAGE_SIZE = 10;
    /** 当前页 */
    private Integer currentPage;
    /** 每页显示数据条数 */
    private Integer pageSize;
    /** 所有记录数 sql count(0)*/
    private int totalRows;
    /** sql查询起始行 */
    private Integer startRow;
    /** 总页数 */
    private Integer totalPage;
    /** 查询所得数据集 */
    private List<?> items;

    @Override
    public String toString() {
        return "PageQueryBean{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalRows=" + totalRows +
                ", startRow=" + startRow +
                ", totalPage=" + totalPage +
                ", items=" + items +
                '}';
    }



    public final Integer getStartRow() {
        if (startRow == null)
            startRow = (currentPage == null ? 0 : (currentPage - 1) * getPageSize()) ;
        return startRow;
    }


    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public final Integer getPageSize() {
        return pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public final void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public final int getTotalRows() {
        return totalRows;
    }

    public final void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        int totalPage = totalRows % getPageSize() == 0 ? totalRows / getPageSize() : totalRows / getPageSize() + 1;
        setTotalPage(totalPage);
    }

    public final List<?> getItems() {
        return items == null ? Collections.EMPTY_LIST : items;
    }

    public final void setItems(List<?> items) {
        this.items = items;
    }

    public final Integer getCurrentPage() {
        return currentPage;
    }

    public final void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public final Integer getTotalPage() {
        return totalPage == null || totalPage == 0 ? 1 : totalPage;
    }

    public final void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
