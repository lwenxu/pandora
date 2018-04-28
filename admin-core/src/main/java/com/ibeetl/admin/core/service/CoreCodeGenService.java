package com.ibeetl.admin.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.beetl.sql.core.JavaType;
import org.beetl.sql.core.NameConversion;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.db.ClassDesc;
import org.beetl.sql.core.db.ColDesc;
import org.beetl.sql.core.db.MetadataManager;
import org.beetl.sql.core.db.TableDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibeetl.admin.core.entity.CoreFunction;
import com.ibeetl.admin.core.gen.model.Attribute;
import com.ibeetl.admin.core.gen.model.Entity;

/**
 * 代码生成，用于根据表或者视图生成entity，mapper，service，conroller
 * 未来可以生成swagger api，界面
 * @author xiandafu
 */
@Service
public class CoreCodeGenService {
	@Autowired
	SQLManager sqlManager;
	@Autowired
	CorePlatformService platformService;
	
	public List<Entity> getAllEntityInfo(){
		MetadataManager meta = sqlManager.getMetaDataManager();
		Set<String> set = meta.allTable();
		List<Entity> list = new ArrayList<Entity>();
		for(String table:set) {
			list.add(getEntitySimpleInfo(table));
		}
		return list;
	}
	
	public Entity getEntitySimpleInfo(String table) {
		MetadataManager meta = sqlManager.getMetaDataManager();
		NameConversion nc = sqlManager.getNc();
		TableDesc tableDesc = meta.getTable(table);
		if(tableDesc==null) {
			return null;
		}
		ClassDesc classDesc = tableDesc .getClassDesc(nc);
		Entity e = new Entity();
		e.setName(nc.getClassName(table));
		e.setComment(tableDesc.getRemark());
		e.setTableName(table);
		return e;
	}
	
	public Entity getEntityInfo(String table) {
		MetadataManager meta = sqlManager.getMetaDataManager();
		NameConversion nc = sqlManager.getNc();
		TableDesc tableDesc = meta.getTable(table);
		if(tableDesc==null) {
			return null;
		}
		ClassDesc classDesc = tableDesc .getClassDesc(nc);
		Entity e = new Entity();
		e.setName(nc.getClassName(table));
		e.setComment(tableDesc.getRemark());
		e.setTableName(table);
		e.setCode(getEntityCode(e.getName()));
		
		Set<String> cols = tableDesc.getCols();
		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		int i=1;
		for(String col:cols) {
			ColDesc desc = tableDesc.getColDesc(col);
			Attribute attr = new Attribute();
			attr.setColName(col);
			attr.setName(nc.getPropertyName(col));
			if(tableDesc.getIdNames().contains(col)) {
				//TODO,代码生成实际上用了一个Id，因此具备联合主键的，不应该生成代码
				attr.setId(true);
				e.setIdAttribute(attr);
			}
			attr.setComment(desc.remark);
			String type = JavaType.getType(desc.sqlType, desc.size, desc.digit);
			if(type.equals("Double")){
				type = "BigDecimal";
			}		
			if(type.equals("Timestamp")){
				type ="Date";
			}
			attr.setJavaType(type);
			setGetDisplayName(attr);
			attrs.add(attr);
			
			
			
		}
		e.setList(attrs);
		
		return e;
	}
	
	public boolean insertFunction(Entity data,String urlBase){
		String  preffix =  urlBase.replace('/', '.');
		String functionCode = preffix+"."+data.getCode();
		String indexFunctonCode = functionCode+".query";
		CoreFunction query = new CoreFunction();
		query.setCode(indexFunctonCode);
		Object o = sqlManager.templateOne(query);
		if(o != null){
			return true;
		}
		
		//设置功能点
		CoreFunction rootFunction = new CoreFunction();
		rootFunction.setName(data.getDisplayName());
		rootFunction.setCode(functionCode);
		rootFunction.setCreateTime(new Date());
		rootFunction.setParentId(0L);
		rootFunction.setType("FN0");
		sqlManager.insert(rootFunction,true);
		Long parentId =rootFunction.getId();
		
		//设置曾删改查功能点
		CoreFunction indexFunction = new CoreFunction();
		indexFunction.setName("查询"+data.getDisplayName());
		indexFunction.setCode(indexFunctonCode);
		indexFunction.setCreateTime(new Date());
		indexFunction.setParentId(parentId);
		indexFunction.setAccessUrl("/"+urlBase+"/"+data.getCode()+"/index.do");
		//设置为查询功能
		indexFunction.setType("FN1");
		sqlManager.insert(indexFunction,true);
		
		
		CoreFunction  upateFunction = new CoreFunction();
		String updateFunctonCode = functionCode+".edit";
		upateFunction.setName("修改"+data.getDisplayName());
		upateFunction.setCode(updateFunctonCode);
		upateFunction.setCreateTime(new Date());
		upateFunction.setParentId(parentId);
		upateFunction.setType("FN0");
		sqlManager.insert(upateFunction,true);
		
		CoreFunction  addFunction = new CoreFunction();
		String addFunctionCode = functionCode+".add";
		addFunction.setName("添加"+data.getDisplayName());
		addFunction.setCode(addFunctionCode);
		addFunction.setCreateTime(new Date());
		addFunction.setParentId(parentId);
		addFunction.setType("FN0");
		sqlManager.insert(addFunction,true);
		
		
		CoreFunction  delFunction = new CoreFunction();
		String delFunctionCode = functionCode+".add";
		delFunction.setName("删除"+data.getDisplayName());
		delFunction.setCode(delFunctionCode);
		delFunction.setCreateTime(new Date());
		delFunction.setParentId(parentId);
		delFunction.setType("FN0");
		sqlManager.insert(addFunction,true);
		
		//刷新缓存
		platformService.clearFunctionCache();
		
		return true;
	}

	
	//根据类名提供一个变量名
	private String getEntityCode(String s) {
		//找到最后一个大写字母，以此为变量名
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
		
	}
	/*根据数据库注释来判断显示名称*/
	private void setGetDisplayName(Attribute attr) {
		String comment = attr.getComment();
		if(comment==null) {
			attr.setDisplayName(attr.getName());
			return ;
		}
		String displayName = null;
		int index = comment.indexOf(",");
		if(index!=-1) {
			displayName =  comment.substring(0,index);
			attr.setDisplayName(displayName);
		}else {
			attr.setDisplayName(comment);
		}
	}
 
}
