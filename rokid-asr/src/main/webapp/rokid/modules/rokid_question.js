/*
	聊天标注
*/
app.controller('rokid_question',['$scope','ajaxService',function($scope,ajaxService){

	/*获取列表*/
	$scope.list={};
	$scope.assignList = ["", "chat", "music", "voice"];
	$scope.key={};
	$scope.getWork=null;
	$scope.pageGet=function(page){
		page=page?page:1;
	    ajaxService.ajaxGet({page:page,getWork:$scope.list.length},'4001',function(result){
            if(result){
                returnDate(result,'list');
            }
        });
    }
    $scope.pageGet();
    /*获取列表-end*/
    
    $scope.chgAssign=function(row){
       if(row.assignId != ''){
    	   row.chatId = '';
    	   row.chatName = '';
       }
       $scope.save(row);
    }

    /*判断是否管理员*/
    if(JSON.parse(localStorage.userMsg).vo.type==1){
    	$scope.admin=true;
    }
    /*判断是否管理员-end*/

    /*调用filter*/
    $scope.key.topic = '';
    $scope.filter={
    	list:function(arr){
    		$("#filter").show();
    		var json = {
	        	topic:$scope.key.topic
    		}
    		$scope.filter.data=[];
    		if($scope.key.topic==''){
    			infoMsg("请输入查询关键字!");
    			return;    		
    		}
		    ajaxService.ajaxGet(json,'4006',function(result){
		        if(result){
		            returnDate(result,'filter');
		        }
		    });
    	},
    	data:"",
    	inflg:false,
    	check:"",
    	edit:function(arr){
    		this.check.chatId=arr.gid;
    		this.check.chatName=arr.strSent;
    		//选择话题，指派变空
    		this.check.assignId = '';
    		this.inflg=this.inflg?false:true; 
    		$scope.save(this.check);
    	},
    	show:function(arr){
    		$scope.key.topic=arr.topic;;
   			this.list();	
            $scope.add.show=false;
    		this.inflg=this.inflg?false:true;
    		this.check=arr;
    	},
    	close:function(arr){
    		$scope.key.topic='';
            $scope.add.show=false;
    		this.inflg=this.inflg?false:true;
    		this.check=arr;
    	}
    };
    /*调用filter-end*/

    /*filter-新建话题*/
    $scope.add={
    	show:false,
    	info:{
    		score:"",
    		name:""
    	},
    	save:function(){
    		var info=this.info;
                info.score=0;
		    ajaxService.ajaxGet(info,'4004',function(result){
		        if(result){
		            returnDate(result,'add');
		        }
		    });
    	}
    };
    /*filter-新建话题-end*/

    /*保存*/
    $scope.save=function(arr){
    	if(!checkInput(arr)) return;
	    ajaxService.ajaxGet(arr,'4002',function(result){
	        if(result){
	            returnDate(result,'save');
	        }
	    });
    }
    /*保存检查*/
    function checkInput(arr){
    	if(!arr.assignId && !arr.chatId){
    		errMsg('话题与指派必须选择其一！');
    		return false;
    	}
    	return true;
    }
    /*保存-end*/


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
		var msg = "";
        if(opt=='list'){
            /*页码处理*/
            $scope.page.paginationLogic(data.pageCount,data.page);
            $scope.page.current=data.page;
            /*页码处理-end*/
            $scope.list=data.list;
            infoMsg(data.info);
        }else if(opt=='save'){
        	//msg = '保存成功!';
        }else if(opt=='filter'){
	    	$scope.filter.data=JSON.parse(data.chatfilterList);
	    	msg = "共找到" + $scope.filter.data.length + "条关于【"+$scope.key.topic+"】数据！";
	    }else if(opt=='add'){
	    	$scope.add.show=false;
	    	$scope.add.info={
    			score:"",
    			name:""
    		};
	    	var vo = {
    			fScore:"",
    			strSent:data.vo.name,
    			gid:data.vo.id
    		};
            $scope.filter.data.unshift(vo);
	    	msg = '添加话题成功!';
	    }
        
        infoMsg(msg);
	}
}]);