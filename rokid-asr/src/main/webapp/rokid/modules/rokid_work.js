/*
	用户管理
*/
app.controller('rokid_user',['$scope','ajaxService','$filter',function($scope,ajaxService,$filter){
	
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
			layer.confirm('确认删除改指派数据？', 
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
			$scope.row = info;
			url='6002';
			ajaxService.ajaxGet(info,url,function(result){
		        if(result){
		            returnDate(result,act);
		        }
		    });
		}
	}
	$scope.add=function(){
		$scope.list.unshift({});
	}
	
	$scope.chgDate=function (id, info){
		$('#laydate_today').focus();
		var format = 'YYYY-MM-DD';
		laydate({istime: true, format: format,choose: function(dates){
			if(id == 'start'){
				info.startTime = dates;
			}else{
				info.endTime = dates;
			}
			$('#'+id).focus();
			$scope.$apply();
		}});
	}
	
	/*保存检查*/
    function checkInput(info){
    	if(info.startTime == null || info.startTime == ""){
    		infoMsg('请输入分配开始时间！');
    		return false;
    	}else if(info.endTime == null || info.endTime == ""){
    		infoMsg('请输入分配结束时间！');
    		return false;
    	}else if(info.endTime < info.startTime){
    		infoMsg('分配结束时间要大于等于开始时间！');
    		return false;
    	}else if(!info.voice && !info.asr){
    		infoMsg('指派标注类型必须选择其一！');
    		return false;
    	}
    	return true;
    }
    /*类表状态改变-end*/
		
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
	    	$scope.list.forEach(function(e){  
	    		e.startTime = $filter('date')(e.startTime, "yyyy-MM-dd"); 
	    		e.endTime = $filter('date')(e.endTime, "yyyy-MM-dd"); 
	    	});
        }else if(opt=='update'){
        	if($scope.row.id){
        		angular.copy($scope.row, $scope.selRow);
        		msg = "修改成功";
        	}else{
        		msg = "添加成功";
        		$scope.row.id = data.id;
        		//$scope.list.unshift($scope.row);
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