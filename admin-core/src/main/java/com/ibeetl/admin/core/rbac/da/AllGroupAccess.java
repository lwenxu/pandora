package com.ibeetl.admin.core.rbac.da;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibeetl.admin.core.rbac.AccessType;
import com.ibeetl.admin.core.rbac.DataAccess;
import com.ibeetl.admin.core.rbac.DataAccessResullt;
import com.ibeetl.admin.core.service.CorePlatformService;
/**
 * 所有机构
 * @author lijiazhi
 *
 */
@Component
public class AllGroupAccess implements DataAccess {
	
	@Autowired
	CorePlatformService platformService;

	@Override
	public DataAccessResullt getOrg(Long userId, Long orgId) {
	
		
		DataAccessResullt ret = new DataAccessResullt();
		ret.setStatus(AccessType.AllOrg);
		return ret;
		
	}

	@Override
	public String getName() {
		return "所有机构";
	}

	@Override
	public Integer getType() {
		return 5;
	}

}
