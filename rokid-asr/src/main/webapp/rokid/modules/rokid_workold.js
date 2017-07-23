/*
	用户管理
*/
app.controller('rokid_user',['$scope','ajaxService','$filter',function($scope,ajaxService,$filter){
	
	$scope.boxmsg={
		detail:0,
		user:0
	}

	/*获取类表*/
	$scope.list={
	};
	$scope.pageGet=function(page){
		page=page?page:1;
	    ajaxService.ajaxGet({page:page},'6001',function(result){
	        if(result){
	            returnDate(result,'list');
	        }
	    });
	}
	$scope.pageGet();
    /*获取类表-end*/

    /*删除选择用户*/
    $scope.delUser=function(row){
    	$scope.info.userList.splice(row,1);
    }
    /*删除选择用户-end*/
    
    $scope.row = {};
	$scope.update=function(info, act){
		var url;
		if(act=='delete'){
			$scope.row = info;
			layer.confirm('已指派日期信息将被删除！<br>确认删除吗？', 
	    		{
	    			btn: ['删除','取消'] //按钮
	    		},function(){
	    			ajaxService.ajaxGet(info,'6003',function(result){
	    		        if(result){
	    		            returnDate(result,act);
	    		        }
	    		    });
	    			layer.closeAll('dialog');
	    		},function(){}
			);
		}else if(act=='update'){
			if(!checkInput(info)) return;
			url='6002';
			ajaxService.ajaxGet(info,url,function(result){
		        if(result){
		            returnDate(result,act);
		        }
		    });
		}
	}
	
	/*保存检查*/
    function checkInput(info){
    	if(!info.voice && !info.asr && !info.chat){
    		errMsg('指派标注类型必须选择其一！');
    		return false;
    	}else if(info.userList.length == 0){
    		errMsg('指派用户必须选择！');
    		return false;
    	}
    	return true;
    }
    /*类表状态改变-end*/
	
	
	/*标注详细*/
	$scope.selRow={};
	$scope.editLoad=function(row){
		$scope.info={};
		angular.copy(row, $scope.info);
		$scope.selRow = row;
		$scope.boxmsg.detail = 1;
		if(($scope.info.time+'').length == 7){
			$("#dateType")[0].value = 'm';
			$scope.info.dateType = 'm';
		}else{
			$("#dateType")[0].value = 'd';
			$scope.info.dateType = 'd';
		}
		if(debug) console.log($scope.info);
		
	}
	$scope.addLoad=function(){
		$scope.info = {
			id:'',
			userList:[],
			time:dateNow,
			voice:false,
			asr:false,
			chat:false,
			dateType:'d'
		};
		$scope.selRow = {};
		$scope.boxmsg.detail = 1;
		if(debug) console.log($scope.info);		
	}
	var date = new Date();
	$scope.chgDate=function (id){
		var format = 'YYYY-MM-DD';
		if($scope.info.dateType == 'm') format = 'YYYY-MM';
		laydate({istime: true, format: format,choose: function(dates){
			$scope.info.time=$("#date").val();
			$scope.$apply();
		}});
	}
	$scope.chgDateType=function (id){
		if($scope.info.dateType == 'm'){
			if($scope.info.time != ''){
				$("#date").val($scope.info.time.substring(0,7));
			}
		}else if($scope.info.dateType == 'd'){
			if($scope.info.time != ''){
				$("#date").val(dateNow);
			}
		}
		$scope.info.time=$("#date").val();
	}
	var dateNow = null;
	$(document).ready(function(){
		dateNow = getFormatDate(new Date(),"yyyy-MM-dd");
	});
	
	/*获取用户*/
	$scope.userList={};
	$scope.userName='';
	$scope.pageUserGet=function(page){
		$scope.boxmsg.user=1;
		page=page?page:1;
	    ajaxService.ajaxGet({page:page, isValid:1, user:$scope.userName},'1003',function(result){
	        if(result){
	            returnDate(result,'userList');
	        }
	    });
	}
	/*选择用户*/
	$scope.userSel=function(row){
		selUser = {
			id:row.id,
			value:row.user
		}
		for(var i=0; i <$scope.info.userList.length; i++){
			if(row.id == $scope.info.userList[i].id){
				errMsg('选择的用户已存在！');
				return;
			}
		}		
		$scope.info.userList.push(selUser);
		$scope.boxmsg.user=0;	    
	}
    /*获取用户-end*/
	
    /*
		分页
		number=总页码
		maxmin=最大值与最小值
		current=当前页码
		pagination=页码数据
		go=处理跳转
		paginationLogic=处理页码数据
    */
    $scope.page={
    	pagination:[],
    	number:'',
    	maxmin:{},
    	current:1,
    	paginationLogic:function(number,cur){
    		/*页面显示*/
			this.maxmin={
				min:1,
				max:number
			}
			var size=10;
			var page=[];
			var i=0;
			var totalPage = number;
			if(number>size){
				if(cur>=(size-5)){
					i = cur - 5;
					if(number >= cur + 4){
						number = cur + 4;
					}else{
					}
				}else{
					number=size - 1;
				}
			}
			if(cur > 5){
				page.push({
    				page:1,
    				text:'1...'
    			});
			}
			for(var i=i;i<number;i++){
    			page.push({
    				page:i+1,
    				text:i+1
    			});
    		}
			if(totalPage > cur + 4){
				page.push({
    				page:totalPage,
    				text:'...'+totalPage
    			});
			}
    		this.pagination=page;
    	},
    	go:function(page){
    		/*跳转操作*/
			switch(page)
			{
				case 'min':
				$scope.pageGet(this.maxmin.min);
				break;
				case 'max':
				$scope.pageGet(this.maxmin.max);
				break;
				case 'prev':
				$scope.pageGet(this.current-1);
				break;
				case 'next':
				$scope.pageGet(this.current+1);
				break;
				default:
				if(page>this.maxmin.max){
					page=this.maxmin.max;
				}else if(page<this.maxmin.min){
					page=this.maxmin.min;
				}
				$scope.pageGet(page);
			}
    	}
    };
    /*分页-end*/

    /*数据返回处理 */
	function returnDate(ret,opt){
		if(debug) console.log(ret);
		var data = JSON.parse(ret.data);
		var msg = "";
	    if(opt=='list'){
	    	/*页码处理*/
	    	$scope.page.paginationLogic(data.pageCount,data.page);
	    	$scope.page.current=data.page;
	    	/*页码处理-end*/
	    	$scope.list=data.list;
        }else if(opt=='userList'){
        	$scope.userList=data.list;
        }else if(opt=='update'){     	
        	$scope.boxmsg.detail = 0;        	
        	if($scope.info.id){
        		angular.copy($scope.info, $scope.selRow);
        		msg = "修改成功";
        	}else{
        		msg = "添加成功";
        		$scope.info.id = data.id;
        		$scope.list.unshift($scope.info);
        	}
        }else if(opt=='delete'){
        	if(ret.code == 1){
	        	$scope.list.splice($scope.row,1);
	        	msg = "删除成功！";
        	}
        }
	    
	    infoMsg(msg);
	}
}]);