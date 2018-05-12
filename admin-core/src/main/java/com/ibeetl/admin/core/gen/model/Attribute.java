package com.ibeetl.admin.core.gen.model;

import java.util.ArrayList;
import java.util.List;

public class Attribute {
	private String name;
	private String colName;
	private String javaType;
	private String displayName;
	private boolean isId;
	private boolean showInQuery =false;
	//数据字典
	private String dictType;
	private String comment;
	//校验对象
	private List<Verify> verifyList = new ArrayList<>();
	
	
	public Attribute() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getDisplayName() {
		if(displayName==null) {
			return this.name;
		}
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public boolean isId() {
		return isId;
	}
	public void setId(boolean isId) {
		this.isId = isId;
	}
	public boolean isShowInQuery() {
		return showInQuery;
	}
	public void setShowInQuery(boolean showInQuery) {
		this.showInQuery = showInQuery;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
    public String getDictType() {
        return dictType;
    }
    public void setDictType(String dictType) {
        this.dictType = dictType;
    }
	public List<Verify> getVerifyList() {
		return verifyList;
	}
	public void setVerifyList(List<Verify> verifyList) {
		this.verifyList = verifyList;
	}

	@Override
	public String toString() {
		return "Attribute{" +
				"name='" + name + '\'' +
				", colName='" + colName + '\'' +
				", javaType='" + javaType + '\'' +
				", displayName='" + displayName + '\'' +
				", isId=" + isId +
				", showInQuery=" + showInQuery +
				", dictType='" + dictType + '\'' +
				", comment='" + comment + '\'' +
				", verifyList=" + verifyList +
				'}';
	}
}
