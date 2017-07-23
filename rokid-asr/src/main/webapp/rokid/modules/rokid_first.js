/*
	聊天标注
*/
app.controller('rokid_first',['$scope','ajaxService',function($scope,ajaxService){

	/*保存*/
    $scope.import=function(act){
    	var url = "";
    	if(act == "voice"){
    		url = '7001';
    	}else if(act == "question"){
    		url = '7002';
    	}else if(act == "chat"){
    		url = '7003';
    	}else if(act == "answer"){
    		url = '7004';
    	}
	    ajaxService.ajaxGet({},url,function(result){
	        if(result){
	            returnDate(result,'save');
	        }
	    });
    }

    /*数据返回处理 */
	function returnDate(ret,opt){
		if(debug) console.log(ret);
		var data = JSON.parse(ret.data);
		var msg = data.info;        
		layer.alert(msg, {
			tips: [1, '#3595CC']
		});
	}
}]);

$(document).ready(function(){
	setTimeout(
		function(){
			$("#over").hide();
			$("#layout").hide();
		}, 100);	
});