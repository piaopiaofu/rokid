/*
	标注
*/
app.controller('rokid_biaozhu',['$scope','ajaxService','$sce',function($scope,ajaxService,$sce){

	/*获取列表*/
	$scope.list={};
	$scope.listUser={};
	
	$scope.flag = false;
    $scope.animate = function(){
        $scope.flag = !$scope.flag;
        if($scope.flag){
        	$("#showFilter").attr("class","dot-top");
        }else{
        	$("#showFilter").attr("class","dot-bottom");
        }
    }
    
	/*获取列表*/
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
	    ajaxService.ajaxGet($scope.keyBak,'3001',function(result){
            if(result){
                returnDate(result,'list');
            }
        });
    }
	
	$scope.chgDate=function (id){
		$('#laydate_ok').focus();
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
	$scope.keyBak.asrLen = 2;
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
    
    /*ase编辑*/
    $scope.row = {};
    $scope.chgAsrType=function(row, type, value){
    	$scope.row = row;
    	if(type == "1"){
    		row.type2 = '';
    		row.asrEdit = '';
    		row.asrEditCnt = '';
    	}else if(type == "2"){
    		row.type1 = '';
    		if(row.asrEdit == null || row.asrEdit == ''){
    			row.asrEdit = row.asr;
    			row.asrEditCnt = '0';
    		}
    	}else{
    		row.type1 = '';
    		row.type2 = '';
    		row.type = '';
    		row.asrEdit = '';
    		row.asrEditCnt = '';
    	}
    	row.isClkAsrType=1;
    	if(value == -1) row.asrType = value;
    	$scope.save(row);
    }
    /*asr标注类型*/
    $scope.chgType=function(row, type, val){
    	$scope.row = row;
    	if(type == "1"){
    		row.asrType = 1;
    		row.type2 = '';
    		row.asrEdit = '';
    		row.asrEditCnt = '';
    	}else if(type == "2"){
    		row.asrType = 2;
    		row.type1 = '';
    		if(row.asrEdit == null || row.asrEdit == ''){
    			row.asrEdit = row.asr;
    			row.asrEditCnt = '';
    		}
    	}else{
    		row.asrEditCnt = '';
    	}
    	row.isClkAsrType=0;
    	row.type = val;
    	$scope.save(row);
    }

    /*保存*/
    $scope.save=function(arr){
    	//if(!checkInput(arr)) return;
	    ajaxService.ajaxGet(arr,'3002',function(result){
	        if(result){
	            returnDate(result,'save');
	        }
	    });
    }
    /*保存检查*/
    function checkInput(arr){
    	if(!arr.asrType){
    		errMsg('请选择ASR结果修改！');
    		return false;
    	}else if(arr.asrType == 1 || arr.asrType == 2){
    		if(!arr.type){
    			errMsg('请选择ASR标注！');
        		return false;
    		}
    		if(arr.asrType == 2 && !arr.asrEdit){
    			errMsg('请输入asr修改信息！');
        		return false;
    		}
    	}else if(arr.asrType == 3 || arr.asrType == 4){
    		
    	}else{
    		errMsg('ASR结果修改类型错误！');
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
	    if(opt=='list'){
            /*页码处理*/
            $scope.page.paginationLogic(data.pageCount,data.page);
            $scope.page.current=data.page;
            /*页码处理-end*/
            $scope.audioPath=data.path;
            $scope.list={};
            $scope.list=data.list;            
            /*添加控制播放*/
	    	setTimeout(function(){playController();}, 100);	
	    	infoMsg(data.info);
	    	
	    	if($scope.admin && data.page == 1){
	    		$scope.listUser = data.listUser;
	    	}
        }
	    if(opt=='save'){
	    	$scope.row.asrType = data.row.asrType;
	    	$scope.row.asrEdit = data.row.asrEdit;
	    	$scope.row.asrEditCnt = data.row.asrEditCnt;
	    	$scope.row.type = data.row.type;
	    	$scope.row.updName = data.row.updName;
	    	$scope.row.editAdmin = data.row.editAdmin;
	    	//infoMsg('保存成功');
        }
	}
	
	/*语音控制*/
	var oldAudio = null;
	function playController(){
		var audios = $("audio");
		var index = 0;
		$scope.list.forEach(function(row){
			// src
			audios[index].src = $scope.audioPath + row.path;
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
		setTimeout(function(){
			$("#"+row.id+"1").hide();
			$("#"+row.id+"2").hide();
			$("#"+row.id+"3").hide();
		},100);
		// 语音存在且不可播放，更新为405
		if(row.fstatus == null || row.fstatus == 200){
			$scope.updStatus(row);
		}
	}	
	$scope.errWav=function(row){
		setTimeout(function(){
			$("#"+row.id+"1").hide();
			$("#"+row.id+"2").hide();
			$("#"+row.id+"3").hide();
		},100);
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
	    ajaxService.ajaxGet(json,'3003',function(result){
	        if(result){
	            //returnDate(result,'status'+index);
	        }
	    });
	}
	
	$scope.chgAsr=function(asrEditText, row){
		var asrText = row.asr;
		var asrLen = asrText.length;
		var asrEditLen = asrEditText.length;
		var max = Math.max(asrLen , asrEditLen);		
		var chgCnt = 0;
		for(var x = 0; x < max; x++){
			if(asrEditText.charAt(x) != asrText.charAt(x)){
				chgCnt++;
			}
		}
		row.asrEditCnt = chgCnt;
		//$scope.save(row);
	};
	
	$scope.chgAsr2=function(asrEditText, row){
		//asrEditText = event.target.value;
		asrText = row.asr;
		out = ",";
		for(var x = 0; x < asrEditText.length; x++){
			var excit = 0;
			for(var y = 0; y < asrText.length; y++){
				if(asrEditText.charAt(x) == asrText.charAt(y) && out.indexOf("," + y + ",") < 0){
					out = out + y + ",";
					excit = 1;
					break;
				}
			}
			if(excit == 0) out = out + "-1,";
		}
		var chgCnt = 0;
		if(out.indexOf(","+(asrText.length - 1)+",")<0){
			out = out + (asrText.length-1) + ",";
			chgCnt = 1;
		}
		var outSp = out.substring(1).split(",");
		//outSp.sort();		
		//var chgCnt = 0;
		var start = null;
		var excitCnt = 0;
		for(var i=0; i <outSp.length; i++){
			if(start==null) start = - 1;
			if(outSp[i]=='') continue;
			if(outSp[i] == -1){
				chgCnt++;
				continue;
			}else{
				if(start == outSp[i]){
				
				}else if(start < outSp[i]){
					chgCnt = chgCnt + parseInt(outSp[i]) - start - 1;
				}else if(start > outSp[i]){
					chgCnt = chgCnt + start - parseInt(outSp[i]) - 1;
				}
				start = parseInt(outSp[i]);
				excitCnt++;
			}
		}
		start=null;
		row.asrEditCnt = chgCnt;
		//$scope.save(row);
	};
}]);