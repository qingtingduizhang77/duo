//去掉字符两端的空白字符  
 String.prototype.Trim=function () {  
     return this.replace(/(^[\t\n\r]*)|([\t\n\r]*$)/g,'');  
 };  
 
//去掉字符左边的空白字符  
 String.prototype.LTrim=function () {  
     return this.replace(/^[\t\n\r]/g,'');  
 };  
 
//去掉字符右边的空白字符  
 String.prototype.RTrim=function () {  
     return this.replace(/[\t\n\r]*$/g,'');  
 }; 

 String.prototype.endWith = function(str){
	 if(str==null || str=="" || this.length == 0 ||str.length > this.length){	
       return false;
	 }
	 if(this.substring(this.length - str.length)){
		 return true;
	 }else{
		 return false;
	 }
	 return true;
};

 String.prototype.startWith = function(str){
  if(str == null || str== "" || this.length== 0 || str.length > this.length){
	 return false;
  } 
  if(this.substr(0,str.length) == str){
     return true;
  }else{
     return false;
   }       
  return true; 
 };

/*
javascript数字格式化
*/

function formatNumber(num, pattern,pfix) {
	pattern = pattern || "#,##0.##";
	pfix = pfix || "";
	var strarr = num ? num.toString().split('.') : [ '0' ];
	var fmtarr = pattern ? pattern.split('.') : [ '' ];
	var retstr = '';

	// 整数部分
	var str = strarr[0];
	var fmt = fmtarr[0];
	var i = str.length - 1;
	var comma = false;
	for ( var f = fmt.length - 1; f >= 0; f--) {
		switch (fmt.substr(f, 1)) {
		case '#':
			if (i >= 0)
				retstr = str.substr(i--, 1) + retstr;
			break;
		case '0':
			if (i >= 0)
				retstr = str.substr(i--, 1) + retstr;
			else
				retstr = '0' + retstr;
			break;
		case ',':
			comma = true;
			retstr = ',' + retstr;
			break;
		}
	}
	if (i >= 0) {
		if (comma) {
			var l = str.length;
			for (; i >= 0; i--) {
				retstr = str.substr(i, 1) + retstr;
				if (i > 0 && ((l - i) % 3) == 0)
					retstr = ',' + retstr;
			}
		} else
			retstr = str.substr(0, i + 1) + retstr;
	}

	retstr = retstr + '.';
	// 处理小数部分
	str = strarr.length > 1 ? strarr[1] : '';
	fmt = fmtarr.length > 1 ? fmtarr[1] : '';
	i = 0;
	for ( var f = 0; f < fmt.length; f++) {
		switch (fmt.substr(f, 1)) {
		case '#':
			if (i < str.length)
				retstr += str.substr(i++, 1);
			break;
		case '0':
			if (i < str.length)
				retstr += str.substr(i++, 1);
			else
				retstr += '0';
			break;
		}
	}
	return retstr.replace(/^,+/, '').replace(/\.$/, '')+pfix; 

};
/**
 *格式化数字
 */
Number.prototype.format = function(pattern) {
	var strarr = this.toString().split('.');
	var fmtarr = pattern ? pattern.split('.') : [''];
	var retstr = '';

	// 整数部分
	var str = strarr[0];
	var fmt = fmtarr[0];
	var i = str.length - 1;
	var comma = false;
	for (var f = fmt.length - 1; f >= 0; f--) {
		switch (fmt.substr(f, 1)) {
		case '#':
			if (i >= 0) retstr = str.substr(i--, 1) + retstr;
			break;
		case '0':
			if (i >= 0) retstr = str.substr(i--, 1) + retstr;
			else retstr = '0' + retstr;
			break;
		case ',':
			comma = true;
			retstr = ',' + retstr;
			break;
		}
	}
	if (i >= 0) {
		if (comma) {
			var l = str.length;
			for (; i >= 0; i--) {
				retstr = str.substr(i, 1) + retstr;
				if (i > 0 && ((l - i) % 3) == 0) retstr = ',' + retstr;
			}
		} else retstr = str.substr(0, i + 1) + retstr;
	}

	retstr = retstr + '.';
	// 处理小数部分
	str = strarr.length > 1 ? strarr[1] : '';
	fmt = fmtarr.length > 1 ? fmtarr[1] : '';
	i = 0;
	for (var f = 0; f < fmt.length; f++) {
		switch (fmt.substr(f, 1)) {
		case '#':
			if (i < str.length) retstr += str.substr(i++, 1);
			break;
		case '0':
			if (i < str.length) retstr += str.substr(i++, 1);
			else retstr += '0';
			break;
		}
	}
	return retstr.replace(/^-,+/, '').replace(/^,+/, '').replace(/\.$/, '');
};

/**
 *	格式成百分数
 */
Number.prototype.toPercent = function(pattern) {
	var num = this * 100;
	pattern = pattern || '#,###,###.0';
	return num.format(pattern) + "%";
};
/**
 *	格式货币
 */
Number.prototype.toMoney = function(pattern) {
	pattern = pattern || '#,###,###.00';
	return this.format(pattern);
};
/**
 *	格式成大写的数字
 */
Number.prototype.toRMB = function() {
	var number = Math.round(this * 100) / 100;
	number = number.toString(10).split(".");
	var a = number[0];
	var cn = "零壹贰叁肆伍陆柒捌玖";
	var cnNum = "";
	var len = a.length - 1;
	for (var i = 0; i <= len; i++) cnNum += cn.charAt(parseInt(a.charAt(i))) + [
		["圆", "万", "亿", "万"][Math.floor((len - i) / 4)], "拾", "佰", "仟"][(len - i) % 4];
	if (number.length == 2 && number[1] != "") {
		var a = number[1];
		for (var i = 0; i < a.length; i++) cnNum += cn.charAt(parseInt(a.charAt(i))) + ["角", "分"][i]
	}
	cnNum = cnNum.replace(/零佰|零拾|零仟|零角/g, "零");
	cnNum = cnNum.replace(/零{2,}/g, "零");
	cnNum = cnNum.replace(/零(?=圆|万|亿)/g, "");
	cnNum = cnNum.replace(/亿万/, "亿");
	cnNum = cnNum.replace(/^圆零?/, "");
	if (cnNum != "" && !/分$/.test(cnNum)) cnNum += "整";
	return cnNum;
}; 
/**
 * 将数值四舍五入(保留2位小数)后格式化成金额形式
 *
 * @param num 数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.45'
 * @type String
 */
function formatCurrency(num) {
	if(num==null||num=="") num=0;
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
    cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}
 
 
 
/**
 * 将数值四舍五入(保留1位小数)后格式化成金额形式
 *
 * @param num 数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.4'
 * @type String
 */
function formatCurrencyTenThou(num) {
	if(num==null||num=="") num=0;
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*10+0.50000000001);
    cents = num%10;
    num = Math.floor(num/10).toString();
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}
///格式化日期
function FormatDate(str){
	var arr = str.split("-");
	if(arr.length < 1 || arr.length>3)
	{
		return str; 
	}
	else
	{
		if(arr[2].length<2){
			arr[2] = "0"+arr[2];
			return arr[0]+arr[1]+arr[2];
		}else{
			return str;
		}
	}

}

function     addmulDate(dtstr,type,n){ 
	return DateStrAdd(dtstr,'d',n).Format("yyyy-mm-dd");
}


function   DateStrAdd(datestr,interval,number){
	var date= new Date(str2datetime(datestr));
	return DateAdd(date,interval,number);
}

function   DateAdd(date,interval,number) 
{
/*
  *   功能:实现VBScript的DateAdd功能.
  *   参数:interval,字符串表达式，表示要添加的时间间隔.
  *   参数:number,数值表达式，表示要添加的时间间隔的个数.
  *   参数:date,时间对象.
  *   返回:新的时间对象.
  *   var   now   =   new   Date();
  *   var   newDate   =   DateAdd( "d ",5,now);
  *---------------   DateAdd(interval,number,date)   -----------------
  */
  if(date==null) date=new Date(); 
        switch(interval)
			
        {
                case   "y"   :   {
                        date.setFullYear(date.getFullYear()+number);
                        return   date;
                        break;
                }
                case   "q"   :   {
                        date.setMonth(date.getMonth()+number*3);
                        return   date;
                        break;
                }
                case   "m"   :   {
                        date.setMonth(date.getMonth()+number);
                        return   date;
                        break;
                }
                case   "w"   :   {
                        date.setDate(date.getDate()+number*7);
                        return   date;
                        break;
                }
                case   "d"   :   {
                        date.setDate(date.getDate()+number);  
                        return   date;
                        break;
                }
                case   "h"   :   {
                        date.setHours(date.getHours()+number);
                        return   date;
                        break;
                }
                case   "m"   :   {
                        date.setMinutes(date.getMinutes()+number);
                        return   date;
                        break;
                }
                case   "s "   :   {
                        date.setSeconds(date.getSeconds()+number);
                        return   date;
                        break;
                }
                default   :   {
                        date.setDate(d.getDate()+number);
                        return   date;
                        break;
                }
        }
}

 
//---------------------------------------------------   
// 日期格式化   
// 格式 YYYY/yyyy/YY/yy 表示年份   
// MM/M 月份   
// W/w 星期   
// dd/DD/d/D 日期   
// hh/HH/h/H 时间   
// mm/m 分钟   
// ss/SS/s/S 秒   
//---------------------------------------------------   
Date.prototype.Format = function(formatStr)    
{    

    var str = formatStr;    
    var Week = ['日','一','二','三','四','五','六'];   
   
    str=str.replace(/yyyy|YYYY/,this.getFullYear());    
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));    
   
    str=str.replace(/MM|mm/,(this.getMonth()+1)>9?(this.getMonth()+1).toString():'0' + (this.getMonth()+1));    
    str=str.replace(/M/g,this.getMonth()+1);    
   
    str=str.replace(/w|W/g,Week[this.getDay()]);    
   
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());    
    str=str.replace(/d|D/g,this.getDate());    
   
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());    
    str=str.replace(/h|H/g,this.getHours());    
    str=str.replace(/mi/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
   
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());    
    str=str.replace(/s|S/g,this.getSeconds());    
   
    return str;    
}     
   function String2Date(str){ 

    if(str==null||str=="") return new Date();
    return new   Date(Date.parse(str.replace(/-/g,   "/")));  

   }
   
   function sysStringToDate(str){
    var sd=str.split("-");
    return new Date(sd[0],(parseInt(sd[1],10)-1),sd[2]);
   }
   
   
   function  initEndtimeValue(begintime_id,endtime_id){
      var begibTimeStr= document.getElementById(begintime_id).value;
      if(begibTimeStr!=""){
       var endTimeStr = document.getElementById(endtime_id).value;
       if(endTimeStr != ""){
          if(endTimeStr <= begibTimeStr){
             var bdate =  String2Date(begibTimeStr);
			 // alert(bdate);
             document.getElementById(endtime_id).value = DateAdd( "d",1,bdate).Format("yyyy-MM-dd");
          }
       }else{
          //var bdate =  String2Date(begibTimeStr);
          //document.getElementById(endtime_id).value = DateAdd( "d",1,bdate).Format("yyyy-MM-dd");
       }
      }
   }
//离店日期
 function  initEndtdate(begintime_id,endtime_id){ 
      var begibTimeStr=$("input=[name="+begintime_id+"]").val(); 
       var endTimeStr = $("input=[name="+endtime_id+"]").val();  
            var bdate =  String2Date(begibTimeStr);   
			//alert(DateAdd(bdate,"d",1).Format("yyyy-MM-dd"));
			return DateAdd(bdate,"d",1).Format("yyyy-MM-dd"); 
     
   }

   
 function str2datetime(sdtime){
        var str = sdtime;
	str=str.replace(/\-/g,"/");
        return str;
   }

function date_difference(starttime,endtime){
		//alert(starttime+"-------"+endtime);
       var stime = new Date(str2datetime(starttime));
       var etime = new Date(str2datetime(endtime)); 
       
       var sutime = (etime.getTime() - stime.getTime())/(1000*60);//分   
       return sutime;
}
//两个日期间隔天数
function date_diff(starttime,endtime){ 
	 //  alert(starttime+"--------"+endtime);
       var stime = new Date(str2datetime(starttime));
       var etime = new Date(str2datetime(endtime)); 
       //alert(stime+"-------"+etime);
       var sudays = (etime.getTime() - stime.getTime())/(1000*60)/1440;//天   
       return sudays;
}

 
//获得某月的最后一天  
 function getLastDay(year,month) {         
	  var new_year = year;    //取当前的年份          
	  var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）          
	  if(month>12) {         
	   new_month -=12;        //月份减          
	   new_year++;            //年份增          
	  }         
	  var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天          
	  return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期          
 } 
 