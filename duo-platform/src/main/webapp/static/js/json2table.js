String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}
var nav4 = window.Event ? true : false; //初始化变量
function modifiers() { //函数:判断键盘Ctrl按键
    if (nav4) { //对于Netscape浏览器
        //判断是否按下Ctrl按键
        if ((typeofe.ctrlKey != 'undefined') ? window.Event.ctrlKey : window.Event.modifiers & window.Event.CONTROL_MASK > 0) {
            return true;
        } else {
            return false;
        }
    } else {
        //对于IE浏览器，判断是否按下Ctrl按键
        if (window.event.ctrlKey) {
            return true;
        } else {
            return false;
        }
    }
    return false;
}


var _data = {};

function extend(destination, source) {
    for (var property in source)
        destination[property] = source[property];
    return destination;
}

//table 转 json
function table2Json(table) {
    var data = [];
    // go through cells
    table.find("tr[id^='tr_data_']").each(function (i) {

        var trRow = $(this);
        var rowData = {};

        trRow.find("td").each(function (j) {

            rowData[this.id] = this.innerHTML;

        });

        data.push(rowData);
    });

    return data;
}

//table tr 转 json
function table2Json(table, n) {
    var data = {};
    // go through cells
    table.find("tr[id^='tr_data_']").each(function (i) {
        if (i != n) return true;
        var trRow = $(this);
        var rowData = {};

        trRow.find("td").each(function (j) {

            rowData[this.id] = this.innerHTML;

        });

        data = rowData;
    });

    return data;
}

//json 赋值 table
function jsonFillForm(row, formid) {//data为返回的json数据

}

function createNo(n) {
    var str_C = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    var str_N = "0123456789";
    var result = "";
    var a = Math.round(Math.random() * 26)
    if (a == 0) a = 1;
    result = result + str_C.substring(a - 1, a);
    for (var i = 1; i <= n; i++) {
        var num = Math.round(Math.random() * 10)
        if (num == 0) num = 1;
        result = result + str_N.substring(num - 1, num);
    }
    return result;
}

/*
 function mergeTD(tableid,tdid,value){//合并列
 if(value==null) value="";
     var num=1;
     var newval;
     var trRows= $(table).find("tr[id^='tr_data_']");
      for(var i=0;i<trRows.length;i++){
	      var trRow = trRows[i]; 
	      var td=trRow.find(tdid);
	      if(td.innerHTML==value) {
		 num++;
		 if(i<trRows.length-1) trRows[i].remove();//如果这个一样了，则将td去掉
		 else trRows[i].attr({ rowspan: num}); 
	        }else{
		  if(num>1)  trRows[i].attr({ rowspan: num}); 
		   num=1;
		}
	 }
 }
*/

//json 赋值 table
function json2Table(datas, tableid, paramename) {//data为返回的json数据
    // var th=$("#"+tableid).find("tr th");

    if (typeof (paramename) == "undefined") _data = datas;
    else eval(paramename + "=datas;");

    eval('var table=$("' + tableid + '");');
    //alert(table.attr("id"));
    table.find("tr").each(function (i) {
        var trRow = $(this);
        if (typeof (trRow.attr("id")) == 'undefined') return true;
        if (trRow.attr("id").indexOf("tr_data_") < 0) return true;

        trRow.remove();
    });

    $(tableid).find("#tr_template").show();
    $.each(datas, function (i, row) {
        // alert(Obj2str(row));
        var tr = $(tableid).find("#tr_template").clone();

        tr.find("td").each(function (n) {
            //alert(th[n].innerHTML);
            var id = $(this).attr("id");
            if (id == "system_no") {
                //alert(i);
                tr.find("#" + id).html(i + 1);
                return true;
            }
            if (id == "ck") {
                //alert(i);
                tr.find("#" + id).html("<input type='checkbox'/>");
                return true;
            }
            var formatter = $(this).attr("formatter");
            var format = $(this).attr("format");
            var value = row[id];
            //eval("var value=row."+id+";");
            //if(i==0&&n==0)alert(value);
            if (formatter != null && formatter != "") {
                //if(i==0) alert(row.apply_number);
                eval("value=" + formatter + "($(this),value,row,i,n);");
                //if(i==0)alert(value);
            } else if (format != null && format != "") {
                //if(i==0) alert(row.apply_number);
                if (format == "DR-date")
                    eval("value=DR_date($(this),value,row);");
                else if (format == "DR-int")
                    eval("value=DR_int($(this),value,row);");
                else if (format == "DR-date")
                    eval("value=DR_string($(this),value,row);");
                else if (format == "DR-date")
                    eval("value=DR_money($(this),value,row);");
                //if(i==0)alert(value);
            }

            tr.find("#" + id).html(value);
            //tr.find("#"+id).attr("id",id+"_"+i);
        });

        tr.attr("id", "tr_data_" + i);
        tr.attr("class", "class_tr_" + (i % 2));
        // if(i==3) alert(tr.attr("class"));
        tr.appendTo(tableid);//添加到模板的容器中


    });
    $(tableid).find("#tr_template").hide();
}


//Ajax 执行后台
function AjaxUrlData(param, fromurl, callback, async) {
    //alert(param);
    //alert(ui);
    if (async == null) async = true;
    $.ajax({
        type: 'post',
        url: fromurl,
        data: $.param(param, true),
        cache: false,
        async: async,
        dataType: 'json',
        success: function (data) {
            if (typeof (callback) == "function") callback(data);
        },
        error: function () {
            $.messager.alert('系统提示', '服务异常，请重新登录!', 'error', function () {
                window.location.reload();
            });
        }
    });
}



function formatStr(str) {
    str = str.replace(/\r\n/g, "");
    return str;
}


function Obj2str(o) {
    if (o == undefined) {
        return "";
    }
    var r = [];
    if (typeof o == "string") return "\"" + o.replace(/([\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
    if (typeof o == "object") {
        if (!o.sort) {
            for (var i in o)
                r.push("\"" + i + "\":" + Obj2str(o[i]));
            if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
                r.push("toString:" + o.toString.toString());
            }
            r = "{" + r.join() + "}"
        } else {
            for (var i = 0; i < o.length; i++)
                r.push(Obj2str(o[i]))
            r = "[" + r.join() + "]";
        }
        return r;
    }
    return o.toString().replace(/\"\:/g, '":""');
}

/*
var mp = new HashMap();
mp.put("key","value");
mp.size();
mp.toString();
mp.values();
mp.entrySet()
....
*/
function HashMap() {
    var size = 0;
    var entry = new Object();

    this.put = function (key, value) {
        entry[key] = value;
        size++;
    };

    this.putAll = function (map) {
        if (typeof map == "object" && !map.sort) {
            for (var key in map) {
                this.put(key, map[key]);
            }
        } else {
            throw "输入类型不正确，必须是HashMap类型！";
        }
    };

    this.get = function (key) {
        return entry[key];
    };

    this.remove = function (key) {
        if (size == 0)
            return;
        delete entry[key];
        size--;
    };

    this.containsKey = function (key) {
        if (entry[key]) {
            return true;
        }
        return false;
    };

    this.containsValue = function (value) {
        for (var key in entry) {
            if (entry[key] == value) {
                return true;
            }
        }
        return false;
    };

    this.clear = function () {
        entry = new Object();
        size = 0;
    };

    this.isEmpty = function () {
        return size == 0;
    };

    this.size = function () {
        return size;
    };

    this.keySet = function () {
        var keys = new Array();
        for (var key in entry) {
            keys.push(key);
        }
        return keys;
    };

    this.entrySet = function () {
        var entrys = new Array();
        for (var key in entry) {
            var et = new Object();
            et[key] = entry[key];
            entrys.push(et);
        }
        return entrys;
    };

    this.values = function () {
        var values = new Array();
        for (var key in entry) {
            values.push(entry[key]);
        }
        return values;
    };

    this.each = function (cb) {
        for (var key in entry) {
            cb.call(this, key, entry[key]);
        }
    };

    this.toString = function () {
        return obj2str(entry);
    };

    function obj2str2(o) {
        var r = [];
        if (typeof o == "string")
            return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
        if (typeof o == "object") {
            for (var i in o)
                r.push("\"" + i + "\":" + obj2str2(o[i]));
            if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
                r.push("toString:" + o.toString.toString());
            }
            r = "{" + r.join() + "}";
            return r;
        }
        return o.toString();
    }
}