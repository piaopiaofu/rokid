/*
	登录
*/
app.controller('login',['$scope','ajaxService',function($scope,ajaxService){

    $scope.login={};

    $scope.post=function(){
    	if(!$scope.loginForm.$valid){
    		//if(debug) console.log($scope.loginForm.$error);
    		return;
    	}
        ajaxService.ajaxGet($scope.login,'1001',function(result){
            if(result) {
                returnDate(result,'login');
            }
        });
    }

    /*数据返回处理 */
	function returnDate(ret,opt){
		if(debug) console.log(ret);
		var data = JSON.parse(ret.data);
	    if(opt=='login'){
	        if(data){
	          localStorage.userMsg=JSON.stringify(data);
	          localStorage.loginTime=new Date().getTime();
	          window.location.href='main.html?'+localStorage.loginTime;
	        }
	    }
	}
}]);

$(document).ready(function(){
	setTimeout(
		function(){
			$("#over").hide();
			$("#layout").hide();
		}, 1);	
});
