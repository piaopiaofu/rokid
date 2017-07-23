/*
	语音标注
*/
app.controller('rokid_yuyin',['$scope','ajaxService','$sce',function($scope,ajaxService,$sce){

	/*获取列表*/
	$scope.list={};
	$scope.listUser={};
	
    $scope.animate = function(){
        $scope.flag = !$scope.flag;
        if($scope.flag){
        	$("#showFilter").attr("class","dot-top");
        }else{
        	$("#showFilter").attr("class","dot-bottom");
        }
    }
	
	$scope.getWork=null;
	$scope.pageGet=function(page){
		if($scope.list.length >= 151 && $scope.getWork){
			infoMsg('未标注数据过多，请标注后再获取！');
			return;
		}
		page=page?page:1;
		$scope.keyBak.page = page;
		$scope.keyBak.getWork = $scope.getWork?1:null;
		angular.copy($scope.keyBak, $scope.key);
	    ajaxService.ajaxGet($scope.keyBak,'2001',function(result){
	        if(result){
	            returnDate(result,'list');
	        }
	    });
	}
	
	$scope.chgDate=function (id){
		$('#laydate_today').focus();
		var format = 'YYYY-MM-DD';
		laydate({istime: true, format: format,choose: function(dates){
			if(id == 'start'){
				$scope.key.start = dates;
			}else{
				$scope.key.end = dates;
			}
			$('#'+id).focus();
			//$("#search").focus();
			$scope.$apply();
		}});
	}
	
	$scope.key = {};
	$scope.keyBak = {};
	$scope.search=function (){
		$scope.key.start = $('#start').val();
		$scope.key.end = $('#end').val();
		angular.copy($scope.key, $scope.keyBak);
		$scope.pageGet(1);
	}
	
	$scope.keyEnter=function(event){
		if(event.keyCode == "13"){  
			$scope.search();
		}  
	}  
	
	$scope.pageGet();
    $scope.sce = $sce.trustAsResourceUrl;//解决src资源问题
    /*获取列表-end*/

    /*判断是否管理员*/
    if(JSON.parse(localStorage.userMsg).vo.type==1){
    	$scope.admin=true;
    }
    /*判断是否管理员-end*/

	/*用户状态改变*/
    $scope.row = {};
    $scope.type = null;
	$scope.update=function(row, type){
		var act = "save";
		//angular.copy(row, $scope.row);
		$scope.row = row;
		$scope.type = type;
		var json={
			id:row.id,
			type:type,
			updName:row.updName
		};
	    ajaxService.ajaxGet(json,'2002',function(result){
	        if(result){
	            returnDate(result, act);
	        }
	    });
	}
    /*用户状态改变-end*/

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
	$scope.audioPath = '';
	function returnDate(ret,opt){
		if(debug) console.log(ret);
		var data = JSON.parse(ret.data);
	    if(opt=='list'){
	    	/*页码处理*/
	    	$scope.page.paginationLogic(data.pageCount,data.page);
	    	$scope.page.current=data.page;
	    	/*页码处理-end*/
	    	$scope.audioPath=data.path;
	    	$scope.list={};
	    	$scope.list=data.list;
	    	/*添加控制播放*/
	    	setTimeout(function(){playController();}, 10);
	    	infoMsg(data.info);
	    	/*用户一栏*/
	    	if($scope.admin && data.page == 1){
	    		$scope.listUser = data.listUser;
	    	}
	    }else if(opt=='save'){
	    	$scope.row.type = data.row.type;
	    	$scope.row.updName = data.row.updName;
	    	$scope.row.editAdmin = data.row.editAdmin;
	    	//infoMsg('保存成功');
        }else{
        	
        }
	}

	/*语音控制*/
	var oldAudio = null;
	function playController(){
		var audios = $("audio");
		var index = 0;
		$scope.list.forEach(function(row){
			// src
			row.path = $scope.audioPath + row.path;//"http://localhost:8080/wav/err.wav";//$scope.audioPath + row.path;
			audios[index].src = row.path;
			// error
			audios[index].addEventListener('error', function(e){
				errWav(this, row);
			});
			// play
			audios[index].addEventListener('play', function(e){
				try{if(oldAudio != this)$(oldAudio)[0].pause();}catch(e){}
				oldAudio = this;
			});						
			index++;
		});
	}
	
	function errWav(obj, row){
		setTimeout(function(){$(obj).parent().parent().hide();},100);
		// 语音存在且不可播放，更新为405
		if(row.fstatus == null || row.fstatus == 200){
			$scope.updStatus(row);
		}
	}
	$scope.errWav=function(row){
		setTimeout(function(){$("#"+row.id).hide();},100);
		// 语音存在且不可播放，更新为405
		row.fstatus = 200;
		$scope.updStatus(row);
	}
	$scope.updStatus=function(row){
		var json={
			"id":row.id,
			"path":row.path,
			"status":row.fstatus
		}
	    ajaxService.ajaxGet(json,'2003',function(result){
	        if(result){
	            //returnDate(result,'status'+index);
	        }
	    });
	}
}]);