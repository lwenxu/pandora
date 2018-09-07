package com.ibeetl.admin.core.rbac.da;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibeetl.admin.core.rbac.AccessType;
import com.ibeetl.admin.core.rbac.DataAccess;
import com.ibeetl.admin.core.rbac.DataAccessResullt;
import com.ibeetl.admin.core.service.CorePlatformService;
/**
 * 同机构
 * @author lijiazhi
 *
 */
@Component
public class SameOrgDataAccess implements DataAccess {
	
	@Autowired
	CorePlatformService platformService;

	@Override
	public DataAccessResullt getOrg(Long userId, Long orgId) {
		DataAccessResullt ret = new DataAccessResullt();
		ret.setStatus(AccessType.OnlyOrg);
		ret.setOrgIds(Arrays.asList(orgId));
		return ret;
		
	}

	@Override
	public String getName() {
		return "同结构";
	}

	@Override
	public Integer getType() {
		return 3;
	}

}
