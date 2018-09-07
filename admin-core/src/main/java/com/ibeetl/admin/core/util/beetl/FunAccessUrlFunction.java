package com.ibeetl.admin.core.util.beetl;

import com.ibeetl.admin.core.rbac.tree.FunctionItem;
import com.ibeetl.admin.core.service.CorePlatformService;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通过functionId获取AccessUrl,从缓存中获取
 */
@Component
public class FunAccessUrlFunction implements Function {

	@Autowired
    CorePlatformService platFormService;
	
	
	public Object call(Object[] paras, Context ctx) {
		FunctionItem tree = platFormService.buildFunction();
		FunctionItem item = tree.findChild((Long)paras[0]);
		return item.getData().getAccessUrl();
		
	}
	
	

}
