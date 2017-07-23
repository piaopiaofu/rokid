/*
	配置管理
*/
app.controller('rokid_config',['$scope','ajaxService',function($scope,ajaxService){

	/*获取列表*/
	$scope.list={};
	
	/*保存*/
    $scope.update=function(arr){
    	var url = '3002';
	    ajaxService.ajaxGet(arr,url,function(result){
	        if(result){
	            returnDate(result, "update");
	        }
	    });
    }
    
    /*保存*/
    $scope.listAll=function(){
    	var url = '3001';
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
	
	$scope.isadm=false;
	$(document).ready(function(){
		var s = "（标注者）";
		if(localStorage.type==1){
			$scope.isadm=true;
			s = "（管理者）";
		}
		$("#user").html(localStorage.user+s);
	});
	
	/*logout*/
	$scope.logout=function(act, arr){
		var json={};
	    ajaxService.ajaxGet(json,'0009',function(result){
	        if(result){
	        	window.top.location.href='login.html';
	        }
	    });
	}
}]);