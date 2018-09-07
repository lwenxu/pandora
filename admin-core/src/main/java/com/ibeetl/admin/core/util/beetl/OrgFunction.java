package com.ibeetl.admin.core.util.beetl;

import com.ibeetl.admin.core.rbac.tree.OrgItem;
import com.ibeetl.admin.core.service.CorePlatformService;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通过orgId获取orgName,从缓存中获取
 * @author xiandafu
 *
 */
@Component
public class OrgFunction implements Function {

	@Autowired
    CorePlatformService platFormService;
	
	
	public Object call(Object[] paras, Context ctx) {
		OrgItem tree = platFormService.buildOrg();
		OrgItem item = tree.findChild((Long)paras[0]);
		return item.getName();
		
	}
	
	

}
