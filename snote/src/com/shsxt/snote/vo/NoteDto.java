package com.shsxt.snote.vo;
/**
 * 为主页面提供数据
 * @author Administrator
 *
 */
public class NoteDto {
	private Long count;
	private String typeName;
	private String pubDate;
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
}
