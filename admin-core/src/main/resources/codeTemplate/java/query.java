package ${package};

import com.lamp.core.annotation.Query;

/**
 *${entity.displayName}查询
 */
public class ${entity.name}Query extends PageParam {
    @for(attr in attrs) {
        @if(isNotEmpty(attr.dictType)) {
    \@Query(name = "${attr.displayName}", display = true,type=Query.TYPE_DICT,dict="${attr.dictType}")
        @}else {
    \@Query(name = "${attr.displayName}", display = true)        
        @}
    private ${attr.javaType} ${attr.name};
    @}
    @for(attr in attrs) {
    public ${attr.javaType} get${upperFirst(attr.name)}(){
        return  ${attr.name};
    }
    public void set${upperFirst(attr.name)}(${attr.javaType} ${attr.name} ){
        this.${attr.name} = ${attr.name};
    }
@}
 
}
