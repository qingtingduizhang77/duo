var isEditing=0;//多少单元格处于编辑中
var editData=[];//编辑列设置信息
var allowEidt=false;//编辑中状态
var auditColumn="auditing";//复核字段


  //字段编辑
function editTableCell(field, value, row, $element) {
	if(!allowEidt) return;//不是编辑状态不能修改
	if(auditColumn!=null&&auditColumn!=""&&row[auditColumn]!=null&&row[auditColumn]!=""&&row[auditColumn]!="0"){//记录已锁定不能修改
		return;
	}
    if ($element.html().indexOf("<span")<0
		&&(unEscapeHTML($element.html())!=unEscapeHTML($element.text()))
		&&((typeof(value) != "undefined" && unEscapeHTML(value) != unEscapeHTML($element.html()))||(typeof(value) == "undefined"&&$element.html().length>5))){
		var temp=unEscapeHTML($element.html());
		if(temp.indexOf("disabled")>0){ //formatter 设置的控件  复选框  图片  ￥样式等
		} else return;//编辑框未消失，无需再创建
	}
	if(typeof(value) == "undefined") value="";
	// if ((row[keyIDColumn]==null||row[keyIDColumn]=="")&&!editData[field]["addedit"]) {//不可编辑
	// 	return;
	// }
	if (editData[field]&&!editData[field]["edit"]) {//不可编辑
		return;
	}
	var hasDuty=false;
	if($element.css("border-bottom").indexOf("2px")>-1) {
		$element.css("border-bottom","");
		hasDuty=true;
	}


	var controlType="text";//控件类型
	if (editData[field]) controlType=editData[field]["type"];
	if (controlType=="text"||controlType=="number") {
		var isRequired=editData[field]["required"];//是否必填
		if(isRequired) isEditing++;
		// field 为文本框
		var input = $("<input type='text'  class='form-control'  title='"+(isRequired?"必填字段！":"")+"'/>");
		if(controlType=="number") input=$("<input type='number'  class='form-control' title='"+(isRequired?"必填字段！":"")+"只能输入数字！'/>")
		$element.html(input);
		input.focus().val(unEscapeHTML(value));
		input.blur(function () {
			let index = $element.parent().data('index');
			if(editData[field]["blur_js"]!=null&&editData[field]["blur_js"]!=""){//执行blur Js方法
				editIndex=index;
				// alert(editData[field]["blur_js"]);
				eval(editData[field]["blur_js"]);
			}
			let tdValue = input.val();
			if (isRequired&&tdValue == "") {
				input.css("border", "1px dashed #FF0000");
				return;
			}
			if (tdValue != unEscapeHTML(value)) {//数据修改过
				//更新最新值到row
				var rows = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true});
				rows[index][field] = tdValue;
				rows[index]["isUpdateData"] = "1";//标记记录被修改
				//alert(JSON.stringify(rows[index]));
				hasDuty=true;
			}
			if(isRequired)  isEditing--;
			if(editData[field]["formatter"]==null||typeof(editData[field]["formatter"])=="undefined"||editData[field]["formatter"]=="") $element.html("" + tdValue);
			else $element.html(eval(editData[field]["formatter"]+"(tdValue,row,index)"));
			if(hasDuty)  $element.css("border-bottom", "2px dashed #F00");
		})
	}else if (controlType=="editcombobox") {
        // field 为选择框
        var isRequired=editData[field]["required"];//是否必填
        var control_name=editData[field]["control_name"];
        if(control_name==""){
            alert("ERROR!No control_name!");
            return;
        }
        if(isRequired) isEditing++;
        var options='';
        eval(" var optionJson="+control_name+";");
        for(var key in optionJson){//遍历设置options
            options+='<option value="'+key+'" '+(key==value?"selected":"")+'>'+optionJson[key]+'</option> ';
        }
        var input = $(" <select class='form-control' >"+options+" </select>");//multiple
        $element.html(input);
        //input.focus().val(value);
        var blur=function () {
            let index = $element.parent().data('index');
            let tdValue = input.val();
            if (isRequired&&tdValue == "") {
                input.css("border", "1px dashed #FF0000");
                return;
            }
            if (tdValue != value) {//数据修改过
                //更新最新值到row
                var rows = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true});
                rows[index][field] = tdValue;
                rows[index]["isUpdateData"] = "1";//标记记录被修改
                //alert(JSON.stringify(rows[index]));
                hasDuty=true;
            }
            if(isRequired)  isEditing--;
            if(editData[field]["formatter"]==null||typeof(editData[field]["formatter"])=="undefined"||editData[field]["formatter"]=="") $element.html("" + tdValue);
            else $element.html(eval(editData[field]["formatter"]+"(tdValue,row,index)"));
            if(hasDuty)  $element.css("border-bottom", "2px dashed #F00");
        };
        input.editableSelect({
            effects: 'slide',
            //可选参数default、fade
            filter: false // 不过滤，否则选中后其它选项消失
        });
        input.on('select.editable-select',blur);
        input.on('input',blur);
    }else if (controlType=="combobox") {
		// field 为选择框
		var isRequired=editData[field]["required"];//是否必填
		var control_name=editData[field]["control_name"];
		if(control_name==""){
			alert("ERROR!No control_name!");
			return;
		}
		if(isRequired) isEditing++;
		var options='';
	   eval(" var optionJson="+control_name+";");
		for(var key in optionJson){//遍历设置options
			options+='<option value="'+key+'" '+(key==value?"selected":"")+'>'+optionJson[key]+'</option> ';
		}
		var input = $(" <select class='form-control' >"+options+" </select>");//multiple
		$element.html(input);
		//input.focus().val(value);
		var blur=function () {
			let index = $element.parent().data('index');
			let tdValue = input.val();
			if (isRequired&&tdValue == "") {
				input.css("border", "1px dashed #FF0000");
				return;
			}
			if (tdValue != value) {//数据修改过
				//更新最新值到row
				var rows = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true});
				rows[index][field] = tdValue;
				rows[index]["isUpdateData"] = "1";//标记记录被修改
				//alert(JSON.stringify(rows[index]));
				hasDuty=true;
			}
			if(isRequired)  isEditing--;
			if(editData[field]["formatter"]==null||typeof(editData[field]["formatter"])=="undefined"||editData[field]["formatter"]=="") $element.html("" + tdValue);
			else $element.html(eval(editData[field]["formatter"]+"(tdValue,row,index)"));
			if(hasDuty)  $element.css("border-bottom", "2px dashed #F00");
		};
		input.change(blur);
		input.blur(blur);
	}else if (controlType=="comboboxmult") {
		return;//暂未实现
        // field 为选择框
        var isRequired=editData[field]["required"];//是否必填
        var control_name=editData[field]["control_name"];
        if(control_name==""){
            alert("ERROR!No control_name!");
            return;
        }
        if(isRequired) isEditing++;
        var options='';
        eval(" var optionJson="+control_name+";");

        for(var key in optionJson){//遍历设置options
            options+='<option value="'+key+'" '+((value+";").indexOf(key+";")?"selected":"")+'>'+optionJson[key]+'</option> ';
        }
        var input = $(" <select class='form-control select2' multiple >"+options+" </select>");//multiple
        $element.html(input);
        //input.focus().val(value);
        var blur=function () {
            let index = $element.parent().data('index');
            let tdValue = input.val();
            if (isRequired&&tdValue == "") {
                input.css("border", "1px dashed #FF0000");
                return;
            }
            if (tdValue != value) {//数据修改过
                //更新最新值到row
                var rows = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true});
                rows[index][field] = tdValue;
                rows[index]["isUpdateData"] = "1";//标记记录被修改
                //alert(JSON.stringify(rows[index]));
                hasDuty=true;
            }
            if(isRequired)  isEditing--;
            if(editData[field]["formatter"]==null||typeof(editData[field]["formatter"])=="undefined"||editData[field]["formatter"]=="") $element.html("" + tdValue);
            else $element.html(eval(editData[field]["formatter"]+"(tdValue,row,index)"));
            if(hasDuty)  $element.css("border-bottom", "2px dashed #F00");
        };
        input.change(blur);
        input.blur(blur);
    }else if (controlType=="selectwindow") {
		// field 为选择窗口
		var isRequired=editData[field]["required"];//是否必填
		if(isRequired) isEditing++;
        var index = $element.parent().data('index');
		//
		var input = $("<input type='text'  class='form-control' readonly title='"+(isRequired?"必填字段！":"")+"'  style='width:75%;float:left; '/>");
		var selbtn=$("<button type='button'  class='form-control' style='width:15%;float:left; '>...</button>");
		$element.html(input).append(selbtn);
		input.focus().val(value);
		input.blur(function () {
			let tdValue = input.val();
			if (isRequired&&tdValue == "") {
				input.css("border", "1px dashed #FF0000");
				return;
			}
			if (tdValue != value) {//数据修改过
				//更新最新值到row
				var rows = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true});
				rows[index][field] = tdValue;
				rows[index]["isUpdateData"] = "1";//标记记录被修改
				//alert(JSON.stringify(rows[index]));
				hasDuty=true;
			}
			if(isRequired)  isEditing--;
			if(editData[field]["formatter"]==null||typeof(editData[field]["formatter"])=="undefined"||editData[field]["formatter"]=="") $element.html("" + tdValue);
			else $element.html(eval(editData[field]["formatter"]+"(tdValue,row,index)"));
			if(hasDuty)  $element.css("border-bottom", "2px dashed #F00");
		})
		var openWindow=function () {
            editIndex=index;
            var control_name=editData[field]["control_name"];
             sourceCols=editData[field]["source_cols"];
             targetCols=editData[field]["target_cols"];
            if(control_name==null||control_name==""||sourceCols==null||sourceCols==""||targetCols==null||targetCols==""){
            	alert("未定义选择窗口控件值或字段对应关系，ERROR！");
				return;
			}
			try{
				var select=eval(control_name);
				var vwhereSQL=editData[field]["control_where"];
				var url = CONTEXT_PATH+"/iframe/select-window?url="+select["layout_url"]+"?funId="+select["fun_id"]+(vwhereSQL==null||vwhereSQL==""||vwhereSQL=="null"?"":"&orgSQL="+unEscapeHTML(vwhereSQL));
				//alert(encodeURI(url));
				$("#selectIframe").attr("src", encodeURI(url));
				//居中
				$('#selectWindowModal').on('show.bs.modal', function (e) {
					// 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
					$(this).css('display', 'block');
					var modalHeight=$(window).height() / 2 - $('#selectWindowModal .modal-dialog').height() / 2;
					var modalWidth=$(window).width() / 2 - $('#selectWindowModal .modal-dialog').width() / 2-100;
					$(this).find('.modal-dialog').css({
						'margin-top': modalHeight,
						'margin-left': modalWidth
					});
				});
				$('#selectWindowModal').modal({ show: true, backdrop: 'static' });
            }catch(e){}
		}
		input.click(openWindow);
		$($element,"button").click(openWindow);
	}else if (controlType=="checkbox") {
		if(value=="-") return value;
		// field 为复选框
		var isRequired=editData[field]["required"];//是否必填
		if(isRequired) isEditing++;
		var checked=value=="1"||value=="true"||value=="on" ?"checked":"";
		//
		var input = $("<input type='checkbox' "+checked+"  />");
		$element.html(input);
		//input.focus().val(value);
		var blur=function () {
			let index = $element.parent().data('index');
			let tdValue = input.is(':checked');
			if(tdValue||tdValue=="true") tdValue="1";
			else tdValue="0";
			if (isRequired&&tdValue == "") {
				input.css("border", "1px dashed #FF0000");
				return;
			}
			if (tdValue != value) {//数据修改过
				//更新最新值到row
				var rows = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true});
				rows[index][field] = tdValue;
				rows[index]["isUpdateData"] = "1";//标记记录被修改
				//alert(JSON.stringify(rows[index]));
				hasDuty=true;
			}
			if(isRequired)  isEditing--;
			if(editData[field]["formatter"]==null||typeof(editData[field]["formatter"])=="undefined"||editData[field]["formatter"]=="") $element.html("" + tdValue);
			else $element.html(eval(editData[field]["formatter"]+"(tdValue,row,index)"));
			if(hasDuty)  $element.css("border-bottom", "2px dashed #F00");
		};
		input.blur(blur);
	}else if (controlType=="date"||controlType=="datetime"||controlType=="time"||controlType=="timeminute"||controlType=="year"||controlType=="yearmonth") {
		// field 为日期框
		var isRequired=editData[field]["required"];//是否必填
		if(isRequired) isEditing++;
		//
		var input = $("<input type='text'  class='form-control'' title='"+(isRequired?"必填字段！":"")+"'/>");
//            input.prop("readonly", true).datetimepicker({
//                format: 'yyyy-mm-dd hh:ii:ss',
//                language: 'zh-CN',
//                pickDate: true,
//                pickTime: false,
//                minView: "day",//设置选择时 只显示到月份 ----day， year --不设置默认带时间 秒   日历的显示时间
//                startView: 2,
//                pickerPosition: "bottom-left",
//                autoclose: true
//            });
		//My97Datepick
		var dateFmt= 'yyyy-MM-dd HH:mm:ss';
		if(controlType=="date") dateFmt="yyyy-MM-dd";
		else if(controlType=="time") dateFmt="HH:mm:ss";
		else if(controlType=="timeminute") dateFmt="HH:mm";
		else if(controlType=="year") dateFmt="yyyy";
		else if(controlType=="yearmonth") dateFmt="yyyy-MM";
		input.bind('focus', function () { WdatePicker({ dateFmt:dateFmt, autoPickDate: true ,minDate:'',maxDate:'',onpicked:function(){blur();},onpicking:function(dp){blur();},isShowClear:false,readOnly:true
		   // ,onclearing: function(){if(!confirm('日期框的值为:'+this.value+', 确实要清空吗?')) return false;else input.val("");}
				}); });
		$element.html(input);
		input.focus().val(value);
		var blur=function(){
			let index = $element.parent().data('index');
			let tdValue = input.val();
			if (isRequired&&tdValue == "") {
				input.css("border", "1px dashed #FF0000");
				return;
			}
			if (tdValue != value) {//数据修改过
				//更新最新值到row
				var rows = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true});
				rows[index][field] = tdValue;
				rows[index]["isUpdateData"] = "1";//标记记录被修改
				//alert(JSON.stringify(rows[index]));
				hasDuty=true;
			}
			if(isRequired)  isEditing--;
			if(editData[field]["formatter"]==null||typeof(editData[field]["formatter"])=="undefined"||editData[field]["formatter"]=="") $element.html("" + tdValue);
			else $element.html(eval(editData[field]["formatter"]+"(tdValue,row,index)"));
			if(hasDuty)  $element.css("border-bottom", "2px dashed #F00");
			hasDuty=false;
		};
		input.on('changeDate',blur);
		input.blur(blur);
	}
}
