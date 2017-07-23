/*
	主页
*/
app.controller('main',['$scope','ajaxService',function($scope,ajaxService){
	$scope.time = localStorage.loginTime;
	/*权限分类*/
	$scope.data=[{
		level:"0",
		url:"voice.html?"+$scope.time,
		cn:"激活语音标注"
		//,en:"Voice Trigger" 
	},{
		level:"0",
		url:"asrNlp.html?"+$scope.time,
		cn:"语音识别标注　",
		en:""
//	},{
//		level:"0",
//		url:"javascript:void(0);",
//		cn:"聊天标注　",
//		//en:"Chat-Group ",
//		sub:[{
//			level:"0",
//			url:"question.html?"+time,
//			en:"句式分发"
//		},{
//			level:"0",
//			url:"chat.html?"+time,
//			en:"话题整理 "
//		}]
	},{
		level:"1",
		url:"javascript:void(0);",
		cn:"统计页面 　",
		//en:"Statistics ",
		sub:[{
			level:"1",
			url:"reportVoice.html?"+$scope.time,
			en:"语音激活",
		},{
			level:"1",
			url:"reportAsr.html?"+$scope.time,
			en:"语音识别" 
		}
//		,{
//			level:"1",
//			url:"reportChatNew.html?"+$scope.time,
//			en:"聊天标注",
//		}
		,{
			level:"1",
			url:"reportAct.html?"+$scope.time,
			en:"活跃度" 
		}
		]
	},{
		level:"1",
		url:"userManage.html?"+$scope.time,
		cn:"标注管理页面　"
		//,en:"Users"
	},{
		level:"1",
		url:"workManage.html?"+$scope.time,
		cn:"任务指派　"
		//,en:"Works"
	},{
		level:"1",
		url:"config.html?"+$scope.time,
		cn:"配置管理　"
		//,en:"Import"
	},
//	,{
//		level:"1",
//		url:"first.html?"+$scope.time,
//		cn:"数据导入"
//		//,en:"Import"
//	},
	{
		level:"1",
		url:"filter.html?"+$scope.time,
		cn:"黑名单　"
		//,en:"Import"
	}
	];
	$scope.menu=[];
	$scope.userInfo=JSON.parse(localStorage.userMsg).vo;
	angular.forEach($scope.data,function(e){
		var level=$scope.userInfo.type;
		if(level == 1 || e.level==0){
			$scope.menu.push(e);
		}
	});
	/*权限分类-end*/

	/*logout*/
	$scope.logout=function(act, arr){
		var json={};
	    ajaxService.ajaxGet(json,'1100',function(result){
	        if(result){
	            returnDate(result,'logout');
	        }
	    });
	}
	
	/*数据返回处理 */
	function returnDate(ret,opt){
		if(debug) console.log(ret);
		var data = JSON.parse(ret.data);
	    if(ret.code==1){
	    	window.top.location.href='login.html';
	    }
	}
}]);

$(function(){
	$('.layui-nav .layui-nav-item').click(function(){
		$(this).siblings().removeClass('layui-this');
		$(this).addClass('layui-this');
	});
	$('.layui-nav .layui-nav-item:first-child').addClass('layui-this');
});

$(document).ready(function(){
	setTimeout(
		function(){
			$("#over").hide();
			$("#layout").hide();
		}, 1);	
});
