/**
 * PageUtil.java
 * 该类提供分页相关的工具方法
 */
package com.achievement.util;

import java.util.List;

public class PageUtil<T> {
    // 当前页码
    private int page;
    // 每页显示的记录数
    private int pageSize;
    // 总记录数
    private long totalItems;
    // 总页数
    private int totalPages;
    // 当前页的记录列表
    private List<T> items;

    /**
     * 构造方法
     * @param page 当前页码
     * @param pageSize 每页显示的记录数
     * @param totalItems 总记录数
     * @param items 当前页的记录列表
     */
    public PageUtil(int page, int pageSize, long totalItems, List<T> items) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.items = items;
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
    }

    /**
     * 获取当前页码
     * @return 当前页码
     */
    public int getPage() {
        return page;
    }

    /**
     * 设置当前页码
     * @param page 当前页码
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 获取每页显示的记录数
     * @return 每页显示的记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页显示的记录数
     * @param pageSize 每页显示的记录数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取总记录数
     * @return 总记录数
     */
    public long getTotalItems() {
        return totalItems;
    }

    /**
     * 设置总记录数
     * @param totalItems 总记录数
     */
    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
    }

    /**
     * 获取总页数
     * @return 总页数
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * 获取当前页的记录列表
     * @return 当前页的记录列表
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * 设置当前页的记录列表
     * @param items 当前页的记录列表
     */
    public void setItems(List<T> items) {
        this.items = items;
    }

    /**
     * 检查当前页是否是第一页
     * @return 如果是第一页返回true，否则返回false
     */
    public boolean isFirstPage() {
        return page == 1;
    }

    /**
     * 检查当前页是否是最后一页
     * @return 如果是最后一页返回true，否则返回false
     */
    public boolean isLastPage() {
        return page == totalPages;
    }

    /**
     * 获取上一页页码
     * @return 上一页页码
     */
    public int getPreviousPage() {
        return Math.max(1, page - 1);
    }

    /**
     * 获取下一页页码
     * @return 下一页页码
     */
    public int getNextPage() {
        return Math.min(totalPages, page + 1);
    }

    /**
     * 获取SQL的LIMIT子句
     * @return SQL的LIMIT子句
     */
    public String getLimitClause() {
        int offset = (page - 1) * pageSize;
        return " LIMIT " + offset + ", " + pageSize;
    }
}