package com.duo.core.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;


public class MyPage<T> implements Serializable {
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	// -- 分页参数 --//
	public int pageNo;
	public int pageNumber;//分页数据起始位置
	public int pageSize;
	public String orderBy = null;
	public String order = null;
	public boolean autoCount = true;
	
	public String sort;//排序

	// -- 返回结果 --//
	public List<T> content = Lists.newArrayList();
	public long totalCount = -1;

	public List list = new ArrayList(0); // 存放数据
	private int barNumbers = 10; // 几个为一条，默认为10
	private List listNumbers = new ArrayList(); // 数字翻页条
	
	private int total; // 总记录数
	private List<T> rows; // 结果集//使用easyui时该list的名称设为rows,否则前台会读取不到数据

	// -- 构造函数 --//
	public MyPage() {
	}

	public MyPage(int pageSize) {
		this.pageSize = pageSize;
	}


	public String getSort() {
		if("".equals(orderBy) || null == orderBy){
			orderBy="id";
		}if("".equals(order) || null == order){
			order="desc";
		}
		return orderBy +"  "+ order;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	// -- 访问查询参数函数 --//
	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为0.
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo; 
		this.setPageNumber((pageNo - 1) * this.pageSize);
	}

	public MyPage<T> pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	public MyPage<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	/**
	 * 获得排序字段,无默认值.多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	public MyPage<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}

	/**
	 * 获得排序方向.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order
	 *            可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		// 检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this.order = StringUtils.lowerCase(order);
	}

	public MyPage<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	public MyPage<T> autoCount(final boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}

	// -- 访问查询结果函数 --//

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getContent() {
		return content;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setContent(final List<T> content) {
		this.content = content;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public int getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}
		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return Integer.valueOf(count+"");
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return 1;
		}
	}

	/**
	 * 计算开始页和结束页
	 *
	 * @return PageIndex 记录开始页（startindex）和结束页（endindex）
	 */
	public List getListNumbers() {
		List listn = new ArrayList();
		int totalPages = Integer.valueOf(String.valueOf(this.getTotalPages()));
		int startpage = pageNo - (barNumbers % 2 == 0 ? barNumbers / 2 - 1 : barNumbers / 2);
		int endpage = pageNo + barNumbers / 2;
		if (startpage < 1) {
			startpage = 1;
			if (totalPages >= barNumbers)
				endpage = barNumbers;
			else
				endpage = totalPages;
		}
		if (endpage > totalPages) {
			endpage = totalPages;
			if ((endpage - barNumbers) > 0)
				startpage = endpage - barNumbers + 1;
			else
				startpage = 1;
		}
		for (int i = startpage; i <= endpage; i++) {
			listn.add(i);
		}
		return listn;
	}

	public void setListNumbers(List listNumbers) {
		this.listNumbers = listNumbers;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getBarNumbers() {
		return barNumbers;
	}

	public void setBarNumbers(int barNumbers) {
		this.barNumbers = barNumbers;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
