package com.ibeetl.admin.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibeetl.admin.core.annotation.Dict;
import com.ibeetl.admin.core.util.ValidateConfig;
import com.ibeetl.admin.core.util.enums.CoreDictType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/*
*   用户实体
*
*/

@ToString
public class CoreUser extends BaseEntity  implements Serializable {
	@NotNull(message = "ID不能为空", groups = ValidateConfig.UPDATE.class)
	@SeqID(name = ORACLE_CORE_SEQ_NAME)
	@AutoID
	protected Long id;
	// 删除标识
	@JsonIgnore
	protected Integer delFlag= 0;
	// 创建时间

	protected Date createTime;
	// 登录名，编号
	@Getter@Setter
	@NotBlank(message = "用户编号不能为空", groups = ValidateConfig.ADD.class)
	@NotNull(message = "用户编号不能为空", groups = ValidateConfig.UPDATE.class)
	private String username;

	// 用户姓名
	@Getter@Setter
	@NotBlank(message = "用户名不能为空")
	private String nickname;

	// 组织机构id

	private Long orgId;

	// 密码
	@JsonIgnore
	private String password;
	
	@Dict(type=CoreDictType.USER_STATE)
	private String status;
	
	//扩展例子
	@Dict(type="job_type")
	private String jobType0;
	
	@Dict(type="job_type")
	private String jobType1;
	
	
	private Date updateTime;
	
	/*用户的个人资料附件，保存到Core_File 表里*/
	private String attachmentId;

	private long group;
	private String phone;
	private String email;

	public long getGroup() {
		return group;
	}

	public void setGroup(long group) {
		this.group = group;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }



}
