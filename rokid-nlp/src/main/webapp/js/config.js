var app=angular.module('rokid_app',[]);
app.constant('api',{
	"0001":"manage/user/v1/login",
    "0002":"manage/user/v1/update",
    "0003":"manage/user/v1/list",
    "0004":"manage/user/v1/delete",
    "0009":"manage/user/v1/logout",
	"1000":"biaozhu/nlp/v1/init",
    "1001":"biaozhu/nlp/v1/list",
    "1002":"biaozhu/nlp/v1/update",
    "1003":"biaozhu/nlp/v1/updates",
    "1004":"biaozhu/nlp/v1/syncDomain",
    "1005":"biaozhu/nlp/v1/selDomainGroup",
    "1006":"biaozhu/nlp/v1/updDomainGroup",
    "2001":"biaozhu/nlp/v1/count",
    "3001":"manage/conf/v1/list",
    "3002":"manage/conf/v1/update",
});

/**
 * 是否调试模式
 */
var debug = true;

/*
 **  请求
 */
app.factory('ajaxService', ['$http','api',function($http,api) {

    function postJson(url, data, callback,dbug) {
        showLoad(true);
        var server = "/"+api[url];
        $http({
            method:'POST',
            url:server,
            data:data,
            cache:true,
            headers:{'Content-Type':'application/json'},
        }).success(function (data,status,headers,config){
            if(data.code==-9){
                window.top.location.href='login.html';
            }else if(data.message!="success"||data.code==0){
                errMsg(data.message);
            }else{
                callback(true, data);//JSON.parse()
            }
            showLoad(false);
        }).error(function(data,status,headers,config){
            showLoad(false);
            errMsg('网络通信失败!');
        });
    };

    return {
        ajaxGet:function(json,url,callback,dbug) {
            // json.item="";
            if(!dbug){
                dbug=1;
            }
            postJson(url, json, function(status, result) {
                if(status){
                    callback(result);
                }
            },dbug);
        }, ajaxGetPdf:function(json,url,callback,dbug) {
            if (!dbug) {
                dbug = 1;
            }
            postJsonReturnPdf(url, json, function (status, result) {
                if (status) {
                    callback(result);
                }
            }, dbug);
        }
    };
}]);

/*
**  获取参数
*/
app.factory('param',[function(){
    return{
        get:function(key){
            var search = document.location.search;
            var pattern = new RegExp("[?&]"+key+"\=([^&]+)", "g");
            var matcher = pattern.exec(search);
            var items = null;
            if(null != matcher){
                try{
                    items = decodeURIComponent(decodeURIComponent(matcher[1]));
                }catch(e){
                    try{
                        items = decodeURIComponent(matcher[1]);
                    }catch(e){
                        items = matcher[1];
                    }
                }
            }
            return items;
        }
    }
}]);

/*
**  导航
*/
app.directive('mainmenu',function(){
     return{
        restrict:'EA',
        scope:{
            data:'=mainmenuData'
        },
        template:'<ul class="layui-nav"><li class="layui-nav-item" ng-repeat="row in data"><a href="{{row.url}}" target="page"><small>{{row.cn}}</small><span>{{row.en}}</span></a><ul class="sub"><li ng-repeat="sub in row.sub"><a href="{{sub.url}}" target="page">{{sub.en}}</a></li></ul></li></ul>'
     };
});

$(document).ready(function(){
	$("body").append("<div id='over' class='over' style='display:none1'></div>");
	$("body").append("<div id='layout' class='layout' style='display:none1'><img src='./images/loading.gif'/></div>");
	setTimeout(	function(){$("#over").hide();$("#layout").hide();}, 100);
});

/*
**  用户信息
*/
app.factory('userMsg',function(){
    return{
        msg:function(){
            var u=localStorage.getItem('userMsg');
            u=JSON.parse(u);
            if(!u){
                return false;
            }else{
                return u;
            }
        }
    }
});

//修改原生alert样式
var _alert = window.alert;
MyAlert = function(text) {
    layer.msg(text, {
        offset: 0,
        shift: 1
    });
}; 
MyAlert.noConflict = function() { 
      window.alert = _alert; 
};
window.alert = window.MyAlert = MyAlert;

function showLoad(flg){
	if(flg){
		$("#over").show();
		$("#layout").show();
	}else{
		setTimeout("closeLoad(false)", 100);
	}
}

function closeLoad(timeoutFlg){
	if(timeoutFlg) errMsg("超时！");
	$("#over").fadeOut(200);
	$("#layout").fadeOut(200);
}

Date.prototype.format = function (format) { 
	var o = { 
	"M+": this.getMonth() + 1, 
	"d+": this.getDate(), 
	"h+": this.getHours(), 
	"m+": this.getMinutes(), 
	"s+": this.getSeconds(), 
	"q+": Math.floor((this.getMonth() + 3) / 3), 
	"S": this.getMilliseconds() 
	} 
	if (/(y+)/.test(format)) { 
	format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length)); 
	} 
	for (var k in o) { 
	if (new RegExp("(" + k + ")").test(format)) { 
	format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length)); 
	} 
	} 
	return format; 
} 

function getSmpFormatDate(date, isFull) { 
	var pattern = ""; 
	if (isFull == true || isFull == undefined) { 
	pattern = "yyyy-MM-dd hh:mm:ss"; 
	} else { 
	pattern = "yyyy-MM-dd"; 
	} 
	return getFormatDate(date, pattern); 
} 

function getFormatDate(date, pattern) { 
	if (date == undefined) { 
	date = new Date(); 
	} 
	if (pattern == undefined) { 
	pattern = "yyyy-MM-dd hh:mm:ss"; 
	} 
	return date.format(pattern); 
} 

function errMsg(msg){
	if(msg == null || msg == "") return;
	layer.alert(msg, {
		tips: [1, '#3595CC'],
		time: 2000
	});
}
function infoMsg(msg){
	if(msg == null || msg == "") return;
	layer.msg(msg, {
	    offset: 0,
	    shift: 1
	});
}