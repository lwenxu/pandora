package ${package};

import com.lamp.core.util.ValidateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibeetl.admin.console.web.dto.DictExcelImportData;
import com.ibeetl.admin.console.web.query.UserQuery;
import com.lamp.core.annotation.Function;
{basePackage}.entity.*;
import ${basePackage}.service.*;{basePackage}.web.query.*;

/**
 * ${entity.displayName} 接口
 */
\@Controller
public class ${entity.name}Controller{

    private final Log log = LogFactory.getLog(this.getClass());
    private static final String MODEL = "/${target.urlBase}/${entity.code}";

    @var service=entity.code+"Service";

    \@Autowired private ${entity.name}Service ${service};
    
    \@Autowired
    FileService fileService;
    /* 页面 */

    \@GetMapping(MODEL + "/index.do")
    \@Function("${entity.code}.query")
    \@ResponseBody
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("/${target.urlBase}/${entity.code}/index.html") ;
        view.addObject("search", ${entity.name}Query.class.getName());
        return view;
    }

    \@GetMapping(MODEL + "/edit.do")
    \@Function("${entity.code}.edit")
    \@ResponseBody
    public ModelAndView edit(${entity.idAttribute.javaType} ${entity.idAttribute.name}) {
        ModelAndView view = new ModelAndView("/${target.urlBase}/${entity.code}/edit.html");
        ${entity.name} ${entity.code} = ${service}.queryById(${entity.idAttribute.name});
        view.addObject("${entity.code}", ${entity.code});
        return view;
    }

    \@GetMapping(MODEL + "/add.do")
    \@Function("${entity.code}.add")
    \@ResponseBody
    public ModelAndView add() {
        ModelAndView view = new ModelAndView("/${target.urlBase}/${entity.code}/add.html");
        return view;
    }

    /* ajax json */

    \@PostMapping(MODEL + "/list.json")
    \@Function("${entity.code}.query")
    \@ResponseBody
    public JsonResult<PageQuery> list(${entity.name}Query condtion)
    {
        PageQuery page = condtion.getPageQuery();
        ${service}.queryByCondition(page);
        return JsonResult.success(page);
    }

    \@PostMapping(MODEL + "/add.json")
    \@Function("${entity.code}.add")
    \@ResponseBody
    public JsonResult add(\@Validated(ValidateConfig.ADD.class)${entity.name} ${entity.code})
    {
        ${service}.save(${entity.code});
        return new JsonResult().success();
    }

    \@PostMapping(MODEL + "/update.json")
    \@Function("${entity.code}.update")
    \@ResponseBody
    public JsonResult<String> update(\@Validated(ValidateConfig.UPDATE.class)  ${entity.name} ${entity.code}) {
        boolean success = ${service}.update(${entity.code});
        if (success) {
            return new JsonResult().success();
        } else {
            return JsonResult.failMessage("保存失败");
        }
    }


   
    \@GetMapping(MODEL + "/view.json")
    \@Function("${entity.code}.query")
    \@ResponseBody
    public JsonResult<${entity.name}>queryInfo(${entity.idAttribute.javaType} ${entity.idAttribute.name}) {
        ${entity.name} ${entity.code} = ${service}.queryById( ${entity.idAttribute.name});
        return  JsonResult.success(${entity.code});
    }

    \@PostMapping(MODEL + "/delete.json")
    \@Function("${entity.code}.delete")
    \@ResponseBody
    public JsonResult delete(String ids) {
        if (ids.endsWith(",")) {
            ids = StringUtils.substringBeforeLast(ids, ",");
        }
        List<Long> idList = ConvertUtil.str2longs(ids);
        ${service}.batchDel${entity.name}(idList);
        return new JsonResult().success();
    }
    
    @if(entity.includeExcel){
    
    \@PostMapping(MODEL + "/excel/export.json")
    \@Function("${entity.code}.export")
    \@ResponseBody
    public JsonResult<String> export(HttpServletResponse response,${entity.name}Query condtion) {
        /**
         * 1)需要用你自己编写一个的excel模板
         * 2)通常excel导出需要关联更多数据，因此${service}.queryByCondition方法经常不符合需求，需要重写一个为模板导出的查询
         * 3)参考ConsoleDictController来实现模板导入导出
         */
        String excelTemplate ="excelTemplates/${target.urlBase}/${entity.code}/你的excel模板文件名字.xls";
        PageQuery<${entity.name}> page = condtion.getPageQuery();
        //取出全部符合条件的
        page.setPageSize(Integer.MAX_VALUE);
        page.setPageNumber(1);
        page.setTotalRow(Integer.MAX_VALUE);
        //本次导出需要的数据
        List<${entity.name}> list =${service}.queryByCondition(page).getList();
        try(InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(excelTemplate)) {
            if(is==null) {
                throw new PlatformException("模板资源不存在："+excelTemplate);
            }
            FileItem item = fileService.createFileTemp("${entity.displayName}_"+DateUtil.now("yyyyMMddHHmmss")+".xls");
            OutputStream os = item.openOutpuStream();
            Context context = new Context();
            context.putVar("list", list);
            JxlsHelper.getInstance().processTemplate(is, os, context);
            os.close();
            //下载参考FileSystemContorller
            return  JsonResult.success(item.getPath());
        } catch (IOException e) {
            throw new PlatformException(e.getMessage());
        }
        
    }
    
    \@PostMapping(MODEL + "/excel/import.do")
    \@Function("${entity.code}.import")
    \@ResponseBody
    public JsonResult importExcel(\@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
           return JsonResult.fail();
        }
        InputStream ins = file.getInputStream();
        /*解析模板并导入到数据库里,参考DictConsoleContorller，使用jxls reader读取excel数据*/
        ins.close();
        return JsonResult.success();
    }
    
    
    @}

}
