/*
	用户管理
*/
app.controller('rokid_user',['$scope','ajaxService','$filter',function($scope,ajaxService,$filter){
	
	$scope.boxmsg={
		add:0,
		detail:0
	};

	/*获取用户*/
	$scope.list={};
	$scope.pageGet=function(page){
		page=page?page:1;
	    ajaxService.ajaxGet({page:page},'1003',function(result){
	        if(result){
	            returnDate(result,'list');
	        }
	    });
	}
	$scope.pageGet();
    /*获取用户-end*/

	/*用户状态改变*/
	$scope.userInfo={};
	$scope.update=function(act,arr){		
		var url;
		if(act=='delete'){
			$scope.userInfo={};
			$scope.userInfo.id=arr.id;
			url='1004';
			$scope.delInfo=arr;
			layer.confirm('确认删除该用户吗？', 
	    		{
	    			btn: ['删除','取消'] //按钮
	    		},function(){	    			
	    		    ajaxService.ajaxGet($scope.userInfo,url,function(result){
	    		        if(result){
	    		            returnDate(result,act);
	    		        }
	    		    });	    		    
	    			layer.closeAll('dialog');
	    		},function(){
	    		}
	    	);
			return;
		}else if(act=='valid'){
			url='1002';
			$scope.userInfo=arr;
			$scope.userInfo.isValid=arr.isValid?0:1;
			act = 'valid'+$scope.userInfo.isValid;
		    ajaxService.ajaxGet($scope.userInfo,url,function(result){
		        if(result){
		            returnDate(result,act);
		        }
		    });
		}else if(act=='load'){			
			$scope.userInfo=arr;
			$scope.boxmsg.add=1;
		}else if(act==''){			
			$scope.userInfo={};
			$scope.userInfo.type=2;
			$scope.boxmsg.add=1;
		}else if(act=='update' || act=='add'){
			url='1002';
		    ajaxService.ajaxGet($scope.userInfo,url,function(result){
		        if(result){
		            returnDate(result,act);
		        }
		    });
		}
	}
    /*用户状态改变-end*/
	
	$scope.key={};
	var date = new Date();
	$scope.chgDate=function (id){
		laydate({istime: true, format: 'YYYY-MM-DD',choose: function(dates){
			if(id=='start'){
				$scope.key.start=$("#"+id).val();
			}else{
				$scope.key.end=$("#"+id).val();
			}
			$scope.$apply();
		}});
	}
	var date = new Date();
	$(document).ready(function(){
		$("#end").val(getFormatDate(date,"yyyy-MM-dd"));
		date.setMonth(date.getMonth() - 1);
		date.setDate(date.getDate() + 1);
		$("#start").val(getFormatDate(date ,"yyyy-MM-dd"));		
		$scope.key.start=$("#start").val();
		$scope.key.end=$("#end").val();
	});
	
	/*标注详细*/
	$scope.countInfo={};
	$scope.countUser="";
	$scope.detail={
		data:{},
		show:function(arr){
			$scope.countInfo={};
			$scope.countUser = arr.user+"（"+arr.typeName+"）";
			$scope.boxmsg.detail=1;
			this.data=arr;
		},
		search:function(date){
			var json={
				userId:this.data.id,
				start:$scope.key.start,
				end:$scope.key.end
			};
		    ajaxService.ajaxGet(json,'1006',function(result){
		        if(result){
		            returnDate(result,'count');
		        }
		    });
		}
	};
    /*标注详细-end*/

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
	    if(opt=='add'){
	    	$scope.boxmsg.add=0;
	    	msg = "用户已添加！";
	    	$scope.list.unshift(data.vo);
	    }else if(opt=='update'){
	    	$scope.boxmsg.add=0;
	    	$scope.userInfo = data.vo;
	    	msg = "用户已修改！";
	    }else if(opt=='list'){
	    	/*页码处理*/
	    	$scope.page.paginationLogic(data.pageCount,data.page);
	    	$scope.page.current=data.page;
	    	/*页码处理-end*/
	    	$scope.list=data.list;
	    }else if(opt=='count'){
	    	$scope.countInfo=data.vo;
	    	msg = "统计完成！";
	    }else if(opt=='valid0'){
	    	msg = "用户已禁用！";
        }else if(opt=='valid1'){
        	msg = "用户已启用！";
        }else if(opt=='delete'){
        	msg = "用户已删除！";
        	$scope.list.splice($scope.delInfo,1);
        }
	    infoMsg(msg);
	}
}]);