package com.ibeetl.admin.core.conf;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibeetl.admin.core.conf.PasswordConfig.PasswordEncryptService;

/**
 * 描述: 密码工具，系统默认采用明文
 *
 * @author : xiandafu
 */
@Configuration
@ConditionalOnMissingBean(PasswordEncryptService.class)
public class PasswordConfig {

	
	public static interface PasswordEncryptService{
		public String password(String pwd);
	}
	
	
	public static class DefaultEncryptBean implements PasswordEncryptService {
		@Autowired
		private CustomConfig customConfig;
		@Override
		public String password(String pwd) {
			// 采用明文，系统应该提供自己的EncryptBean实现以代替默认
			return DigestUtils.md5Hex(pwd + customConfig.getSalt());
		}
		
	}
	
	@Bean
	public PasswordEncryptService passwordEncryptBean(){
		return new DefaultEncryptBean();
	}

}
