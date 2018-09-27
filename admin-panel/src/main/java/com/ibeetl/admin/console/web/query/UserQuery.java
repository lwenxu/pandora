package com.ibeetl.admin.console.web.query;

import java.util.Date;
import java.util.List;

import com.ibeetl.admin.console.constant.SortType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.util.Tool;
import com.ibeetl.admin.core.util.enums.CoreDictType;
import com.ibeetl.admin.core.web.query.PageParam;

@ToString
public class UserQuery extends PageParam {

	@Query(name="账号",display=true,fuzzy=true)
	private String code ;
	@Query(name="名称",display=true,fuzzy=true)
	private String name ;
	private String nickName;
	@Query(name = "部门", display = true, type = Query.TYPE_CONTROL, control = "org")
	@Getter @Setter
	private Long group;
	
	@Query(name="状态",display=true,type=Query.TYPE_DICT,dict=CoreDictType.USER_STATE)
	private String state;

	@Query(name="排序")
	private String sorter;

	@Getter@Setter
	private String sortField;
	@Getter@Setter
	private String sortType;

	@Query(name="职务",display=true,type=Query.TYPE_DICT,dict="job_type",group="job_type")
	private String jobType0;
	
	@Query(name="职务明细",display=true,type=Query.TYPE_DICT,dict="",group="job_type")
    private String jobType1;
	
	
	@Query(name="创建日期",display=true,type=Query.TYPE_DATE_BETWEEN)
	private String createDateRange;
	private Date createDateMin;
	private Date createDateMax;


	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setSorter(String sorter) {
		this.sorter = sorter;
		if (StringUtils.isNotEmpty(sorter)) {
			String[] arr = sorter.split("_");
			this.sortField = arr[0];
			this.sortType = arr[1].equals("descend") ? SortType.DESC : SortType.ASC;
		}
	}

	public String getSorter() {
		return sorter;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	public String getJobType0() {
        return jobType0;
    }
    public void setJobType0(String jobType0) {
        this.jobType0 = jobType0;
    }
    public String getJobType1() {
        return jobType1;
    }
    public void setJobType1(String jobType1) {
        this.jobType1 = jobType1;
    }
    public String getCreateDateRange() {
		return createDateRange;
	}
	public void setCreateDateRange(String createDateRange) {
		this.createDateRange = createDateRange;
		if(StringUtils.isEmpty(createDateRange)) {
			return ;
		}
		Date[] ds = Tool.parseDataRange(createDateRange);
		this.createDateMin=ds[0];
		this.createDateMax =ds[1];
	}
	public Date getCreateDateMin() {
		return createDateMin;
	}
	public void setCreateDateMin(Date createDateMin) {
		this.createDateMin = createDateMin;
	}
	public Date getCreateDateMax() {
		return createDateMax;
	}
	public void setCreateDateMax(Date createDateMax) {
		this.createDateMax = createDateMax;
	}
//    public String getJobSubType() {
//        return jobSubType;
//    }
//    public void setJobSubType(String jobSubType) {
//        this.jobSubType = jobSubType;
//    }
//   



}
