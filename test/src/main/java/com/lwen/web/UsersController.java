package com.lwen.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.sql.core.engine.PageQuery;
import org.jxls.common.Context;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.ReaderConfig;
import org.jxls.reader.XLSReadMessage;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ibeetl.admin.console.web.dto.DictExcelImportData;
import com.ibeetl.admin.console.web.query.UserQuery;
import com.ibeetl.admin.core.annotation.Function;
import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.entity.CoreDict;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.file.FileItem;
import com.ibeetl.admin.core.file.FileService;
import com.ibeetl.admin.core.web.JsonResult;
import com.ibeetl.admin.core.util.*;
import com.lwen.entity.*;
import com.lwen.service.*;
import com.lwen.web.query.*;

/**
 * Users 接口
 */
@Controller
public class UsersController{

    private final Log log = LogFactory.getLog(this.getClass());
    private static final String MODEL = "/users/users";


    @Autowired private UsersService usersService;
    
    @Autowired
    FileService fileService;
    /* 页面 */

    @GetMapping(MODEL + "/index.do")
    @Function("users.query")
    @ResponseBody
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("/users/users/index.html") ;
        view.addObject("search", UsersQuery.class.getName());
        return view;
    }

    @GetMapping(MODEL + "/edit.do")
    @Function("users.edit")
    @ResponseBody
    public ModelAndView edit(String gender) {
        ModelAndView view = new ModelAndView("/users/users/edit.html");
        Users users = usersService.queryById(gender);
        view.addObject("users", users);
        return view;
    }

    @GetMapping(MODEL + "/add.do")
    @Function("users.add")
    @ResponseBody
    public ModelAndView add() {
        ModelAndView view = new ModelAndView("/users/users/add.html");
        return view;
    }

    /* ajax json */

    @PostMapping(MODEL + "/list.json")
    @Function("users.query")
    @ResponseBody
    public JsonResult<PageQuery> list(UsersQuery condtion)
    {
        PageQuery page = condtion.getPageQuery();
        usersService.queryByCondition(page);
        return JsonResult.success(page);
    }

    @PostMapping(MODEL + "/add.json")
    @Function("users.add")
    @ResponseBody
    public JsonResult add(@Validated(ValidateConfig.ADD.class)Users users)
    {
        usersService.save(users);
        return new JsonResult().success();
    }

    @PostMapping(MODEL + "/update.json")
    @Function("users.update")
    @ResponseBody
    public JsonResult<String> update(@Validated(ValidateConfig.UPDATE.class)  Users users) {
        boolean success = usersService.update(users);
        if (success) {
            return new JsonResult().success();
        } else {
            return JsonResult.failMessage("保存失败");
        }
    }


   
    @GetMapping(MODEL + "/view.json")
    @Function("users.query")
    @ResponseBody
    public JsonResult<Users>queryInfo(String gender) {
        Users users = usersService.queryById( gender);
        return  JsonResult.success(users);
    }

    @PostMapping(MODEL + "/delete.json")
    @Function("users.delete")
    @ResponseBody
    public JsonResult delete(String ids) {
        if (ids.endsWith(",")) {
            ids = StringUtils.substringBeforeLast(ids, ",");
        }
        List<Long> idList = ConvertUtil.str2longs(ids);
        usersService.batchDelUsers(idList);
        return new JsonResult().success();
    }
    

}
