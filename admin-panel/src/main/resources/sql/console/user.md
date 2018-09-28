queryByCondition
===
    select 
    @pageTag(){
    u.*
    @}
    from core_user u left join core_group o on u.group=o.id where 1=1
    @//数据权限，该sql语句功能点  
    
    @if(!isEmpty(group)){
        and  u.group =#group#
    @}
    @if(!isEmpty(username)){
        and  u.username like #"%"+username+"%"#
    @}
    @if(!isEmpty(username)){
        and  u.username like #"%"+username+"%"#
    @}
    @if(!isEmpty(username)){
        and  u.nickname like #"%"+nickname+"%"#
    @}
    @if(!isEmpty(status)){
        and  u.status in (#status#)
    @}else{
        and u.status in (0,1)
    @}
    @if(!isEmpty(jobType0)){
        and  u.job_type0= #jobType0#
    @}
    @if(!isEmpty(jobType1)){
        and  u.job_type1= #jobType1#
    @}
    @if(!isEmpty(createDateMin)){
        and  u.create_time>= #createDateMin#
    @}
    @if(!isEmpty(createDateMax)){
        and  u.create_time< #nextDay(createDateMax)#
    @}
    @if(!isEmpty(sortField) && !isEmpty(sortType)){
        order by #sortField# #sortType#
    @}
    
    

batchDelUserByIds
===
    update core_user u set u.status = 2 where u.id in( #join(ids)#)
    
batchUpdateUserState
===
    update core_user u set u.status = #status# where u.id in( #join(ids)#)
    
queryUserRole
===

* 查询用户所有权限

    select	
    ur.*, u.username as user_code,
    u.username as user_name,
    org.username as org_name, role.username as role_name
    from core_user_role ur
    left join core_org org on org.id = ur.org_id
    left join core_user u on u.id = ur.user_id
    left join core_role role on role.id = ur.role_id
    where u.id=#id# 
    @if(isNotEmpty(orgId)){
    	and org.id=#orgId#
    @}
    @if(isNotEmpty(roleId)){
    	and role.id=#roleId#
    @}
