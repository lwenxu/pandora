layui.define(['table', 'menuApi','treeGrid'], function(exports) {
	var menuApi = layui.menuApi;
	var table=layui.table;
	var treeGrid = layui.treeGrid;
	var view = {
			init:function(){
				
			},
			delBatch:function(){
				var data = Common.getMoreDataFromTable(treeGrid,"menuTable");
				if(data==null){
					return ;
				}
				Common.openConfirm("确认要删除这些菜单?",function(){
					var ids =Common.concatBatchId(data);
					menuApi.del(ids,function(){
						Common.info("删除成功");
						dataReload();
					})
				})
				
			}
				
	}
	 exports('del',view);
	
});