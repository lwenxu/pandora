batchDelByIds
===
	update core_org set u.del_flag = 1 where u.id in( #join(ids)#);

queryByCondtion
===
	select 
	@pageTag(){
	o.*
	@}
	from core_org o where 1=1 and del_flag = 0
	@ //数据权限，组织机构对应的对应的字段
	and #function("org.query",{org:"id"})# 
    @if(!isEmpty(username)){
        and  o.username like #"%"+username+"%"#
    @}
    @if(!isEmpty(username)){
        and  o.username like #"%"+username+"%"#
    @}
    @if(!isEmpty(type)){
        and  o.type = #type#
    @}
    @if(!isEmpty(parentOrgId)){
        and  o.parent_org_id = #parentOrgId#
    @}

    @pageIgnoreTag(){
    	   order by o.id
    @}
   
  






	
	
	

