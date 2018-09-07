package ${package};

import javax.validation.constraints.NotNull;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;

import com.lamp.core.util.ValidateConfig;

import com.lamp.core.annotation.Dict;

import org.beetl.sql.core.annotatoin.InsertIgnore;
import org.beetl.sql.core.annotatoin.Version;
import org.beetl.sql.core.annotatoin.LogicDelete;


/* 
* ${comment}
* gen by Spring Boot2 Admin ${date(),"yyyy-MM-dd"}
*/
public class ${className} extends BaseEntity{

    @for(attr in attrs){
		@if(!isEmpty(attr.comment)){
    //${attr.comment}
		@}
		@if(attr.isId) {
    \@NotNull(message = "ID不能为空", groups =ValidateConfig.UPDATE.class)
    \@SeqID(name = ORACLE_CORE_SEQ_NAME)
    \@AutoID	
		@}
		@if(isNotEmpty(attr.dictType)) {
    \@Dict(type="${attr.dictType}")
		@}
		@if(attr.name == "delFlag") {
	/*逻辑删除标志*/
	\@InsertIgnore
	\@LogicDelete(value = 1)
		@}
		@if(attr.name == "version") {
	/*乐观锁版本标志*/
	\@InsertIgnore
	\@Version
		@}

    private ${attr.type} ${attr.name} ;
	
	@}
    public ${className}()
    {
    }

    @for(attr in attrs){
		@if(!isEmpty(attr.comment)){
    /**${attr.comment}
    *\@return 
    */
		@}
    public ${attr.type} get${attr.methodName}(){
	    return  ${attr.name};
    }
		@if(!isEmpty(attr.comment)){
    /**${attr.comment}
    *\@param  ${attr.name}
    */
		@}
    public void set${attr.methodName}(${attr.type} ${attr.name}){
        this.${attr.name} = ${attr.name};
    }

    @}

}
