/*
	配置管理
*/
app.controller('rokid_config',['$scope','ajaxService',function($scope,ajaxService){

	/*获取列表*/
	$scope.list={};
	
	/*保存*/
    $scope.update=function(arr){
    	var url = '8002';
	    ajaxService.ajaxGet(arr,url,function(result){
	        if(result){
	            returnDate(result, "update");
	        }
	    });
    }
    
    /*保存*/
    $scope.listAll=function(){
    	var url = '8001';
	    ajaxService.ajaxGet({},url,function(result){
	        if(result){
	            returnDate(result, "list");
	        }
	    });
    }
    $scope.listAll();

    /*数据返回处理 */
	function returnDate(ret,opt){
		if(debug) console.log(ret);
		var data = JSON.parse(ret.data);
		var msg = '';    
		if(opt == "list"){
			$scope.list=data.list;
		}else if(opt == "update"){
			msg = '修改成功！';
    	}
		infoMsg(msg);
	}
}]);

$(document).ready(function(){
	setTimeout(
		function(){
			$("#over").hide();
			$("#layout").hide();
		}, 100);	
});