package com.ibeetl.admin.console.web.query;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.util.enums.CoreDictType;
import com.ibeetl.admin.core.web.query.PageParam;

public class OrgUserQuery extends PageParam {

	@Query(name="账号",display=true,fuzzy=true)
	private String code ;
	@Query(name="名称",display=true,fuzzy=true)
	private String name ;
	
	@Query(name="状态",display=true,type=Query.TYPE_DICT,dict=CoreDictType.USER_STATE)
	private String state;
	
	@Query(name="职务",display=true,type=Query.TYPE_DICT,dict="job_type",group="jobType")
    private String jobType0;
    
    @Query(name="职务明细",display=true,type=Query.TYPE_DICT,dict="",group="jobType")
    private String jobType1;
	
	private Long orgId;
	
	
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
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	



}
