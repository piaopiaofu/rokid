/*
	黑名单
*/
app.controller('rokid_filter',['$scope','ajaxService','$sce',function($scope,ajaxService,$sce){

	/*获取列表*/
	$scope.list={};
	
	$scope.boxmsg = {
		showFlg:false,
		snList:"",
		addPost:function(){
			$scope.boxmsg.snList = $scope.boxmsg.snList.trim();
			if($scope.boxmsg.snList == ""){
				errMsg('请输入机器号！');
	    		return false;
			}
			var url = '9004';
		    ajaxService.ajaxGet({snList:$scope.boxmsg.snList},url,function(result){
		        if(result){
		            returnDate(result, "snList");
		        }
		    });	
		},
		close:function(){
    		this.showFlg=false;
    		$scope.boxmsg.showFlg = false;
    	},
    	showWin:function(){
    		this.showFlg=true;
    		$scope.boxmsg.showFlg = true;
    	}
	};
	$scope.boxmsg.showFlg = false;
		
	/*保存*/
	$scope.row={};
    $scope.update=function(arr){
    	if(!checkInput(arr)) return false;
    	$scope.row = arr;
    	var url = '9002';
	    ajaxService.ajaxGet(arr,url,function(result){
	        if(result){
	            returnDate(result, "update");
	        }
	    });
    }
    $scope.add=function(){
    	var vo = {id:"",sn:"",memo:""};
    	$scope.list.unshift(vo);
    }
    
    function checkInput(arr){
    	if(arr.sn == ''){
    		errMsg('请输入机器号！');
    		return false;
    	}
    	return true;
    }

    $scope.del=function(arr){
    	layer.confirm('确定删除该黑名单？', 
    		{
    			btn: ['删除','取消'] //按钮
    		},function(){
		    	$scope.row = arr;
		    	var url = '9003';
			    ajaxService.ajaxGet(arr,url,function(result){
			        if(result){
			            returnDate(result, "delete");
			        }
			    });
    		},function(){
      		  
    		}
    	);
    }
    
    /*一栏*/
    $scope.pageGet=function(page){
    	page=page?page:1;
    	var url = '9001';
	    ajaxService.ajaxGet({"page":page},url,function(result){
	        if(result){
	            returnDate(result, "list");
	        }
	    });
    }   
    $scope.pageGet();
    $scope.sce = $sce.trustAsResourceUrl;//解决src资源问题
    
    /*  分页
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
		var msg = '';    
		if(opt == "list"){
			/*页码处理*/
            $scope.page.paginationLogic(data.pageCount,data.page);
            $scope.page.current=data.page;
            /*页码处理-end*/
			$scope.list=data.list;
		}else if(opt == "update"){
			$scope.row.id = data.row.id;
			msg = '修改成功！';
    	}else if(opt == "delete"){
    		$scope.list.splice($scope.list.indexOf($scope.row), 1);
			msg = '删除成功！';
    	}else if(opt == "snList"){
    		msg = data.succ + "<br>" + data.err;
    		$scope.boxmsg.showFlg = false;
    		$scope.boxmsg.snList = "";
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