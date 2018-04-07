package com.ibeetl.admin.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.ext.simulate.WebSimulate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 模拟所有还未实现的视图,或者json，或者直接访问相应的html页面
 * @author xiandafu
 *
 */
@Controller
public class SimulateController {
	@Autowired
	WebSimulate webSimulate;
	Log log = LogFactory.getLog(SimulateController.class);
	@RequestMapping("/**/*.do")
	public void simluateWeb(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		log.info("没有配置 url "+request.getRequestURI()+",使用模拟MVC功能使用前后端分离");
		webSimulate.execute(request, response);
	}

	
	
}
