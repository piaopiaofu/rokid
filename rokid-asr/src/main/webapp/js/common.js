var connect = "";
var mydata = "";
var curWwwPath=window.document.location.href;
var t = curWwwPath.split("/");
var baseUrl = t[0] + "//" + t[2] ;

$(document).ready(function(){
	var domain = document.domain; 
	if(domain.indexOf(".") > 0 && domain.split(".").length > 2){
		domain = domain.substring(domain.indexOf(".")+1);	
	}
	document.domain=domain;
});

//原始方法
var postDate;
function postJson(connect, mydata, opt) {
	showLoad(true);

    if(mydata != null && mydata != ""){
    	if(mydata.substring(0,1) != "{") postDate = '{' + mydata + '}';
    	//mydata = JSON.stringify(mydata);
    }else{
    	postDate = "{}";
    }
	$.ajax({
		type : 'POST',
		contentType : 'application/json; charset=UTF-8',
		url : baseUrl + connect + "?" + new Date(),
		processData : false,
		dataType : 'text',
		data : postDate,
		success : function(ret) {
			showLoad(false);
			var json = jQuery.parseJSON(ret);
			if(json.code == -9){
				window.location.href= baseUrl + "/login.html";		
			//}else if(json.code == 0){
			//	alert(json.message, function(){});
			}else{
				try{
					returnDate(json, opt);	
				}catch(e){}
			}		    
		},
		error : function() {
			showLoad(false);
			alert("服务器调用失败", function(){});
		}
	});
}

function showLoad(flg){
	if(flg){
		$("body").append("<div id='over' class='over'></div>");
		$("body").append("<div id='layout' class='layout'><img src='../../images/loading.gif'/></div>");
	}else{
		setTimeout("closeLoad(false)", 100);
	}
}

function closeLoad(timeoutFlg){
	if(timeoutFlg) alert("超时！");
	$("#over").remove();
	$("#layout").remove();
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