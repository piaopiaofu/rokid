/*
 统计
 */
var dateObj = "";
app.controller('rokid_tongji',['$scope','ajaxService','$sce',function($scope,ajaxService,$sce){
	/*获取列表*/
	$scope.list={};
    $scope.sce = $sce.trustAsResourceUrl;//解决src资源问题
    /*获取列表-end*/
	
    /*判断是否管理员*/
    if(JSON.parse(localStorage.userMsg).vo.type==1){
    	$scope.admin=true;
    }
    /*判断是否管理员-end*/
    
    $scope.key={};
    $scope.key2={};
    $scope.type=1;
    
    /*时间选择器*/
	var date = new Date();
	$scope.chgDate=function (id){
		var strFor = 'YYYY-MM-DD hh:mm:ss';
		if($scope.type == 4){
			strFor = 'YYYY-MM-DD'
		}
		laydate({istime: true, format: strFor,choose: function(dates){
			$scope.key.startDate=$("#startDate").val();
			$scope.key.endDate=$("#endDate").val();
			$scope.$apply();
		}});
	}
	
	$(document).ready(function(){
		$("#endDate").val(getFormatDate(date,"yyyy-MM-dd") + " 23:59:59");
		if($scope.type == 4){
		}else{
			//date.setMonth(date.getMonth() - 1);
			date.setDate(date.getDate() - 7);
		}
		$("#startDate").val(getFormatDate(date ,"yyyy-MM-dd") + " 00:00:00");		
		$scope.key.startDate=$("#startDate").val();
		$scope.key.endDate=$("#endDate").val();
		$("#dateType").val("m");
		$scope.key.dateType=$("#dateType").val();
		$scope.sce = $sce.trustAsResourceUrl;//解决src资源问题
		
	    if($scope.type == 1){
	    	$scope.voice();
		}else if($scope.type == 2){
			$scope.asrNlp();
		}else if($scope.type == 3){
			$scope.pageGet();
		}else if($scope.type == 4){
			$("#startDate").val(getFormatDate(date ,"yyyy-MM-dd"));		
			$scope.key.startDate=$("#startDate").val();
			$scope.active();
		}
	});
    /*时间选择器-end*/
	
	/*初始化*/
	$scope.init=function(act, arr){
//		console.log($scope.key);
//		var json=$scope.key;
//	    ajaxService.ajaxGet(json,'5001',function(result){
//	        if(result){
//	            returnDate(result,'init');
//	        }
//	    });
	}

	/*voice统计*/
	$scope.noMarked = 0;
	$scope.allData = 0;
	$scope.voice=function(){
		if(debug) console.log($scope.key);
		$scope.key2={};
		$scope.key2.dateType = $scope.key.dateType;
		$scope.key2.sn = $scope.key.sn;
		$scope.key2.isSn = $scope.key.isSn;
		var json=$scope.key;
	    ajaxService.ajaxGet(json,'5002',function(result){
	        if(result){
	            returnDate(result,'voice');
	        }
	    });
	}
	
	$scope.active=function(){
		if(debug) console.log($scope.key);
		$scope.key2={};
		$scope.key2.sn = $scope.key.sn;
		$scope.key2.isSn = $scope.key.isSn;
		var json=$scope.key;
	    ajaxService.ajaxGet(json,'5009',function(result){
	        if(result){
	            returnDate(result,'active');
	        }
	    });
	}
	
	/*asrNlp统计*/
	$scope.asrNlp=function(){
		if(debug) console.log($scope.key);
		if(!$scope.key.isAsr) $scope.key.isAsr='0';
		$scope.key2={};
		$scope.key2.dateType = $scope.key.dateType;
		$scope.key2.sn = $scope.key.sn;
		$scope.key2.domain = $scope.key.domain;
		$scope.key2.intent = $scope.key.intent;
		$scope.key2.slot = $scope.key.slot;
		$scope.key2.isSn = $scope.key.isSn;
		$scope.key2.isAsr = $scope.key.isAsr;
		$scope.key2.isDomain = $scope.key.isDomain;
		$scope.key2.isIntent = $scope.key.isIntent;
		$scope.key2.isSlot = $scope.key.isSlot;
		var json=$scope.key;
		$scope.list={};
		if($scope.key.isAsr == '0' || $scope.key.isAsr == '1'){
		    ajaxService.ajaxGet(json,'5003',function(result){
		        if(result){
		            returnDate(result,'asrNlp');
		        }
		    });
		}else{
			ajaxService.ajaxGet(json,'5008',function(result){
		        if(result){
		            returnDate(result,'asrNlpSex');
		        }
		    });
		}
	}
	
	/*chat统计*/
	$scope.chat=function(act, arr){
		if(debug) console.log($scope.key);
		$scope.key2={};
		$scope.key2.sn = $scope.key.sn;
		$scope.key2.isSn = $scope.key.isSn;
		$scope.key2.assignId = $scope.key.assignId;
		$scope.key2.isAssignId = $scope.key.isAssignId;
		var json=$scope.key;		
	    ajaxService.ajaxGet(json,'5004',function(result){
	        if(result){
	            returnDate(result,'chat');
	        }
	    });
	}
	
	/*获取列表*/
    $scope.pageGet=function(page){
        page=page?page:1;
        $scope.key.page=page;
        $scope.key2.sn = $scope.key.sn;
		$scope.key2.assignId = $scope.key.assignId;
        ajaxService.ajaxGet($scope.key,'5005',function(result){
            if(result){
                returnDate(result,'chatNew');
            }
        });
    }
    /*获取列表-end*/
    
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
	
	/*删除选择*/
    $scope.chatNewDel=function(){
        $scope.ids="";
        var ids = new Array();
        $('input[name="checked"]:checked').each(function(){ 
        	ids.push($(this).val());
        });
        if(ids.length==0){
        	layer.msg('请选择删除数据', {
                offset: 1,
                shift: 1
            });
        	return;
        }
        var json={
        	"ids" : ids.join(",")
        }
        if(debug) console.log(json);
        
        // 删除确认
        layer.confirm('确认删除吗？删除后不可恢复 ', 
			{
				btn: ['删除','取消'] //按钮
			},function(){
		        ajaxService.ajaxGet(json,'5006',function(result){
		            if(result){
		            	returnDate(result,'chatNewDel');
		            }
		        });
		        layer.closeAll('dialog');
			},function(){
			  
			}
		);
    }

	/*数据返回处理 */
	function returnDate(ret,opt){
		if(debug) console.log(ret);
		var data = JSON.parse(ret.data);
		if(opt=='chatNew'){
	    	/*页码处理*/
	    	$scope.page.paginationLogic(data.pageCount,data.page);
	    	$scope.page.current=data.page;
	    	$scope.list=data.list;
	    	/*页码处理-end*/
	    }else if(opt == "chatNewDel"){
	    	$scope.pageGet($scope.key.page);
	    }else if(opt == "voice"){
			$scope.noMarked = 0;
			$scope.allData = 0;
	    	$scope.list=data.list;
	    	$scope.list.forEach(function(e){  
	    		$scope.noMarked = $scope.noMarked + e.noMarked;
	    		$scope.allData = $scope.allData + e.allData;
	    	})  
	    }else if(opt == "asrNlpSex"){
	    	$scope.markedMale = 0;
	    	$scope.markedFemale = 0;
	    	$scope.markedYoung = 0;
	    	$scope.markedMaleOk = 0;
	    	$scope.markedFemaleOk = 0;
	    	$scope.markedYoungOk = 0;
	    	
	    	$scope.markedMaleIdonot = 0;
	    	$scope.markedFemaleIdonot = 0;
	    	$scope.markedYoungIdonot = 0;
	    	$scope.markedMaleOkIdonot = 0;
	    	$scope.markedFemaleOkIdonot = 0;
	    	$scope.markedYoungOkIdonot = 0;
	    	
	    	$scope.markedMaleErr = 0;
	    	$scope.markedFemaleErr = 0;
	    	$scope.markedYoungErr = 0;
	    	$scope.markedMaleErrIdonot = 0;
	    	$scope.markedFemaleErrIdonot = 0;
	    	$scope.markedYoungErrIdonot = 0;
	    	
	    	$scope.list=data.list;
	    	$scope.list.forEach(function(e){  
	    		$scope.markedMale = $scope.markedMale + e.maleok + e.maleerr;
	    		$scope.markedFemale = $scope.markedFemale + e.femaleok + e.femaleerr;
	    		$scope.markedYoung = $scope.markedYoung + e.youngok + e.youngerr;
	    		$scope.markedMaleOk = $scope.markedMaleOk + e.maleok;
	    		$scope.markedFemaleOk = $scope.markedFemaleOk + e.femaleok;
	    		$scope.markedYoungOk = $scope.markedYoungOk + e.youngok;
	    		
	    		$scope.markedMaleIdonot = $scope.markedMaleIdonot + e.maleokidnot + e.maleerridnot;
	    		$scope.markedFemaleIdonot = $scope.markedFemaleIdonot + e.femaleokidnot + e.femaleerridnot;
	    		$scope.markedYoungIdonot = $scope.markedYoungIdonot + e.youngokidnot + e.youngerridnot;
	    		$scope.markedMaleOkIdonot = $scope.markedMaleOkIdonot + e.maleokidnot;
	    		$scope.markedFemaleOkIdonot = $scope.markedFemaleOkIdonot + e.femaleokidnot;
	    		$scope.markedYoungOkIdonot = $scope.markedYoungOkIdonot + e.youngokidnot;
	    		
	    		$scope.markedMaleErr = $scope.markedMaleErr + e.maleerr;
		    	$scope.markedFemaleErr = $scope.markedFemaleErr + e.femaleerr;
		    	$scope.markedYoungErr = $scope.markedYoungErr + e.youngerr;
		    	$scope.markedMaleErrIdonot = $scope.markedMaleErrIdonot + e.maleerridnot;
		    	$scope.markedFemaleErrIdonot = $scope.markedFemaleErrIdonot + e.femaleerridnot;
		    	$scope.markedYoungErrIdonot = $scope.markedYoungErrIdonot + e.youngerridnot;
	    	})
	    }else if(opt == "asrNlp"){
	    	$scope.noMarked = 0;
			$scope.allData = 0;
			$scope.marked = 0;
			$scope.okAsr = 0;
			$scope.takkiAsr = 0;
			$scope.errorAsr = 0;
			//$scope.okChatIdont = 0;
			//$scope.takkiChatIdont = 0;
			//$scope.errorChatIdont = 0;
			//$scope.markChatIdont = 0;
			$scope.zhengtierr = 0;
			$scope.zhengticnt = 0;
			$scope.tongyongerr = 0;
			$scope.tongyongcnt = 0;
			$scope.zhuanxiangerr = 0;
			$scope.zhuanxiangcnt = 0;
	    	$scope.list=data.list;
	    	$scope.list.forEach(function(e){  
	    		$scope.noMarked = $scope.noMarked + e.noMarked;
	    		$scope.allData = $scope.allData + e.allData;
	    		$scope.marked = $scope.allData - $scope.noMarked;
	    		//$scope.okChatIdont = $scope.okChatIdont + e.okChatIdont;
	    		//$scope.takkiChatIdont = $scope.takkiChatIdont + e.takkiChatIdont;
	    		//$scope.errorChatIdont = $scope.errorChatIdont + e.errorChatIdont;
	    		//$scope.markChatIdont = $scope.markChatIdont + e.markChatIdont;
	    		$scope.zhengtierr = $scope.zhengtierr + e.asrEditCnt;
				$scope.zhengticnt = $scope.zhengticnt + e.asrLenCnt;
				$scope.tongyongerr = $scope.tongyongerr + e.tyEditCnt;
				$scope.tongyongcnt = $scope.tongyongcnt + e.tyLenCnt;
				$scope.zhuanxiangerr = $scope.zhuanxiangerr + e.asrEditCnt - e.tyEditCnt;
				$scope.zhuanxiangcnt = $scope.zhuanxiangcnt + e.asrLenCnt - e.tyLenCnt;
	    		$scope.okAsr = $scope.okAsr + e.okAsr;
	    		$scope.takkiAsr = $scope.takkiAsr + e.takki;
	    		$scope.errorAsr = $scope.errorAsr + e.error;
	    	})  
	    }else if(opt == "active"){
	    	$scope.list=data.list;
	    	$scope.list.forEach(function(e){  
	    		e.timestart = e.time + ":00:00";
	    		if(parseInt(e.time) + 1 < 10){
	    			e.timeend = "0" + (parseInt(e.time) + 1) + ":59:59";
	    		}else{
	    			e.timeend = (parseInt(e.time) + 1) + ":59:59";
	    		}
	    	})
	    }	    	
	}

	/*asrExp PDF出力*/
	$scope.asrExp=function(){
		if(debug) console.log($scope.key);
		$scope.key2={};
		$scope.key2.dateType = $scope.key.dateType;
		$scope.key2.sn = $scope.key.sn;
		$scope.key2.domain = $scope.key.domain;
		$scope.key2.intent = $scope.key.intent;
		$scope.key2.slot = $scope.key.slot;
		$scope.key2.isSn = $scope.key.isSn;
		$scope.key2.isAsr = $scope.key.isAsr;
		$scope.key2.isDomain = $scope.key.isDomain;
		$scope.key2.isIntent = $scope.key.isIntent;
		$scope.key2.isSlot = $scope.key.isSlot;
		var json=$scope.key;
		console.log(json);

		ajaxService.ajaxGetPdf(json,'5007',function(result){
			if(result){
				var anchor = angular.element('<a/>');
				var myBlob = new Blob([result.pdf], {type: 'application/pdf'}); // 使用Blob将PDF Stream 转换为file
				var blobURL = (window.URL || window.webkitURL).createObjectURL(myBlob);
				var anchor = document.createElement("a");
				anchor.download = result.fileName;
				anchor.href = blobURL;
				anchor.click();
			}
		});
	}
}]);

