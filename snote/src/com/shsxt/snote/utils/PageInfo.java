package com.shsxt.snote.utils;

import java.util.List;

import com.shsxt.snote.constant.NoteConstant;
import com.shsxt.snote.dao.BaseDao;





/**
 * 分页封装类
 * @author Administrator
 *
 */
public class PageInfo<T> extends BaseDao<T>{
	private int pageNum;//页码
	
	private int pageSize=NoteConstant.PAGE_SIZE;//每页记录数
	
	private long totalElements;//总记录数
	
	private int totalPages;// 总页数
	
	private int pageIndex;//起始索引
	
	private List<T>  currentDatas;//当前页记录
	
	private int firstPage;//首页
	private boolean hasFirstPage;//是否显示首页

	private int prePage;//上一页
	private boolean hasPrePage;//是否显示上一页

	private int startNavPage;//开始导航页
	
	private int endNavPage;//导航结束页
	
	private int nextPage;//下一页
	private boolean hasNextPage;// 是否显示下一页
	
	private int endPage;//尾页
	private boolean hasEndPage;//是否显示尾页

	public PageInfo(String sqlCount,String sqlQuery,Object[] params,int pageNum,Class<T> clazz) {	
		this.pageNum=pageNum;//设置当前页码值	
		this.pageIndex=(this.pageNum-1)*this.pageSize;//起始索引
		// 设置总记录数
		setTotalElements(sqlCount,params);
		// 获取当前页记录
		setCurrentDatas(sqlQuery,params,clazz);
		setTotalPages();
		//设置首页
		setFirstPage();
		setHasFirstPage();
		//设置上一页
		setPrePage();
		setHasPrePage();
		//设置导航开始页
		setStartNavPage();
		// 设置导航结束页
		setEndNavPage();
		// 设置下一页
		setNextPage();
		setHasNextPage();
		//设置末页
		setEndPage();
		setHasEndPage();

	}

	public boolean isHasFirstPage() {
		return hasFirstPage;
	}


	public void setHasFirstPage() {
		this.hasFirstPage = this.pageNum>=2;
	}

	public boolean isHasPrePage() {
		return hasPrePage;
	}

	public void setHasPrePage() {
		this.hasPrePage = this.pageNum>=2;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage() {
		this.hasNextPage = this.pageNum<this.totalPages;
	}
	public boolean isHasEndPage() {
		return hasEndPage;
	}

	public void setHasEndPage() {
		this.hasEndPage = this.pageNum<this.totalPages;
	}


	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalElements() {
		return totalElements;
	}

	// 设置总记录数
	public void setTotalElements(String sqlCount,Object[] params) {
		this.totalElements= (long) querySingleValue(sqlCount, params);
	}

	public int getTotalPages() {
		return totalPages;
	}

	//设置总页数
	public void setTotalPages() {
		this.totalPages = (int) ((this.totalElements+this.pageSize-1)/this.pageSize);
	}
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	@SuppressWarnings("rawtypes")
	public List getCurrentDatas() {
		return currentDatas;
	}
	//  limit ?,?  设置当前页数据
	public void setCurrentDatas(String sqlQuery,Object[] params,Class<T> clazz) {
		sqlQuery=sqlQuery+" limit ?,? ";
		Object[] newObject=null;
		if(null==params||params.length==0){
			newObject=new Object[2];
			newObject[0]=this.pageIndex;
			newObject[1]=this.pageSize;	
		}else{
			newObject=new Object[params.length+2];	
			//数组拷贝
			System.arraycopy(params, 0, newObject, 0, params.length);
			newObject[newObject.length-2]=this.pageIndex;// 起始索引
			newObject[newObject.length-1]=this.pageSize;// 每页记录
		}
				
		this.currentDatas= queryRows(sqlQuery, clazz, newObject);
	}
	public int getFirstPage() {
		return firstPage;
	}
	public void setFirstPage() {
		this.firstPage = 1;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage() {
		this.prePage = this.pageNum-1<=0?1:this.pageNum-1;
	}

	public int getStartNavPage() {
		return startNavPage;
	}

	public void setStartNavPage() {
		this.startNavPage = this.pageNum-2<=0?1:this.pageNum-2;
	}

	public int getEndNavPage() {
		return endNavPage;
	}

	public void setEndNavPage() {
		this.endNavPage = this.pageNum+2>this.totalPages?totalPages:this.pageNum+2;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage() {
		this.nextPage = this.pageNum+1>=this.totalPages?totalPages:this.pageNum+1;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage() {
		this.endPage = this.totalPages;
	}

}
