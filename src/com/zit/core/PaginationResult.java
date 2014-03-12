package com.zit.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页结果对象
 * 
 * @author 易君
 * @version V1.0.0 2012-10-10
 * @since JDK5.0
 */
public class PaginationResult<DATA> {
	// 此页开始序号
	private int beginRow = -1;
	// 每页最大显示记录数
	private int pageSize = 20;
	// 找到的记录总条数
	private long maxRow = -1L;
	// 总页数
	private int totalPageNo = -1;
	// 当前页数
	private int currentPageNo = -1;
	// 分页数据
	private DATA data;
	
	public int getMaxPageNo() {
		if (totalPageNo == -1) {
			totalPageNo = (int) (maxRow / pageSize);
			if (maxRow % pageSize != 0) {
				totalPageNo++;
			}
		}
		return this.totalPageNo;
	}

	public int getBeginRow() {
		if (beginRow == -1) {
			beginRow = pageSize * (currentPageNo - 1);
		}
		return this.beginRow;
	}


	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getMaxRow() {
		return this.maxRow;
	}

	public void setMaxRow(long maxxRow) {
		this.maxRow = maxxRow;
	}

	public int getCurrentPageNo() {
		return this.currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public DATA getData() {
		return this.data;
	}

	public void setData(DATA data) {
		this.data = data;

	}
	
	public boolean getHaveData() {
		return this.getMaxRow()>0;
	}

}
