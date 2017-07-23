/*
	聊天标注
*/
app.controller('rokid_answer',['$scope','ajaxService',function($scope,ajaxService){

	/*获取列表*/
	$scope.list={};
	$scope.key={};
	$scope.row={};
    $scope.pageGet=function(page){
        page=page?page:1;
        var json={
        	page:page,
        	name:$scope.key.name,
        	question:$scope.key.question,
        	answer:$scope.key.answer
        }
        ajaxService.ajaxGet(json,'4003',function(result){
            if(result){
                returnDate(result,'list');
            }
        });
    }
    $scope.pageGet();
    /*获取列表-end*/

    /*判断是否管理员*/
    if(JSON.parse(localStorage.userMsg).vo.type==1){
    	$scope.admin=true;
    }
    /*判断是否管理员-end*/

    /*保存*/
    $scope.save=function(arr){
    	if(!checkInput(arr)){
    		return;
    	} 
    	$scope.row=arr;
	    ajaxService.ajaxGet(arr,'4004',function(result){
	        if(result){
	            returnDate(result,'save');
	        }
	    });
    }
    function checkInput(arr){
    	if(arr.name==''){
    		errMsg('请输入话题！');
    		return false;
    	}
    	if(!arr.questionList) return true;
    	for(var i=0; i < arr.questionList.length; i++){
    		if(arr.questionList[i].text == ''){
    			errMsg('请输入句式分发内容！');
        		return false;
    		}
    	}
    	if(!arr.answerList) return true;
    	for(var i=0; i < arr.answerList.length; i++){
    		if(arr.answerList[i].text == ''){
    			errMsg('请输入话题整理内容！');
        		return false;
    		}
    	}
    	  
    	if(!checkName(arr.questionList, "句式分发")){
    		return false;
    	}
    	
    	if(!checkName(arr.answerList, "话题整理")){
    		return false;
    	}

    	return true;
    }   
    
    function checkName(list, field){
    	var x = 1;
    	var ret = true;
    	angular.forEach(list,function(a){
    		if(ret && a.act != "del"){
	    		for(var y = x; y < list.length; y++){
	    			if(list[y].act == "del"){continue;}
	    			if(a.text.trim() == list[y].text.trim()){    				
	    				errMsg(field + ',内容['+a.text +'] 已存在！');
	    				ret = false;
	    				return ret;
	    			}    			
	    		}
    		}
    		x++;
    	})
    	return ret;
    }
    
    /*添加话题*/
    $scope.add=function(){
    	layer.prompt({title: '请输入需要添加的话题', formType: 2}, function(text, index){
    		if(text.trim() == ""){
    			errMsg("请输入话题内容！");
    			return;
    		}
    		ajaxService.ajaxGet({name:text,score:0},'4004',function(result){
    	        if(result){
    	            returnDate(result,'add');
    	        }
    	    });
    	});
    }
    
    $scope.question={
    		del:function(list, one){
        		if(one.act == 'add'){
        			list.splice(list.indexOf(one), 1);
        			if(debug) console.log(list);
        			return;
        		}
        		one.act = "del";  
        		if(debug) console.log(list);
        	},
        	change:function(one){
        		if(one.act == 'load'){
        			one.act = "edit";
        		}
        		if(debug) console.log(one);
        	},    	
        	add:function(list){
        		var one ={
        			id:'',
        			text:'',
        			act:'add'
        		};
        		list.unshift(one); 
        		if(debug) console.log(list);
        	}    	
    };
	$scope.answer={
    	del:function(list, one){
    		if(one.act == 'add'){
    			list.splice(list.indexOf(one), 1);
    			if(debug) console.log(list);
    			return;
    		}
    		one.act = "del";  
    		if(debug) console.log(list);
    	},
    	change:function(one){
    		if(one.act == 'load'){
    			one.act = "edit";
    		}
    		if(debug) console.log(one);
    	},    	
    	add:function(list){
    		var one ={
    			id:'',
    			text:'',
    			act:'add'
    		};
    		list.unshift(one); 
    		if(debug) console.log(list);
    	}
    };
        
    /*删除*/
    $scope.del=function(row){
    	layer.confirm('该话题下，所有句式分发将被设置为未标注,话题整理将全部被删除！  确认删除吗？', 
    		{
    			btn: ['删除','取消'] //按钮
    		},function(){
    			ajaxService.ajaxGet(row,'4005',function(result){
    				if(result){
    					$scope.row=row;
    					returnDate(result,'del');
    				}
    			});
    			layer.closeAll('dialog');
    		},function(){
    		  
    		}
    	);
    }

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
        }else if(opt=='save'){
        	$scope.row.questionList = data.questionList;
        	$scope.row.answerList = data.answerList;
        	if(debug) console.log($scope.row);
        	msg = '保存成功!';
        }else if(opt=='add'){
        	if(ret.code == 1){
	        	$scope.list.unshift(data.vo);
	        	msg = '添加成功!';
        	}
        }else if(opt=='del'){
        	if(ret.code == 1){
	        	$scope.list.splice($scope.list.indexOf($scope.row), 1);
	        	msg = '删除成功!';
        	}
        }
        
        infoMsg(msg);
	}
}]);