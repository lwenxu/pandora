layui.define(['table', 'orgApi','treeGrid'], function(exports) {
	var orgApi = layui.orgApi;
	var table=layui.table;
	var treeGrid = layui.treeGrid;
	var view = {
			init:function(){
				
			},
			delBatch:function(){
				var data = Common.getMoreDataFromTable(treeGrid,"orgTable");
				if(data==null){
					return ;
				}
				Common.openConfirm("确认要删除这些结构?",function(){
					var ids =Common.concatBatchId(data);
					orgApi.del(ids,function(){
						Common.info("删除成功");
						dataReload();
					})
				})
				
			}
				
	}
	 exports('del',view);
	
});