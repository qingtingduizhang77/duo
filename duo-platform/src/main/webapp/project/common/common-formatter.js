/*
footerFormatter
*/
function totalFormatter(data) {
	return "∑" ;
}

/*
formatter
*/

function imgFormatter(value, row, index){
    if(value==null||value=="") return "";
    return "<img src='"+CONTEXT_PATH+"/file/download?file_id="+value+"' class='gridimg'/> ";
}

function downloadFormatter(value, row, index){
    if(value==null||value=="") return "";
    return  CONTEXT_PATH+"/file/download?file_id="+value;
}

var deptJson={};//{dept_id:"",data:{xxxxxx}}
var dataJson={};//{data_type:"",data:{data_id:"",data_value:"",data_name:""}}
//是否下拉列表用checkbox显示
function chekboxFormatter(value, row, index){
    if(value=="-") return value;
	var checked="";
	if(value=="1")checked="checked" ;
	return "<input type='checkbox' "+checked+" disabled/> ";
}
function checkboxFormatter(value, row, index){
    if(value=="-") return value;
    var checked="";
    if(value=="1")checked="checked" ;
    return "<input type='checkbox' "+checked+" disabled/> ";
}

//表设计器判断实际数据库是否存在字段
function dbHasFormatter(value, row, index){
	if(value=="10") return "<span style='color:green'>设计</span> | <span style='color:red;text-decoration:line-through;'>数据库</span>";
	if(value=="01") return "<span style='color:red;text-decoration:line-through;'>设计</span> | <span style='color:green'>数据库</span>";
	if(value=="11") return "<span style='color:green'>设计</span> | <span style='color:green'>数据库</span>";
	return value;
}

//是否有附件
function fileIcoFormatter(value, row, index){
    if(value==null||value=="") return "";
    var n=value.split(";");
    return "<i class='glyphicon glyphicon-paperclip' onclick='attachment(\"query\")' alt='该记录有"+n+"个附件'></i>";
}

//敏感字符加密显示 ，身份证、电话号码，显示前4后4
 function encryptionFormatter4(value, row, index){
    if(value==null||value.length<=8) return value;
    return value.substring(0,4)+"***"+value.substring(value.length-4);
 }

 //敏感字符加密显示 ，身份证、电话号码，显示前3后3
 function encryptionFormatter3(value, row, index){
     if(value==null||value.length<=6) return value;
     return value.substring(0,3)+"***"+value.substring(value.length-3);
 }

//敏感字符加密显示 ，身份证、电话号码，显示前3后3
function encryptionFormatter(value, row, index){
    if(value==null||value.length<=2) return value;
    return value.substring(0,1)+"***"+value.substring(value.length-1);
}

//基于权限的显示或加密
function encryptionRuleFormatter(value, row, index){
    //colRuleType  字段显示控制类型，company:只显示公司  dept:只显示所属部门数据，onedept:显示所属一级部门数据， twodept:显示所属一级部门数据， user:显示自己的数据
    if(funParames!=null&&funParames["encryRuleType"]!=null&&funParames["encryRuleCol"]!=null){
        let col=funParames["encryRuleCol"];
        alert(col);
        alert(row[col]);
        if(funParames["encryRuleType"]=="user"&&(CURRENTUSERID==null||row[col]!=CURRENTUSERID))  return "***";
        if(funParames["encryRuleType"]=="company"&&(CURRENTCOMPANYID==null||row[col]!=CURRENTCOMPANYID))  return "***";
        if(funParames["encryRuleType"]=="onedept"&&(CURRENTONEDEPTID==null||row[col]!=CURRENTONEDEPTID))  return "***";
        if(funParames["encryRuleType"]=="twodept"&&(CURRENTTWODEPTID==null||row[col]!=CURRENTTWODEPTID))  return "***";
        if(funParames["encryRuleType"]=="dept"&&(CURRENTDEPTID==null||row[col]!=CURRENTDEPTID))  return "***";
    }

  return value;
}


//显示图标
function icoFormatter(value, row, index){
    return "<i class='"+value+"'></i>";
}

//显示颜色
function colorFormatter(value, row, index){
    if(value == null||value=="") return value;
    return "<span style='color:"+value.replace("bold","")+";"+(value.indexOf("bold")>-1?"font-weight:bold":"")+"'>■"+value+"</span>";
}

//修改——转换日期格式(时间戳转换为datetime格式)
function dateFormatter(value, row, index) {
    if (value == null||value=="") return "";
    if( typeof(value) =="string") return value;
    var dateVal = value + "";
    var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
    var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    return date.getFullYear() + "-" + month + "-" + currentDate;
}


//修改——转换日期格式(时间戳转换为datetime格式)
function dateTimeFormatter(value, row, index) {
    if (value == null||value=="") return "";
    if( typeof(value) =="string") return value;
    var dateVal = value + "";
    var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
    var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
    var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
}



//修改——转换日期格式(时间戳转换为datetime格式)
function timeFormatter(value, row, index) {
    if (value == null||value=="") return "";
    if( typeof(value) =="string") return value;
    var dateVal = value + "";
    var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
    var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
    var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    return   hours + ":" + minutes + ":" + seconds;
}
//敏感字符屏蔽
function passwordFormatter(value, row, index){
    if(value==null||value.length<8)
     return "******";
    else return value.substring(0,3)+"****"+value.substring(value.length-3,value.length);
}


//Grid行内按钮，需要对事件定义页面类型为 gridbtn
function gridButtonFormatter(value, row, index) {
    // alert(eventGridBtn);
    if(eventGridBtn==null) return value;
    return   eventGridBtn.replaceAll("index",index);
}