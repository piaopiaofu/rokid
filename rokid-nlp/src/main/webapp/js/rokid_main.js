/*
	语音标注
*/
app.controller('rokid_nlp',['$scope','ajaxService','$sce',function($scope,ajaxService,$sce){

	/*获取列表*/
	$scope.list={};

	$scope.pageGet=function(page){
		page=page?page:1;
		$scope.keyBak.page = page;
		angular.copy($scope.keyBak, $scope.key);
		$scope.searchKey = {};
		angular.copy($scope.keyBak, $scope.searchKey);
		$scope.searchKey.intent = getDomainFromIntent($scope.searchKey.intent, 'i');
	    ajaxService.ajaxGet($scope.searchKey,'1001',function(result){
	        if(result){
	        	if($scope.keyBak.intent != undefined){
	        		var obj = $('select[name="selectObj"]')[1];
	        		$(obj).val($scope.keyBak.intent).select2();
	        	}
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
		if($scope.key.type==4){
			$scope.keyBak.page = 1;
			angular.copy($scope.key, $scope.keyBak);
		    ajaxService.ajaxGet($scope.keyBak,'2001',function(result){
		        if(result){
		            returnDate(result,'count');
		        }
		    });
			return;
		}
		$scope.key.start = $('#start').val();
		$scope.key.end = $('#end').val();
		angular.copy($scope.key, $scope.keyBak);
		$scope.pageGet(1);
	}
	
	$scope.key.type = null;
	$scope.keyEnter=function(event){
		if(event.keyCode == "13"){  
			$scope.search();
		}  
	}  

	//$scope.pageGet();
    $scope.sce = $sce.trustAsResourceUrl;//解决src资源问题
    /*获取列表-end*/

	/*用户状态改变*/
    $scope.row = {};
    
	$scope.update=function(row, act){
		$scope.row = row;
		$scope.row.act = act;
		if(act=="save"){
			if(row.newDomain == null || row.newDomain == ""){
				infoMsg("请选择Domain");
				return false;
			}
		}
		$scope.updRow = {};
		angular.copy($scope.row, $scope.updRow);
		$scope.updRow.newIntent = getDomainFromIntent($scope.updRow.newIntent, 'i');
	    ajaxService.ajaxGet($scope.updRow,'1002',function(result){
	        if(result){
	            returnDate(result, act);
	        }
	    });
	}
	$scope.syncDomain=function(){
	    ajaxService.ajaxGet({},'1004',function(result){
	        if(result){
	            returnDate(result, "syncDomain");
	        }
	    });
	}
	$scope.rowList = [];
	$scope.updates=function(){
		$scope.rowList = [];
		var json = [];
		$scope.list.forEach(function(row){
			if($scope.key.type==1){
				if(row.toBz || row.toOk){
					var action = -99;
					if(row.toBz) action = 0;
					if(row.toOk) action = 2;
					if(action > -99){
						json.push({"id":row.id, "value": action});
						$scope.rowList.push(row);
					}
				}
			}else{
				if(row.toDel){
					json.push({"id":row.id, "value": -1});
					$scope.rowList.push(row);
				}
			}			
		});
		
		if(json.length < 1){
			infoMsg("请选择操作的数据");
			return;
		}
	    ajaxService.ajaxGet({list:json},'1003',function(result){
	        if(result){
	            returnDate(result, 'saves');
	        }
	    });
	}
	$scope.checkboxchg=function(row, target){
		if(target.id == 'bz' && target.checked){
			row.toOk = false;
		}
		if(target.id == 'ok' && target.checked){
			row.toBz = false;
		}
	}
    /*用户状态改变-end*/
	
	$scope.init=function(){
		ajaxService.ajaxGet({type:$scope.key.type},'1000',function(result){
	        if(result){
	            returnDate(result, 'init');
	        }
	    });
	}
	
	/*count*/
	$scope.domainshow = false;
	$scope.domain={
		data:{},
		show:function(){
			//angular.copy($scope.groupTempList, $scope.groupList);
			$scope.domain.list(null);
			$scope.groupTempList = [];
			for(var i = 0; i < $scope.groupList.length; i++){
				$scope.groupTempList.push($scope.groupList[i]);
			}
			$scope.domainshow = true;
		},
		list:function(date){
			var json={
				groupId: date==null?null:date.id
			};
		    ajaxService.ajaxGet(json,'1005',function(result){
		        if(result){
		            returnDate(result,'dlist');
		        }
		    });
		},
		save:function(){			
			var gJson = [];
			$.each($scope.groupTempList, function (i, n) {
				if(n.act != null && n.act != ""){
					gJson.push(n);
				}
			});
			var dJson = [];
			$.each($scope.domainGroupList, function (i, n) {
				if(n.act != null && n.act != ""){
					if(isNaN(n.groupId)){
						dJson.push({act:n.act,domain:n.domain,type:n.type,groupId:null,groupName:n.groupId});
					}else{
						dJson.push(n);
					}
				}
			});
			var json={
				gList:gJson,
				dList:dJson
			};
		    ajaxService.ajaxGet(json,'1006',function(result){
		        if(result){
		            returnDate(result,'dsave');
		        }
		    });
		},
		groupAdd:function(){
			$scope.groupTempList.push({act:"add",id:null,name:""});//unshift
		},
		groupDel:function(date){
			date.act = "del";
		},
		groupEdit:function(date){
			if((date.act == null || date.act == "" || date.act == "edit") && date.act != "add") date.act = "edit";
		},
		domainEdit:function(date,obj){
			if(date.act == null || date.act == "" || date.act == "edit") date.act = "edit";
		}
	};
	
	$scope.isadm=false;
	$(document).ready(function(){
		if($scope.key.type==4){
			var date = new Date();
			$("#end").val(getFormatDate(date,"yyyy-MM-dd"));
			$("#start").val(getFormatDate(new Date(date.getTime() - 7*24*3600*1000),"yyyy-MM-dd"));
			$scope.key.start=$("#start").val();
			$scope.key.end=$("#end").val();
		}
		
		$scope.init();
		setTimeout(function(){$scope.search();}, 100);
		var s = "（标注者）";
		if(localStorage.type==1){
			$scope.isadm=true;
			s = "（管理者）";
		}
		$("#user").html(localStorage.user+s);
	});
	
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
	$scope.groupList = {};
	$scope.groupTempList = {};
	$scope.domainList = {};
	$scope.domainGroupList = {};
	$scope.intentList = {};	
	$scope.path = "";
	function returnDate(ret,opt){
		if(debug) console.log(ret);
		var data = JSON.parse(ret.data);
		if(opt=='init'){
			var intentAry = [[]];
			//if($scope.key.type<4){
			$scope.groupList = data.groupList;
			$scope.groupTempList = $scope.groupList;
			$scope.domainList = data.domainList;
			$scope.domainList.push({type:null,groupId:null,domain:"com.rokid.system.chat"});
			$scope.domainList.push({type:null,groupId:null,domain:"com.rokid.system.idontknow"});
			$scope.domainGroupList = data.domainList;
			intentAry[""]= new Array();
			for(var i = 0; i < data.intentList.length; i++){
				if(!intentAry[data.intentList[i].domain]) intentAry[data.intentList[i].domain]= new Array();
				data.intentList[i].value = data.intentList[i].domain + "!@#$%" + data.intentList[i].intent;
				intentAry[data.intentList[i].domain].push(data.intentList[i]);
				intentAry[""].push(data.intentList[i]); 
			}		
			$scope.intentList = intentAry;
			$scope.key.slot = 0;
			$scope.key.intentList=intentAry[""];
			setSelectCss();
		}else if(opt=='list'){
	    	/*页码处理*/
	    	$scope.page.paginationLogic(data.pageCount,data.page);
	    	$scope.page.current=data.page;
	    	/*页码处理-end*/
	    	$scope.list={};
	    	$scope.list=data.list;
	    	for(var i = 0; i < $scope.list.length; i++){
	    		$scope.list[i].intentList = [];
	    		$scope.list[i].intentList = $scope.intentList[$scope.list[i].domain]; 
	    		if($scope.key.type == 0){
	    			$scope.list[i].newDomain = $scope.list[i].domain;
	    			$scope.list[i].newIntent = $scope.list[i].domain + "!@#$%" + $scope.list[i].intent;
	    			$scope.list[i].newSlot = $scope.list[i].slot;
	    		}
	    	}
	    	$scope.path = data.path;
	    	infoMsg(data.info);
	    	/*添加控制播放*/
	    	setTimeout(function(){playController();}, 10);
	    	/*添加SELECT样式*/
	    	setTimeout(function(){setSelectCss();}, 100);	
		}else if(opt=='count'){
	    	/*页码处理-end*/
	    	$scope.list={};
	    	$scope.list=data.list;
	    }else if(opt=='save' || opt=='ignore'){ //标注页码返回处理
	    	$scope.list.splice($scope.list.indexOf($scope.row), 1);
	    }else if(opt=='saves'){ //错误页面，正确页面操作成功处理
	    	for(var i = 0; i < $scope.rowList.length; i++){
	    		$scope.list.splice($scope.list.indexOf($scope.rowList[i]), 1);
	    	}
	    }else if(opt=='dlist'){
			$scope.domainGroupList = data.list;
			for(var i = 0; i < $scope.domainGroupList.length; i++){
				if($scope.domainGroupList[i].groupId==undefined) $scope.domainGroupList[i].groupId="";
			}
	    }else if(opt=='dsave'){
	    	$scope.init();
	    	/*添加SELECT样式*/
	    	setTimeout(function(){setSelectCss();}, 100);	    	
	    	infoMsg("分组保存成功");  
	    }else if(opt == "syncDomain"){
	    	//重新加载domain,intent数据
	    	$scope.init();
	    	/*添加SELECT样式*/
	    	setTimeout(function(){setSelectCss();}, 100);	    	
	    	infoMsg("domain/intent同步成功");
        }
	}
	
	/*语音控制*/
	var oldAudio = null;
	function playController(){
		var audios = $("audio");
		var index = 0;
		$scope.list.forEach(function(row){
			// src
			//row.path = $scope.path + row.path;
			audios[index].src = $scope.path + row.path;
			// error
			audios[index].addEventListener('error', function(e){
				//errWav(this, row);
			});
			// play
			audios[index].addEventListener('play', function(e){
				try{if(oldAudio != this)$(oldAudio)[0].pause();}catch(e){}
				oldAudio = this;
			});						
			index++;
		});
	}
	
	$scope.selectDoamin=function(row, type){
		var intentObj = null;
		var slotObj = null;
		if(type=='key'){
			row.intent = "";
			row.intentList = $scope.intentList[row.domain];
			intentObj = $('select[name="selectObj"]')[1];
		}else{
			row.newIntent = "";
			row.intentList = $scope.intentList[row.newDomain];
			intentObj = $('select[name="selectObjI"]')[$scope.list.indexOf(row)];
			row.newSlot = "";
		}
		$(intentObj).val("").select2();			
		return;
	}
	
	function getDomainFromIntent(intent, type){
		if(intent != null && intent != ""){
			var s = intent.split("!@#$%");
			if(type == 'd'){
				return s[0];
			}else if(type == 'i'){
				return s[1];
			}
		}else{
			return "";
		}
	}
	$scope.selectIntent=function(row, type){
//		var liAry = $(event.target).parent().find("li");
//		var selectIndex = -1;
//		var indexBegin = 0;
//		for(var i=0; i < liAry.length; i++){
//			if(liAry[i] == event.target){
//				selectIndex = i - indexBegin;
//				break;
//			}
//			if(liAry[i].innerText == ""){
//				indexBegin++;
//			}
//		}
//		row.newSlot = "";
//		if(selectIndex == -1){			
//			return;
//		}
		var domainObj = null;
		var slotObj = null;
		if(type=='key'){
			if(row.intent == null || row.intent == "") return;
			row.domain = getDomainFromIntent(row.intent, 'd');
			domainObj = $('select[name="selectObj"]')[0];
			if($(domainObj).val() != row.domain){
				$(domainObj).val(row.domain);	
				$(domainObj).select2();
			}
		}else{
			row.newSlot = "";
			if(row.newIntent == null || row.newIntent == "") return;
			row.newDomain = getDomainFromIntent(row.newIntent, 'd');
			domainObj = $('select[name="selectObjD"]')[$scope.list.indexOf(row)];
			if($(domainObj).val() != row.newDomain){
				$(domainObj).val(row.newDomain);
				$(domainObj).select2();
			}
		}
	}
		
	function setSelectCss(){
		//return;
		$('select[name="selectObj"]').each(function(){
		    $(this).select2();
		});
		$('select[name="selectObjD"]').each(function(){
		    $(this).select2();
		});
		$('select[name="selectObjI"]').each(function(){
		    $(this).select2();
		});
	}
	
	/*logout*/
	$scope.logout=function(act, arr){
		var json={};
	    ajaxService.ajaxGet(json,'0009',function(result){
	        if(result){
	        	window.top.location.href='login.html';
	        }
	    });
	}
}]);