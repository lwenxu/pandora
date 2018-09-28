package com.ibeetl.admin.core.service;

import java.util.List;

import com.ibeetl.admin.core.conf.CustomConfig;
import com.ibeetl.admin.core.conf.PasswordConfig;
import com.ibeetl.admin.core.dao.CoreOrgDao;
import com.ibeetl.admin.core.dao.CoreUserDao;
import com.ibeetl.admin.core.rbac.UserLoginInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibeetl.admin.core.entity.CoreOrg;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.util.PlatformException;
import com.ibeetl.admin.core.util.enums.DelFlagEnum;
import com.ibeetl.admin.core.util.enums.GeneralStateEnum;

@Service
@Transactional
public class CoreUserService {
	@Autowired
    CoreUserDao userDao ;
	
	@Autowired
    CoreOrgDao orgDao;
	
	@Autowired
    PasswordConfig.PasswordEncryptService passwordEncryptService;
	
	@Autowired SQLManager sqlManager;

	@Autowired
	CustomConfig config;

	public UserLoginInfo login(String userName, String password){
		CoreUser query = new CoreUser();
		query.setUsername(userName);
		query.setPassword(passwordEncryptService.password(password));
		query.setStatus(GeneralStateEnum.ENABLE.getValue());
		CoreUser user =userDao.createLambdaQuery().andEq(CoreUser::getUsername,userName).
		    andEq(CoreUser::getPassword, passwordEncryptService.password(password)).single();
		if(user==null) {
		    throw new PlatformException("用户"+userName+"不存在或者密码错误");
		}
		if(!user.getStatus().equals(GeneralStateEnum.ENABLE.getValue())){
		    throw new PlatformException("用户"+userName+"已经失效");
		}
		if(user.getDelFlag()==DelFlagEnum.DELETED.getValue()) {
		    throw new PlatformException("用户"+userName+"已经删除");
		}
		
		
		List<CoreOrg>  orgs = getUserOrg(user.getId(),user.getOrgId());
		UserLoginInfo loginInfo = new UserLoginInfo();
		loginInfo.setOrgs(orgs);
		loginInfo.setUser(user);
		return loginInfo;
		
	}
	
	
	
	public  List<CoreOrg> getUserOrg(long userId,long orgId){
		List<CoreOrg> orgs =  orgDao.queryOrgByUser(userId);
		if(orgs.isEmpty()){
			//没有赋值任何角色，默认给一个所在部门
			CoreOrg userOrg = orgDao.unique(orgId);
			orgs.add(userOrg);
		}
		return orgs;
	}
	
	
	
	public List<CoreUser> getAllUsersByRole(String role){
		return userDao.getUserByRole(role);
	}
	
	public CoreUser getUserByCode(String userName){
		CoreUser user = new CoreUser();
		user.setUsername(userName);
		return userDao.templateOne(user);
	}
	
	public void update(CoreUser user){
		userDao.updateById(user);
	}
	
	public CoreOrg getOrgById(Long orgId){
		return orgDao.unique(orgId);
	}
	
	public CoreUser getUserById(Long userId){
		return userDao.unique(userId);
	}
	
	public List<String> getOrgCode(List<Long> orgIds){
		return orgDao.queryAllOrgCode(orgIds);
	}
}
