var keyIDColumn;
var funName ;
 var funId ;
var targetFunID;
var foreignKeyId;
var importforeignKeyId;
var defaultQueryId;
var ckObject;
//Ajax提交
function ajaxSumit(_url,_data,callback){
    _url=(_url.indexOf("http")>-1?"":CONTEXT_PATH )+encodeURI(_url);
	$.ajax({
		type: 'post',
		url:  _url+(_url.indexOf("?")>-1?"&":"?")+"t="+Math.random(),
		dataType: 'json',
		data: _data,
		success: function (data) {
			if (data.success) {
				if(data.msg!=null&&data.msg!="") $.myNotify.success(data.msg);
			} else {
				$.myNotify.warning(data.msg);
			}

			callback && callback(data);
		},
		error: function (data) {
			callback && callback(data);
		}
	});
}



//加密方法。没有过滤首尾空格，即没有trim.
//加密可以加密N次，对应解密N次就可以获取明文
function encodeBase64(mingwen,times){
    var code="";
    var num=1;
    if(typeof times=='undefined'||times==null||times==""){
        num=1;
    }else{
        var vt=times+"";
        num=parseInt(vt);
    }
    if(typeof mingwen=='undefined'||mingwen==null||mingwen==""){
    }else{
        $.base64.utf8encode = true;
        code=mingwen;
        for(var i=0;i<num;i++){
            code=$.base64.btoa(code);
        }
    }
    return code;
}


/**
 * 将escape过的HTML再反解回来
 * @param txt
 * @return {String|XML}
 * @private
 */
function unEscapeHTML(txt) {
    if(txt==null) return "";
    return  txt.toString().replace(/&amp;/g,'&').replace(/&gt;/g,'>')
        .replace(/&lt;/g,'<').replace(/&quot;/g,'"')
        .replace(/&#039;/g,"'") .replace(/&#39;/g,"'");
}

//用于执行指定uri
function runURI(that){
	var url="";
    if(that!=null){//指定了执行自定义类
        if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
            url=$(that).attr("bo-function");
        }
    }

    var  data={};
    var datas=$table.bootstrapTable('getSelections');
    if(datas!=null&&datas.length>0){
        data=datas[0];
	}

    ajaxSumit(url,data,callback);
}


function topTree(){
	if(treeTopId!=null){
		$("input[name='pId']").val(treeTopId);
	};
	$('#table').bootstrapTable('refresh', {silent: true});
}


//打开子功能
function opensubwindow(that) {
    var parames;
    if(that!=null){//指定了执行自定义类
        if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
            parames=$(that).attr("bo-function");
        }
    }
    var datas=$table.bootstrapTable('getSelections');
    if(datas==null||datas.length==0){
        alert("请选择并仅选择一条记录后再操作！");
        return;
    }
    var dataID=datas[0][keyIDColumn];

    var frameSrc = CONTEXT_PATH +"/iframe/subfun-window?foreignColumn="+keyIDColumn+"&foreignKeyId="+dataID+parames;
    $("#selectIframe").attr("src", frameSrc);
    $('#selectWindowModal .modal-title').text($(that).text()==""?"子功能窗口":$(that).text());
    //居中
    $('#selectWindowModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#selectWindowModal .modal-dialog').height() / 2;
        var modalWidth=$(window).width() / 2 - $('#selectWindowModal .modal-dialog').width() / 2-200;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    $('#selectWindowModal').modal({ show: true, backdrop: 'static' });
    //模态框隐藏的事件
    // var n=1;
    // $('#selectWindowModal').on('hidden.bs.modal', function (e) {
    //     if(n-->0)
    //         $('#table').bootstrapTable('refresh', {silent: true});
    // });
}
//Tab切换
var isnew=false;
var mainValide=true;//主功能记录是否可编辑
var ckInitSuccess=false;
//tabFun  FunTab
function funFormTab(that,divkey){
//	alert($(that).parent().hasClass('active'));
//	if($(that).parent().hasClass('active')) return;
    _autoFresh=true;
    if(divkey=="grid"){
        $("#gridFrameDiv").show();
        $("#formInfoDiv").hide();
        $("#formFrameDiv").hide();
        lastSelectRows=$('#table').bootstrapTable('getSelections');
        $('#table').bootstrapTable('refresh', {silent: true});
        if(lastSelectRows.length>0){
            var n=1;
            $('#table').on('load-success.bs.table',function(data){
                //设置默认值方法
                if(n-->0){
                    for(var i=0;i<lastSelectRows.length;i++){
                        $('#table').bootstrapTable("checkBy",{field: keyIDColumn, values:[lastSelectRows[i][keyIDColumn]]});
                    }
                }
            });
        }
    }else if(divkey=="form"){
        editIndex=-1;
        var datas=$('#table').bootstrapTable('getSelections');
        if(isnew==null||isnew==false){
            if(datas==null||datas.length==0){
                alert("请选择并仅选择一条记录后再操作！");
                return;
            }else{
                $('#infoform').form('clear');
                var row=datas[0];
                for(var datakey in editData){
                    if(editData[datakey].type=="date"){
                        row[datakey]=dateFormatter(row[datakey],null,null);
                    }else if(editData[datakey].type=="datetime"){
                         row[datakey]=dateTimeFormatter(row[datakey],null,null);
                    }
                }

                $('#infoform').fillForm(row);
                if(ckInitSuccess&&$("#infoform").find("#editor1").length>0){//处理CKeditor控件
                    var name=$("#infoform").find("#editor1").attr("name")+"";
                    CKEDITOR.instances.editor1.setData(row[name])  ;
                } ;
            }
            if(mainValide){
                $("#infoform").removeClass("disabledform");
            }else{
                $("#infoform").addClass("disabledform");
            }
        }else{
            $('#infoform').form('clear');
          if(ckInitSuccess&&$("#infoform").find("#editor1").length>0){//处理CKeditor控件
                var name=$("#infoform").find("#editor1").attr("name")+"";
                CKEDITOR.instances.editor1.setData("")  ;
            } ;
            isnew=false;
            var row={},treenode={};
            // row["isUpdateData"]="2";
            if($.fn.zTree.getZTreeObj("tree")!=null){//有树结构
                var treeNodes=$.fn.zTree.getZTreeObj("tree").getSelectedNodes();
                if(treeNodes!=null&&treeNodes.length>0){
                    treenode=  treeNodes[0].obj ;
                    //alert( row["module_id"]);
                }
            }
            for(var datakey in editData){
                if(editData[datakey].type=="date"){
                    row[datakey]=dateFormatter(row[datakey],null,null);
                }else if(editData[datakey].type=="datetime"){
                    row[datakey]=dateTimeFormatter(row[datakey],null,null);
                }
            }
            for(var key in editData){//取默认值
                if(editData[key].default!=null){
                    if(editData[key].default=="{tree}"){
                        row[key]=(treenode[key]!=null?treenode[key]:"");

                    }else{
                        row[key]=defaultChange(editData[key].default);
                    }
                }
            }

            if(foreignColumn!=null&&foreignColumn!=""){
                row[foreignColumn]=foreignKeyId;
            }
            $('#infoform').fillForm(row);

            $("#infoform").removeClass("disabledform");
        }
        $("#gridFrameDiv").hide();
        $("#formInfoDiv").height(document.body.clientHeight-125);
        $("#formInfoDiv").show();
        $("#formFrameDiv").hide();
    }else if(divkey=="chart") {
        var chartId="";
        if(funParames!=null&&funParames["chartId"]!=null) chartId=funParames["chartId"];
        viewChartLine(chartId);
        $("#gridFrameDiv").hide();
        $("#formFrameDiv").hide();
        $("#formInfoDiv").height(document.body.clientHeight-125);
        $("#formInfoDiv").show();
    }else if(divkey=="help") {
        $("#formFrameDiv").show();
        $("#gridFrameDiv").hide();
        $("#subIframe").height(document.body.clientHeight-80);
        $("#subIframe").attr("src","http://wwww.baidu.com");
        $("#formInfoDiv").hide();
    }else{
        var datas=$('#table').bootstrapTable('getSelections');
        if(datas==null||datas.length==0){
            alert("请选择并仅选择一条记录后再操作！");
            return;
        }
        $("#subIframe").height(document.body.clientHeight-80);

        if(divkey.indexOf("griddesigner")>0) $("#subIframe").attr("src", +divkey);//已无用
        else $("#subIframe").attr("src",CONTEXT_PATH + "/iframe/subfun-window?foreignColumn="+keyIDColumn+"&foreignKeyId="+datas[0][keyIDColumn]+"&height=660&url="+divkey+"&mainValide="+mainValide);
        $("#gridFrameDiv").hide();
        $("#formInfoDiv").hide();
        $("#formFrameDiv").show();
    }
    $("#myFormTab").find("li").removeClass("active");
    $(that).parent().addClass('active');
}


//add form
function addForm(){
    if($("#myFormTab")&&$("#myFormTab").find("li a").length>1&&$("#myFormTab").find('li').eq(1).find('a').text().indexOf("表单")>0){
		isnew=true;
		$("#myFormTab").find('li').eq(1).find('a').click();
    }else{//弹出form
        $('#infoform').form('clear');
        var row={},treenode={};
        // row["isUpdateData"]="2";
        if(window.parent.$.fn.zTree.getZTreeObj("tree")!=null){//有树结构
            var treeNodes=window.parent.$.fn.zTree.getZTreeObj("tree").getSelectedNodes();
            if(treeNodes!=null&&treeNodes.length>0){
                treenode=  treeNodes[0].obj ;
                //alert( row["module_id"]);
            }
        }
        for(var key in window.parent.editData){//取默认值
            if(window.parent.editData[key].default!=null){
                if(window.parent.editData[key].default=="{tree}"){
                    row[key]=(treenode[key]!=null?treenode[key]:"");

                }else{
                    row[key]=defaultChange(window.parent.editData[key].default);
                }
            }
        }

        if(window.parent.foreignColumn!=null&&window.parent.foreignColumn!=""){
            row[window.parent.foreignColumn]=window.parent.foreignKeyId;
        }
        $('#infoform').fillForm(row);
        $table.bootstrapTable('uncheckAll');
    }

}


//edkit form
function editForm(){
    isnew=false;
    $("#myFormTab").find("li a")[1].click();
}


//iFrame内关闭刷新
function iFrameClose(){
	try{
    	window.parent.$(".modal").modal("hide");
    	if(window.parent.importWindowDom!=null){
    	  //  alert(window.parent.location);
            window.parent.importWindowDom.$('#table').bootstrapTable('refresh', {silent: false});
            window.parent.importWindowDom=null;
        } else window.parent.$('#table').bootstrapTable('refresh', {silent: false});
    }catch(e){}
}
function eventFresh(){
    $('[data-action]').each(function (i) {
        var str = $(this).attr('data-action');
        try {
            var obj = eval('(' + str + ')');
        } catch (err) {
            error('[data-action]有误，请检查语法')
        }
        if (obj && obj.selection) {
            switch (obj.selection) {
                case 'single':
                    $(this).prop('disabled', !($table.bootstrapTable('getSelections').length === 1));
                    break;
                case 'multi':
                    $(this).prop('disabled', !$table.bootstrapTable('getSelections').length);
                    break;
            }
        }
    });
}

var _autoFresh=true;//用户某些页面不触发刷新
var lastSelectRows;
//回调刷新
function callback(data){
    if(!_autoFresh){
        _autoFresh=true;
        return;
    }
    lastSelectRows=$('#table').bootstrapTable('getSelections');
    $('#table').bootstrapTable('refresh', {silent: false});
    if(lastSelectRows.length>0){
        var n=1;
        $('#table').on('load-success.bs.table',function(data){
            //设置默认值方法
            if(n-->0){
                for(var i=0;i<lastSelectRows.length;i++){
                    $('#table').bootstrapTable("checkBy",{field: keyIDColumn, values:[lastSelectRows[i][keyIDColumn]]});
                }
            }

        });
    }
}

//回调刷新

//回调刷新
function callbackTree(data){
    if(!_autoFresh){
        _autoFresh=true;
        return;
    }

    refreshNode();
    lastSelectRows=$('#table').bootstrapTable('getSelections');
    $('#table').bootstrapTable('refresh', {silent: false});
    if(lastSelectRows.length>0){
        var n=1;
        $('#table').on('load-success.bs.table',function(data){
            //设置默认值方法
            if(n-->0){
                for(var i=0;i<lastSelectRows.length;i++){
                    $('#table').bootstrapTable("checkBy",{field: keyIDColumn, values:[lastSelectRows[i][keyIDColumn]]});
                }
            }
        });
    }

}

/**
 * 刷新当前选择节点的父节点
 */
function refreshNode() {
    var zTree = $.fn.zTree.getZTreeObj("tree"),
        type = "refresh",
        silent = false,
        nodes = zTree.getSelectedNodes();
    /*根据 zTree 的唯一标识 tId 快速获取节点 JSON 数据对象*/
    if(nodes[0]!=null&&nodes[0].parentTId!=null){
        var parentNode = zTree.getNodeByTId(nodes[0].parentTId);
        /*选中指定节点*/
        zTree.selectNode(parentNode);
        zTree.reAsyncChildNodes(parentNode, type, silent);
    }else{
        /*强行异步加载父节点的子节点。[setting.async.enable = true 时有效]*/
        zTree.reAsyncChildNodes(nodes[0], type, silent);
    }
}


//回调刷新
function savecallback(data){
	  if( $('#formModal')!=null) $('#formModal').modal('hide');
	  callback(data);
        try{
            $("#myFormTab").find("li a")[0].click();
        }catch(e){}
}

//form save
function save(that){
    var els=$("#infoform").find("input");
    $('#infoform').validator('validate');
    for(var i=0;i<els.length;i++) {
        var obj=els[i];
        var required = $(obj).attr("required");
        if(required && required == "required") {
            if(validateEl(obj,true)==false) {
                $(obj).focus();
                return ;
            }
        }

    } ;
    var disabledobjs=$("#infoform").find(":disabled");
    disabledobjs.attr("disabled",false);
    var data= $("#infoform").parseForm() ;

    disabledobjs.attr("disabled",true);
    $("#infoform").find(":checkbox").each(function(){//处理复选框取值转换为1 0
        var name=$(this).attr("name")+"";
        data[name] = $(this).prop('checked')?"1":"0"  ;
    });
    if(ckInitSuccess&&$("#infoform").find("#editor1").length>0){//处理CKeditor控件
        var name=$("#infoform").find("#editor1").attr("name")+"";
         // alert(ckObject.document.getBody().getHtml());
        data[name] =CKEDITOR.instances.editor1.getData()  ;
    };
    //alert(JSON.stringify(data));
    var url='/common/save';
    if(that!=null){//指定了执行自定义类
        if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
            url=$(that).attr("bo-function");
        }
    }
    var eventId="";
    if(that!=null){//获取eventId
        if($(that).attr("id")!=null&&$(that).attr("id")!=""){
            eventId=$(that).attr("id");
        }
    }
    _autoFresh=true;
    ajaxSumit(url+'?funId='+funId+"&eventId="+eventId+"&foreignKeyId="+foreignKeyId,data, savecallback);
}


//form save
function saveFile(that){
    var els=$("#infoform").find("input");
    for(var i=0;i<els.length;i++) {
        var obj=els[i];
        var required = $(obj).attr("required");
        if(required && required == "required") {
            if(validateEl(obj,true)==false) {
                $(obj).focus();
                return ;
            }
        }

    } ;
    var data= $("#infoform").parseForm() ;
    $("#infoform").find(":checkbox").each(function(){//处理复选框取值转换为1 0
        var name=$(this).attr("name")+"";
        data[name] = $(this).prop('checked')?"1":"0"  ;
    });
    //alert(JSON.stringify(data));
    var url='/common/savefile';
    if(that!=null){//指定了执行自定义类
        if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
            url=$(that).attr("bo-function");
        }
    }
    $("#infoform").submit(function(e){
           // var formObj = $(this);
            var formURL = url+'?funId='+funId;
            var formData = new FormData(this);

             formData.append("file",$("#infoform").find("input[type=file]").get(0).files[0]);
            $.ajax({
                url: formURL,
                type: 'POST',
                data:  formData,
                mimeType:"multipart/form-data",
                contentType: false,
                cache: false,
                processData:false,
                success: function(data, textStatus, jqXHR){
                    savecallback(data);
                },
                error: function(jqXHR, textStatus, errorThrown){
                    savecallback(data);
                }
                });
            e.preventDefault(); //Prevent Default action.
            e.unbind(); //to stop multiple form submit.
      });
    $("#infoform").submit();
    alert($("#infoform").find("input[type=file]").val());
   // ajaxSumit(url+'?funId='+funId,data, savecallback);
}



//导入数据事件
var importWindowDom=null;
function importWindow(that,pageType,windowDom) {
    var parames;
    if(that!=null){//指定了执行自定义类
        if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
            parames=$(that).attr("bo-function");
        }
    }

    if(pageType==null) pageType="import";
    if(top.location!=window.location) {
        return top.importWindow(that,pageType,this.window);
    }
    var importforeignKeyId;
    if(windowDom!=null) importWindowDom=windowDom;
    else windowDom=this.window;

    var datas=windowDom.$table.bootstrapTable('getSelections');
    var id=datas.length>0?datas[0][windowDom.keyIDColumn]:"";
     importforeignKeyId=windowDom.foreignKeyId;
    if(importforeignKeyId==null||importforeignKeyId=="") {
        if(windowDom.$.fn.zTree.getZTreeObj("tree")!=null){//有树结构
            var treeNodes=windowDom.$.fn.zTree.getZTreeObj("tree").getSelectedNodes();
            if(treeNodes!=null&&treeNodes.length>0){
                importforeignKeyId=  treeNodes[0].id ;
            }
        }
    }
    if(parames.indexOf("orgSQL")>0){
        var ps=parames.split("orgSQL");
        parames=ps[0]+"orgSQL"+unEscapeHTML(ps[1]);
    }
    var frameSrc = CONTEXT_PATH +"/iframe/import-window?targetFunID="+windowDom.funId+"&pageType="+pageType+"&importforeignKeyId="+importforeignKeyId+parames+"&selectId="+id;
      // alert(encodeURI(frameSrc));
    $("#selectIframe").attr("src", encodeURI(frameSrc));
    $('#selectWindowModal .modal-title').text("导入窗口");

    //居中
    $('#selectWindowModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 -$('#selectWindowModal .modal-dialog').height();
        if(modalHeight<0) modalHeight=50;
        var modalWidth=$(window).width() /2-$('#selectWindowModal .modal-dialog').width() / 2-100 ;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    $('#selectWindowModal').modal({ show: true, backdrop: 'static' });

    //模态框隐藏的事件
    // var n=1;
    // $('#selectWindowModal').on('hidden.bs.modal', function (e) {
    //     if(n-->0)
    //         callback();
    // });
}

//导入事件
function dataimport(that){

    var datas=$table.bootstrapTable('getSelections');
    var ids="";
    for(var i=0;i<datas.length;i++){
        let  keyId=datas[i][keyIDColumn];
        if(keyId==null||keyId=="") {
            alert("请选择要导入记录！");
            return;
        }
        ids+= keyId+";";
    }
    var eventId="";
    if(that!=null){//获取eventId
        if($(that).attr("id")!=null&&$(that).attr("id")!=""){
            eventId=$(that).attr("id");
        }
    }
    ajaxSumit('/common/dataimport?eventId='+eventId+'&sourceFunId='+funId+"&targetFunId="+targetFunID+"&pageType="+pageType+"&importforeignKeyId="+importforeignKeyId+(null!=selectId?"&selectId="+selectId:""),
        {ids:ids.substring(0,ids.length-1)}, iFrameClose);
}


//导入事件2  仅导入到Grid页面，不自动保存
function datacopy2page(that){

    var datas=$table.bootstrapTable('getSelections');
    if(datas==null||datas.length==0) {
        alert("请选择要导入记录！");
        return;
    }
    var sourceCols,targetCols,isTree=false;
    if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
        eval("var parames="+$(that).attr("bo-function")+";");
        if(parames==null||parames["sourceCols"]==null||parames["targetCols"]==null) return;
        sourceCols=parames["sourceCols"];
        targetCols=parames["targetCols"];
        if(parames["isTree"]!=null) isTree=parames["isTree"];
    }
    var sourceColsArr=sourceCols.split(";");
    var targetColsArr=targetCols.split(";");
    for(var i=0;i<datas.length;i++){
        var row=window.parent.adddatainit(isTree);
        for(var n=0;n<sourceColsArr.length;n++){
            row[targetColsArr[n]]=datas[i][sourceColsArr[n]];
        }
        row["isUpdateData"]="1";
        window.parent.$table.bootstrapTable('insertRow', {
            index: i,
            row: row
        });
    }
    window.parent._autoFresh=false;
    window.parent.$(".modal").modal("hide");
}

//form tab
function formTab(that,divkey){ 
//	alert($(that).parent().hasClass('active'));
//	if($(that).parent().hasClass('active')) return;
	if(divkey=="form"){
		$("#formInfoDiv").show();
		$("#formFrameDiv").hide();
	}else{
		var keyid=$("#infoform input[name='form_datakeyid']").val();
		if(keyid==null||keyid==""){
			alert("主键ID为空，请先保存新增记录或选中已有记录后再操作！");
			return;
		  }
		$("#subIframe").attr("src",CONTEXT_PATH + "/iframe/subfun-window?foreignColumn="+keyIDColumn+"&foreignKeyId="+keyid+"&height=660&url="+divkey);
		$("#formInfoDiv").hide();
		$("#formFrameDiv").show();
	 }
   $("#myFormTab").find("li").removeClass("active");
   $(that).parent().addClass('active');
}

//add form 
function add(isTree){
	var row= adddatainit(isTree);
    //新增前自定义方法
    if(typeof(preadd)!="undefined"){
        row=preadd(row);
        if(row==null) return;
    }
	form(row);//打开form窗口
}

var GlobalDefaults={};
function adddatainit(isTree){
    var row={},treenode={};
    // row["isUpdateData"]="2";

    var pId=$("#searchForm input[name='pId']").val();
    if(isTree!=null&&isTree){
        var pId=$("#searchForm input[name='pId']").val();
        //if(pId==""){
        //	alert("请先选择左边树节点再新增数据！");
        //	return;
        //}
        row["pId"]=pId;
    }else{
        if($.fn.zTree.getZTreeObj("tree")!=null){//有树结构
            var treeNodes=$.fn.zTree.getZTreeObj("tree").getSelectedNodes();
            if(treeNodes!=null&&treeNodes.length>0){
                treenode=  treeNodes[0].obj ;
                //alert( row["module_id"]);
            }
        }
    }
    for(var key in editData){//取默认值
        if(editData[key].default!=null){
            if(editData[key].default=="{tree}"){
                row[key]=(treenode[key]!=null?treenode[key]:"");

            }else{
                row[key]=GlobalDefaults!=null&&GlobalDefaults[key]!=null?GlobalDefaults[key]: defaultChange(editData[key].default);
            }
        }
    }
    if(foreignColumn!=null&&foreignColumn!=""){
        row[foreignColumn]=foreignKeyId;
    }

    return row;
}
//edkit form
function edit(){
	var row=$table.bootstrapTable('getSelections')[0];
	form(row);//打开form窗口
}

function form(row) {  
	
    $('#infoform').form('clear'); 
	$('#infoform').fillForm(row);
	try{ 
		$("#infoform input[name='form_datakeyid']").val(keyIDColumn==null||row[keyIDColumn]==null?"":row[keyIDColumn]);
		$("#myFormTab").find("li a")[0].click();
        reloadComponent();
	}catch(e){}
	//居中
	$('#formModal').on('show.bs.modal', function (e) {
		// 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
		$(this).css('display', 'block');
		var modalHeight=$(window).height() / 2 - $('#formModal .modal-dialog').height() / 2;
		var modalWidth=$(window).width() / 2 - $('#formModal .modal-dialog').width() / 2-100;
		$(this).find('.modal-dialog').css({
			'margin-top': modalHeight,
			'margin-left': modalWidth
		});
	});
	$('#formModal').modal({ show: true, backdrop: 'static' });
	var count = 0 ;
	$('#formModal').on("hide.bs.modal",function(){
			if(++count == 1) callback();
		});
}

//转换系统默认值
function defaultChange(value){
    if(value==null) return null;
    if(value.indexOf("{")>-1) {
        if(value=="{CURRENTUSERID}") return CURRENTUSERID;
        if(value=="{CURRENTUSERNAME}") return CURRENTUSERNAME;
        if(value=="{CURRENTDEPTID}") return CURRENTDEPTID;
        if(value=="{CURRENTDEPTNAME}") return CURRENTDEPTNAME;
        if(value=="{CURRENTCOMPANYID}") return CURRENTCOMPANYID;
        if(value=="{CURRENTSYSTEMID}") return CURRENTSYSTEMID;
        if(value=="{CURRENTONEDEPTID}") return CURRENTONEDEPTID;
        if(value=="{CURRENTTWODEPTID}") return CURRENTTWODEPTID;
        var num=0;
        if(value.indexOf("+")>0||value.indexOf("-")>0){
            num=value.substring(value.indexOf("}")+1,value.length);
            if(num.trim()=="") num=0;
            else num=num*1;
            value=value.substring(0,value.indexOf("}")+1);
        }
        //当前日期
        var myDate = new Date();
        if(num!=0)
            if(value=="{CURRENTMONTH}"){
                myDate=myDate.setMonth(myDate.getMonth()+num)
            }else if(value=="{CURRENTDATE}"||value=="{CURRENTTIME}"){
                myDate=myDate.setDate(myDate.getDate()+num)
            }
        var year=myDate.getFullYear();
        if(value=="{CURRENTYEAR}") return year;
        var month=myDate.getMonth()+1;
        if (month<10) {
            month='0'+month;
        }
        if(value=="{CURRENTMONTH}") return myDate.getFullYear()+"-"+(myDate.getMonth()+1);

        var day=myDate.getDate();
        if (day<10) {
            day='0'+day;
        }
        if(value=="{CURRENTDATE}") return year+'-'+month+'-'+day;

        var hour=myDate.getHours();
        var minute=myDate.getMinutes();
        var second=myDate.getSeconds();
        if (hour<10) {
            hour='0'+hour;
        }
        if (minute<10) {
            minute='0'+minute;
        }
        if (second<10) {
            second='0'+second;
        }
        if(value=="{CURRENTTIME}") return year+'-'+month+'-'+day+' '+hour+':'+minute+':'+second;

    }
    return unEscapeHTML(value);
}

//Grid新增
function gridadd(isTree) {
	var count = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true}).length;

	if(count>0&&isEditing>0){
		alert("必填字段为空,不能增加新行！"+isEditing);
		return;
	}
	var row={},treenode={};
	// row["isUpdateData"]="2";
	if(isTree!=null&&isTree){
		var pId=$("#searchForm input[name='pId']").val();
		//if(pId==""){
		//	alert("请先选择左边树节点再新增数据！");
		//	return;
		//}
		row["pId"]=pId;
	}else{
		if($.fn.zTree.getZTreeObj("tree")!=null){//有树结构
			var treeNodes=$.fn.zTree.getZTreeObj("tree").getSelectedNodes();
			if(treeNodes!=null&&treeNodes.length>0){
				treenode=  treeNodes[0].obj ;
				//alert( row["module_id"]);
			}
		}
	}
	for(var key in editData){//取默认值
		if(editData[key].default!=null){
			 if(editData[key].default=="{tree}"){
					row[key]=(treenode[key]!=null?treenode[key]:"");
			 }else{
				 row[key]=defaultChange(editData[key].default);
				 if(key=="order_index"){
				     row[key]=row[key]*1+10*count;
                 }

			 }
		}
	}

    if(foreignColumn!=null&&foreignColumn!=""){
        row[foreignColumn]=foreignKeyId;
    }
    row["data-index"]=count;
	$table.bootstrapTable('insertRow', {
		index: count,
		row: row
	});
    _autoFresh=true;
}


//复制事件 
function gridcopy(that,isTree) {
	//复制前自定义方法
	if(typeof(precopy)!="undefined"){
		if(precopy()) return;
	}

	var datas=$table.bootstrapTable('getSelections');
    if(datas==null||datas.length==0){
        alert("请选择至少一条记录后再操作！");
        return;
    }
	var ids="";
	for(var i=0;i<datas.length;i++){
		let  keyId=datas[i][keyIDColumn];
		if(keyId==null||keyId=="") {
			alert("新增行需要保存后才能复制！");
			return;
		}
		ids+= keyId+";";
//            for(var key in datas[i]) {//遍历
//                alert(key + "==" + datas[i][key]);
//            }
	}
	var url=isTree!=null&&isTree ? '/tree/copy' : '/common/copy';
	var parames="";
	if(that!=null){//指定了执行自定义类
		if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
			if($(that).attr("bo-function").indexOf("/")>-1) url=$(that).attr("bo-function");
			else parames=$(that).attr("bo-function");
		}
	}
    var eventId="";
    if(that!=null){//获取eventId
        if($(that).attr("id")!=null&&$(that).attr("id")!=""){
            eventId=$(that).attr("id");
        }
    }
	ajaxSumit(url+'?funId='+funId+parames,{ids:ids.substring(0,ids.length-1),eventId:eventId,foreignKeyId:foreignKeyId},isTree!=null&&isTree ? callbackTree:callback);
}

//保存事件
function gridsave(that,isTree) {

	var datas=$table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true});
	var submitData=[];
	for(var i=0;i<datas.length;i++){
		let  isUpdateData=datas[i]["isUpdateData"];

		if(isUpdateData==null||isUpdateData=="0") {// 没有修改过的不保存
			continue;
		}

		let n=0;
		for(var key in editData) {//遍历 检查必填项
//                alert(key + "==" + datas[i][key]);
			n++;
			if(editData[key]["required"]) {
				if (datas[i][key]==null||datas[i][key]=="") {
					alert("第"+(i+1)+"行，第"+n+"为必填字段，请检查后再保存！");
					var  temp = $table.find("tbody tr").eq(i).find("td").eq(n);
				   // alert(temp.html());
					allowEidt=true;
					temp.trigger("click");
					return;
				 }
			}
		} 
		 submitData[submitData.length]=datas[i];//新增和修改过记录提交到后台 

	}
	if(submitData.length==0){
		alert("没有找到需要保存的修改数据！");
		return;
	}
	var url=isTree!=null&&isTree ? '/tree/gridsave': '/common/gridsave';

    var parames="";
    if(that!=null){//指定了执行自定义类
        if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
            if($(that).attr("bo-function").indexOf("/")>-1) url=$(that).attr("bo-function");
            else parames=$(that).attr("bo-function");
        }
    }
    var eventId="";
    if(that!=null){//获取eventId
        if($(that).attr("id")!=null&&$(that).attr("id")!=""){
            eventId=$(that).attr("id");
        }
    }
    _autoFresh=true;
	ajaxSumit(url+'?funId='+funId+parames,{datas:JSON.stringify(submitData),eventId:eventId,foreignKeyId:foreignKeyId},isTree!=null&&isTree ? callbackTree:callback);
}

//删除事件
function griddelete(that,isTree) {

    //删除前自定义方法
    if(typeof(predelete)!="undefined"){
        if(predelete()) return;
    }
    var datas=$table.bootstrapTable('getSelections');
    if(datas==null||datas.length==0){
        alert("请选择至少一条记录后再操作！");
        return;
    }
    var isalert=true;
    //先删掉空行
    for(var i=0;i<datas.length;i++){
        var trs=$("#table").find("tbody").find("tr");
        for(var n=0;n<trs.length;n++){
            var tr=trs[n];
            var cktd=$(tr).find("input[name='btSelectItem']");
            if(cktd.attr("value")!=null&&cktd.attr("value")!="") continue;
            if(cktd.prop('checked')== true ){
                var ckindex=($(tr).find("input[name='btSelectItem']").attr("data-index"));
                $table.bootstrapTable('removeByIndex',ckindex);
                isalert=false;
                break;
            }
        }
    }
    datas=$table.bootstrapTable('getSelections');
    var ids="";
    for(var i=0;i<datas.length;i++){
        let  keyId=datas[i][keyIDColumn];
        if(keyId==null||keyId=="") {
            continue;
        }
        ids+= keyId+";"
    }
    if(ids==""){
         if(isalert)   alert("没有选中要删除的记录数据，请确认后再删除！");
        return;
    }

    _autoFresh=true;
    BootstrapDialog.confirm({
        title : '确认',
        message : "确认要删除？",
        type : BootstrapDialog.TYPE_WARNING, // <-- Default value is
        // BootstrapDialog.TYPE_PRIMARY
        closable : true, // <-- Default value is false，点击对话框以外的页面内容可关闭
        draggable : true, // <-- Default value is false，可拖拽
        btnCancelLabel : '取消', // <-- Default value is 'Cancel',
        btnOKLabel : '确定', // <-- Default value is 'OK',
        btnOKClass : 'btn-warning', // <-- If you didn't specify it, dialog type
        size : BootstrapDialog.SIZE_SMALL,
        // 对话框关闭的时候执行方法
        onhide : function () {

        },
        callback : function(result) {
            // 点击确定按钮时，result为true
            if (result) {

                var url=isTree!=null&&isTree ? '/tree/deletes' : '/common/deletes';

                var parames="";
                if(that!=null){//指定了执行自定义类
                    if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
                        if($(that).attr("bo-function").indexOf("/")>-1) url=$(that).attr("bo-function");
                        else parames=$(that).attr("bo-function");
                    }
                }
                var eventId="";
                if(that!=null){//获取eventId
                    if($(that).attr("id")!=null&&$(that).attr("id")!=""){
                        eventId=$(that).attr("id");
                    }
                }
                // 执行方法
                ajaxSumit(url+'?funId='+funId+parames,{ids:ids,eventId:eventId},isTree!=null&&isTree ? callbackTree:callback);
            }
        }
    });
}

/*
查询方案切换
*/
function querySelectFun(value){
	$("#searchForm").find("div[query-id^='query_']").hide();
	$("#searchForm").find("div[query-id='query_"+value+"']").show();
	$("input[name='query__selectCombo']").val(value);
}
/*
查询按钮
*/
function query(){
	var value=$("#queryCombo").val();
	if(value==null||value=="") value= defaultQueryId;
	$("input[name='query__selectCombo']").val(value);
	$('table').bootstrapTable('refresh', {silent: false});
}


//Copy Excel to新增
function copyimportwindow(that,isTree) {
    if(that!=null) {//定义列顺序
        if ($(that).attr("bo-function") != null && $(that).attr("bo-function") != "") {
            $("#importcopyDataReration").val( $(that).attr("bo-function")) ;
        }
    }
    if(isTree!=null&&isTree) {
        var pId = $("#searchForm input[name='pId']").val();
        if (pId == "") {
            alert("请先选择左边树节点再新增数据！");
            return;
        }
    }

    $("#importcopyData").html("");
    $("#importcopyData").focus();
    //居中
    $('#importcopyDataModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#importcopyDataModal .modal-dialog').height() / 2;
        var modalWidth=$(window).width() / 2 - $('#importcopyDataModal .modal-dialog').width() / 2-100;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    _autoFresh=false;
    $('#importcopyDataModal').modal({ show: true, backdrop: 'static' });

}
//在页面grid新增copy的数据
function copyimport() {
	var relation=$("#importcopyDataReration").val();
	if(relation=="") {
		alert("未定义字段对应关系，无法导入！");
        $("#importcopyDataReration").focus();
		return;
	}
    var copycols=relation.split(",");

	var count = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true}).length;
	var exceldata=$("#importcopyData").html();
	var copytab=$(exceldata);
	var datas=[];
	copytab.find("tr").each(function(i){  // 遍历 tr
		var data={};
		$(this).children('td').each(function(j){  // 遍历 tr 的各个 td
			//alert("第"+(i+1)+"行，第"+(j+1)+"个td的值："+$(this).text()+"。");
			if(j<copycols.length) data[copycols[j]]=$(this).text().trim();
		});
		data["order_index"]=5+5*i;//序号自递增+5
		data["isUpdateData"]="1";
		datas[datas.length]=data;
	});
	//alert( JSON.stringify(datas));
	for(var i=0;i<datas.length;i++){
		var row=datas[i];
		//if(i==0)alert( JSON.stringify(row));
		var pId=$("#searchForm input[name='pId']").val();
		row["pId"]=pId;
		if(pId==null||pId=="") {
            if ($.fn.zTree.getZTreeObj("tree") != null) {//有树结构
                var treeNodes = $.fn.zTree.getZTreeObj("tree").getSelectedNodes();
                if (treeNodes != null && treeNodes.length > 0) {
                    treenode = treeNodes[0].obj;
                    //alert( row["module_id"]);
                }
            }
        }

		for(var key in editData){//取默认值
			if(editData[key].default!=null&&row[key]==null){
				row[key]=defaultChange(editData[key].default);
			}
		}
        if(foreignColumn!=null&&foreignColumn!=""){
            row[foreignColumn]=foreignKeyId;
        }
		$table.bootstrapTable('insertRow', {
			index: count++,
			row: row
		});
    }
}

//弹出新窗口打开
function openWindowBlank(that) {
    var parames;
    var title=funName;
    var id=funId;
    if(that!=null){//指定了执行自定义类
        if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
            parames=$(that).attr("bo-function");
            title=$(that).text();
            id= $(that).attr("id");
        }
    }else{
        id=id+"_"+"open";
        var url=window.location.href;
        // if(url.indexOf("/iframe/open-window")>0){
        // alert("已经是独立窗口，无需再打开！");
        // return ;
        // }
        if(url.indexOf("#")>0){
            parames="&openurl=/"+url.split("#")[1];
        }else if(url.indexOf("openurl=/")>0){
            parames="&openurl=/"+url.split("openurl=/")[1];
        }else if(url.indexOf("openurl=")>0){
            parames="&openurl="+url.split("openurl=")[1];
        }else{
            parames="&openurl="+url;
        }
    }
    var dataID=foreignKeyId;
    if(that!=null){
        var datas=$table.bootstrapTable('getSelections');
        if(datas!=null&&datas.length>0) dataID= datas[0][keyIDColumn];
    }

    if(top.$("#navigation").length>0) openTabFrame("",CONTEXT_PATH +"/iframe/open-window?foreignColumn="+(keyIDColumn==null?"":keyIDColumn)+"&foreignKeyId="+(dataID==null?"":dataID)+parames,title);
    else window.open( CONTEXT_PATH +"/iframe/open-window?foreignColumn="+(keyIDColumn==null?"":keyIDColumn)+"&foreignKeyId="+(dataID==null?"":dataID)+parames, "_blank");
}


var editIndex=-1;
var sourceCols;
var targetCols;
//选择窗口选择返回GRID可编辑赋值（单选）
function selectCallBack(row){
    if(editIndex==-1) {
        formSelectCallBack(row);
        return;
    }
    //  alert(JSON.stringify(row));
    var rows = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true});
    var n=0
    for(var key in editData) {//遍历 检查必填项
        n++;
        if(sourceCols==null||sourceCols==""){//同名自动赋值
            if(key=="add_userid"||key=="add_date"||key=="modify_date"||key=="modify_userid"||key=="record_flag"||key=="memo") continue;//公共字段不带入

            if (row[key]!=null) {
                let  temp = $table.find("tbody tr").eq(editIndex).find("td").eq(n);
                if(temp.text()==temp.html()){
                    temp.html(row[key])
                }else{
                    try{
                        temp.find("input").val(row[key])
                    }catch(e){}
                }
                rows[editIndex][key] = row[key];
            }
        }else{
            if((targetCols+";").indexOf(key+";")>-1){
                let targetColArr=targetCols.split(";");
                let sourceColsArr=sourceCols.split(";");
                for(let i=0;i<targetColArr.length;i++){
                    if(targetColArr[i]==key){
                        if (row[sourceColsArr[i]]!=null) {
                            let  temp = $table.find("tbody tr").eq(editIndex).find("td").eq(n);
                            if(temp.text()==temp.html()){
                                temp.html(row[sourceColsArr[i]])
                            }else{
                                try{
                                    temp.find("input").val(row[sourceColsArr[i]])
                                }catch(e){}
                            }
                            rows[editIndex][key] = row[sourceColsArr[i]];
                        }
                    }
                }
            }
        }
    }

    rows[editIndex]["isUpdateData"] = "1";//标记记录被修改
}


//更新事件
function update(that){

    var datas=$table.bootstrapTable('getSelections');
    var ids="";
    for(var i=0;i<datas.length;i++){
        let  keyId=datas[i][keyIDColumn];
        if(keyId==null||keyId=="") {
            alert("请先保存，再选择要操作的记录！");
            return;
        }
        ids+= keyId+";";
    }

    var parames={};
    if(that!=null){//获取更新参数值
        if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
            eval("parames="+$(that).attr("bo-function"));
        }
    }

    var eventId="";
    if(that!=null){//获取eventId
        if($(that).attr("id")!=null&&$(that).attr("id")!=""){
            eventId=$(that).attr("id");
        }
    }
    ajaxSumit('/common/update?funId='+funId+"&eventId="+eventId+"&foreignKeyId="+foreignKeyId+'&ids='+ids  ,  parames, callback);

}

//仅执行工作流
function exec(that){

    var parames={};
    if(that!=null){//获取更新参数值
        if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
            eval("parames="+$(that).attr("bo-function"));
        }
    }
    var eventId="";
    if(that!=null){//获取eventId
        if($(that).attr("id")!=null&&$(that).attr("id")!=""){
            eventId=$(that).attr("id");
        }
    }
    ajaxSumit('/common/exec?funId='+funId+"&eventId="+eventId+"&foreignKeyId="+foreignKeyId  ,  parames, callback);

}


//Form、查询条的选择窗口
function formSelectWindow(control_name,vsourceCols,vtargetCols,vwhereSQL){

    sourceCols=vsourceCols;
    targetCols=vtargetCols;
    if(control_name==null||control_name==""||sourceCols==null||sourceCols==""||targetCols==null||targetCols==""){
        alert("未定义选择窗口控件值或字段对应关系，ERROR！");
        return;
    }
    try{
        var select=eval(control_name);
        var url = CONTEXT_PATH + "/iframe/select-window?url="+select["layout_url"]+"?funId="+select["fun_id"]+(vwhereSQL==null||vwhereSQL==""||vwhereSQL=="null"?"":"&orgSQL="+unEscapeHTML(vwhereSQL));
        $("#selectIframe").attr("src", encodeURI(url));
        $('#selectWindowModal .modal-title').text("选择窗口");
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


//form选择返回赋值
function formSelectCallBack(rows){
    let targetColArr=targetCols.split(";");
    let sourceColsArr=sourceCols.split(";");
    for(let i=0;i<targetColArr.length;i++){
         $("input[name='"+targetColArr[i]+"']").val(rows[sourceColsArr[i]]);
    }
    // var cols= targetCols.split(";");
    // var scols= sourceCols.split(";");
    // for(var n=0;n< rows.length;n++) {
    //     var row=rows[n];
    //     for (var i = 0; i < cols.length; i++) {
    //         if ((";"+$("input[name='" + cols[i] + "']").val()+";").indexOf(";"+row[scols[i]] + ";") > -1) break;
    //         if(cols[i].indexOf("sponsor")<0) $("input[name='" + cols[i] + "']").val($("input[name='" + cols[i] + "']").val() + ($("input[name='" + cols[i] + "']").val() == "" ? "" : ";") + row[scols[i]]);
    //         else $("input[name='" + cols[i] + "']").val( row[scols[i]]);
    //     }
    // }

}


//Form窗口
var formRow;
function formWindow(that){
    var datas=$table.bootstrapTable('getSelections');
    if(datas==null||datas.length==0){
        alert("请选择至少一条记录后再操作！");
        return;
    }
    formRow=datas[0];
    try{
        var url = CONTEXT_PATH + "/iframe/form-window?funId="+funId+ "&pageType=form";
        $("#selectIframe").attr("src", url);
        $('#selectWindowModal .modal-title').text("表单详细");
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






//通用批量更新字段值
function batchupdateWindow(that) {
    var datas=$table.bootstrapTable('getSelections');
    if(datas==null||datas.length==0){
        alert("请选择至少一条记录后再操作！");
        return;
    }
    $("#CommonUpdateForm").html("");//清空旧数据
    // var columns="fun_id;fun_name;fun_type";  ///指定要修改的字段
    if(funParames!=null&&funParames["AllowEditCloumns"]!=null) {
        columns = funParames["AllowEditCloumns"].split(";");
        for (var i = 0; i < columns.length; i++) {
            addColumnSet(columns[i]);//新增一行
        }
        $("#CommonUpdateModal").find(".modal-body button").hide();
    }else{
        addColumnSet();//新增一行
        $("#CommonUpdateModal").find(".modal-body button").show();
    }


    //居中
    $('#CommonUpdateModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#CommonUpdateModal .modal-dialog').height() / 2;
        var modalWidth=$(window).width() / 2 - $('#CommonUpdateModal .modal-dialog').width() / 2-200;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    $('#CommonUpdateModal').modal({ show: true, backdrop: 'static' });

}



//高级查询
function seniorQueryWindow(that) {
    $("#CommonQueryForm").find("tbody tr").each(function(){
        var display =$(this).css('display');
        if(display != 'none'){
            $(this).remove();//清空旧数据
        }
    })
    addQueryColumnSet();//新增一行
    //居中
    $('#CommonQueryModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#CommonQueryModal .modal-dialog').height() / 2;
        var modalWidth=$(window).width() / 2 - $('#CommonQueryModal .modal-dialog').width() / 2-200;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    $('#CommonQueryModal').modal({ show: true, backdrop: 'static' });

}

//校验
function validateEl(field,isForm) {
    if(isForm==null) isForm=false;
    var el = $(field);
    var result = validateField(el);
//            var controlGroup = el.parents('.form-group');
//            if(result.result) {
//              controlGroup.removeClass('has-error');
//            } else {
//                controlGroup.find(".help-block").remove();
//                if(!isForm) controlGroup.addClass('has-error');
//                el.after('<span class="help-block">' + result.errorMsg +'</span>');
//            }
    return result.result;
}

function validateField(el) {
    //验证非空表单
    var required = el.attr("required");
    if(required && required == "required") {
        var requiredMsg = el.attr("required-message");
        requiredMsg = requiredMsg ? requiredMsg : "该项不能为空";
        var value = el.val();
        if(value == null || $.trim(value) == "") {
            return {result:false, errorMsg:requiredMsg};
        }
    }

    //验证表单数据类型
    var checkType = el.attr("check-type");
    if(checkType) {
        var value = el.val();
        var checkTypeMsg = el.attr("check-type-msg");
        checkTypeMsg = checkTypeMsg ? checkTypeMsg : "请填写正确的数据格式";
        if(checkType == "number") {
            if(validRules.checkNumber(value)) {
                return {result:false, errorMsg:checkTypeMsg};
            }
        } else if(checkType == "int") {
            if(validRules.checkInt(value)) {
                return {result:false, errorMsg:checkTypeMsg};
            }
        } else if(checkType == "url") {
            if(validRules.checkUrl(value)) {
                return {result:false, errorMsg:checkTypeMsg};
            }
        } else if(checkType == "email") {
            if(validRules.checkEmail(value)) {
                return {result:false, errorMsg:checkTypeMsg};
            }
        } else if(checkType == "mobile") {
            if(validRules.checkMobile(value)) {
                return {result:false, errorMsg:checkTypeMsg};
            }
        }
    }

    //验证字符串长度
    var minLength = el.attr("minLength");
    var minLengthMsg = el.attr("minLengthMsg");
    minLengthMsg = minLengthMsg ? minLengthMsg : "请输入正确的字符长度";
    if(minLength) {
        var value = el.val();
        if(value.length <= minLength) {
            return {result:false, errorMsg:minLengthMsg};
        }
    }
    var maxLength = el.attr("maxLength");
    if(maxLength) {
        var maxLengthMsg = el.attr("maxLengthMsg");
        maxLengthMsg = maxLengthMsg ? maxLengthMsg : "请输入正确的字符长度";
        if(value.length > maxLength) {
            return {result:false, errorMsg:minLengthMsg};
        }
    }

    //验证数值大小
    var min = el.attr("min");
    if(min && !isNaN(min)) {
        var value = el.val();
        var minMsg = el.attr("min-msg");
        minMsg = minMsg ? minMsg : "请输出正确的数值";
        if(isNaN(value) || value <= min) {
            return {result:false, errorMsg:minMsg};
        }
    }
    var max = el.attr("max");
    if(max && !isNaN(max)) {
        var value = el.val();
        var maxMsg = el.attr("max-msg");
        maxMsg = maxMsg ? maxMsg : "请输入正确数值";
        if(isNaN(value) || value > max) {
            return {result:false, errorMsg:maxMsg};
        }
    }

    return {result:true};
}

var validRules = {
    checkInt:function(value) {
        return (!/^[0-9]\d*$/.test(value));
    },
    checkNumber:function(value) {
        return (!/^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(value));
    },
    checkEmail:function(value) {
        return (!/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(value));
    },
    checkUrl:function(value) {
        !/^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value)
    },
    checkMobile:function(value) {
        return (!/^0?(13[0-9]|15[0-9]|17[0678]|18[0-9]|14[57])[0-9]{8}$/.test(value));
    }
}

function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(typeof obj == 'object' && obj ){
                return true;
            }else{
                return false;
            }

        } catch(e) {
            console.log('error：'+str+'!!!'+e);
            return false;
        }
    }
    console.log('It is not a string!')
}


/*
 导出数据到文件
 */
function exportXls(){
    $("#exportDataModal input[name='exportType']").val("excel");
    openExportWindow();
}
function exportTxt(){
    $("#exportDataModal input[name='exportType']").val("txt");
    openExportWindow();
}
function exportJson(){
    $("#exportDataModal input[name='exportType']").val("json");
    openExportWindow();
}
function exportSQL(){
    $("#exportDataModal input[name='exportType']").val("sql");
    openExportWindow();
}

function openExportWindow(){
    //居中
    $('#exportDataModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#exportDataModal .modal-dialog').height() / 2;
        var modalWidth=$(window).width() / 2 - $('#exportDataModal .modal-dialog').width() / 2-100;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    $('#exportDataModal').modal({ show: true, backdrop: 'static' });
}
function exportFile(){

    var exportScala=$("#exportDataModal input[name='exportScala']:checked").val();
    var filetype=$("#exportDataModal input[name='exportType']").val();
    $table.tableExport(
        {type:filetype,
            exportDataType:exportScala,
            fileName:funName,
            tableName:funId,
            ignoreColumn: [0],  //忽略某一列的索引
            worksheetName: 'sheet1',
            escape:'false',
            excelstyles: ['background-color', 'color', 'font-weight', 'border']});

    $('#exportDataModal').modal("hide");
}

//设置图标
function selectIcoListWindow(taleName,icoCol) {
    if(icoCol==null) icoCol="ico_img";
    var datas=$table.bootstrapTable('getSelections');
    var dataID=datas[0][keyIDColumn];
    var frameSrc = CONTEXT_PATH +"/iframe/select-ico-window?tableName="+taleName+"&dataID="+dataID+"&columnName="+icoCol;
    $("#selectIframe").attr("src", frameSrc);
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

}

//上传附件，可以在bo-中指定更新字段
function openUpload(that,fileType,typeId){
    var datas=$table.bootstrapTable('getSelections');
    if(datas==null||datas.length!=1){
        alert("请选择一条记录后再操作！");
        return;
    }
    var parames = "";
    if ($(that).attr("bo-function") != null && $(that).attr("bo-function") != "") {
        parames = $(that).attr("bo-function");
    }
    if(parames==null||parames==""||parames.indexOf("{")<0||parames.indexOf("updateColumnName")<0){
        alert("ERROR！未定义更新字段信息，请联系系统管理员！");
        return;
    }
    eval("parames="+parames);//转Json对象
    openUploadFileWindow(null,fileType,typeId,null,parames);
}

//            'fileType':[
//                "application/pdf",
//                "application/msword",
//                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
//                "application/vnd.ms-excel",
//                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
//                "application/vnd.ms-powerpoint",
//                "application/vnd.openxmlformats-officedocument.presentationml.presentation",
//                "application/zip",
//                "application/x-rar-compressed",
//            ]
//column_name用于form页面上传时返回值的
function openUploadFileWindow(uploadScript,fileType,typeId,column_name,parames){
    var datas=$table.bootstrapTable('getSelections');
    //居中
    $('#uploadFileModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#uploadFileModal .modal-dialog').height() / 2;
        var modalWidth=$(window).width() / 2 - $('#uploadFileModal .modal-dialog').width() / 2-100;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    $('#uploadFileModal').modal({ show: true, backdrop: 'static' });

    //模态框隐藏的事件
    var n=1;
    if(column_name==null)
        $('#uploadFileModal').on('hidden.bs.modal', function (e) {
            if(n-->0)
                callback();
        });
    var accept={};
    if(fileType=="img"){
        accept={
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    }else if(fileType=="xls"){
        accept={
            title: 'xls',
            extensions: 'xls,xlsx',
            mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        }
    }
    var uploader = WebUploader.create({
        // 选完文件后，是否自动上传。
        auto: false,
        // swf文件路径
        swf: CONTEXT_PATH + '/static/libs/webuploader-0.1.5/Uploader.swf',
        // 文件接收服务端。
        server: CONTEXT_PATH + (uploadScript==null||uploadScript==""? '/file/upload':uploadScript), //'/excel/import' excel导入
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#pickerFile',
        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false,
        // // 只允许选择图片文件。
        accept:accept ,
        fileNumLimit: 5,//上传数量限制
        /* fileSizeLimit :100*1024*1024, //验证文件总大小是否超出限制, 超出则不允许加入队列*/
        fileSingleSizeLimit :100*1024*1024,   //验证单个文件大小是否超出限制, 超出则不允许加入队列。
        duplicate :true, //去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
        formData: {
        }
    });
    // 当文件被加入队列之前触发，此事件的handler返回值为false，则此文件不会被添加进入队列。
    // uploader.on( 'beforeFileQueued', function( file ) {
    //     // 限制图片数量
    //     img_length = $("#thelist img").length;
    //     if (img_length >= 6) {
    //         layer.msg("图片最多上传6张");
    //         return false;
    //     }
    //
    // });
    if($("#theFileList").find(".item-file").length>0)
        $("#theFileList").find(".item-file").remove();
    // 当有文件被添加进队列的时候
    uploader.on( 'fileQueued', function( file ) {

        $("#theFileList").append( '<div id="' + file.id + '" class="item-file" style="border:1px solid #ccc;">' +
            '<h4 class="info">' + file.name + '</h4>' +
             '<span style="margin-left: 78%;cursor:pointer;" onclick="$(this).parent().remove();">删除</span>' +
            '<p class="state">等待上传...</p>' +
            '</div>' );
    });
    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
                '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                '</div>' +
                '</div>').appendTo( $li ).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');

        $percent.css( 'width', percentage * 100 + '%' );
    });
    uploader.on( 'uploadSuccess', function( file ) {
        $( '#'+file.id ).find('p.state').text('已上传');
    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });
    //  验证大小
    uploader.on("error",function (type){
        // if (type == "Q_TYPE_DENIED") {
        //     layer.msg("请上传JPG、PNG格式文件");
        // } else
            if(type == "F_DUPLICATE"){
            win.alert("系统提示","请不要重复选择文件！");
        }else if(type == "Q_EXCEED_SIZE_LIMIT"){
            win.alert("系统提示","<span class='C6'>所选附件总大小</span>不可超过<span class='C6'>100M</span>哦！<br>换个小点的文件吧！");
        }

    });
    uploader.on("uploadSuccess", function (file, response) {
       var result= (response._raw) ;
       if(column_name!=null){
           eval("result="+result);
           // alert(JSON.stringify(result.data));
           if(result.success){
               var file_id=result.data[0]["file_id"];
             //  alert(file_id);
               $("#infoform").find("input[name="+column_name+"]").val(file_id);
               $("#infoform").find("img[name="+column_name+"_img]").attr("src",CONTEXT_PATH+"/file/download?file_id="+file_id);
           }
       }
    });

    //绑定上传按钮
    $("#ctlBtn").click(function(){
        var formData= {
            auditing : '0',
            funId : funId,
            keyId : column_name != null ? $("#infoform").find("input[name=" + keyIDColumn + "]").val()
                : (datas == null || funId == "system_attachment" ? foreignKeyId : datas[0][keyIDColumn]),
            typeId : typeId == null ? "other" : typeId
        }
         $.extend(formData,parames);
        uploader.options.formData=formData;
        uploader.upload();
    })
}



//查看附件列表
function attachment(pageType) {
    if(pageType==null) pageType="grid";
    var datas = $table.bootstrapTable('getSelections');
    if(datas==null||datas.length==0){
        alert("请选择一条记录后再操作！");
        return;
    }
    var frameSrc =CONTEXT_PATH + "/iframe/open-window?openurl=/layout/layout-common-grid?funId=system_attachment&pageType="+pageType+"&foreignKeyId="+datas[0][keyIDColumn]+"&orgSQL=data_id='"+datas[0][keyIDColumn]+"'";
    // alert(frameSrc);
    top.$("#selectIframe").attr("src", frameSrc);
    top.$('#selectWindowModal .modal-title').text("附件窗口");
    //居中
    top.$('#selectWindowModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 -top. $('#selectWindowModal .modal-dialog').height() / 2;
        var modalWidth=$(window).width() / 2 - top.$('#selectWindowModal .modal-dialog').width() / 2-200;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    top.$('#selectWindowModal').modal({ show: true, backdrop: 'static' });

}

//查看或下载附件
function downloadAttachment(that,fileColumn) {
    var datas = $table.bootstrapTable('getSelections');
    if(datas==null||datas.length==0){
        alert("请选择一条记录后再操作！");
        return;
    }
    var url="";
    if(fileColumn==null||fileColumn==""){
        if(datas[0]["file_id"]==null||datas[0]["file_id"]==""){
            alert("没有附件！");
            return;
        }
        url="/file/download?file_id="+datas[0]["file_id"];
    }else{
        if(datas[0][fileColumn]==null||datas[0][fileColumn]==""){
            alert("没有附件！");
            return;
        }
        if(datas[0][fileColumn].indexOf("file_id")>-1){
            url=datas[0][fileColumn];
        }else if(datas[0][fileColumn].indexOf("/")>-1){
            url=datas[0][fileColumn];
        }else{
            url="/file/download?file_id="+datas[0][fileColumn];
        }

    }

    window.open( CONTEXT_PATH +url, "_blank");

}


//图片上传预览功能
function changepic(objname) {
    $("#infoform").find("div[name="+objname+"]").find("div[name=prompt3]").css("display", "none");
    var reads = new FileReader();
    f = $("#infoform").find("div[name="+objname+"]").find("input[type=file]").get(0).files[0];
    reads.readAsDataURL(f);
    reads.onload = function (e) {
        $("#infoform").find("input[name="+objname.substring(0,objname.length-5)+"]").val( $("#infoform").find("div[name="+objname+"]").find("input[type=file]").val());
       // alert($("#infoform").find("input[name="+objname.substring(0,objname.length-5)+"]").val());

        $("#infoform").find("div[name="+objname+"]").find("img").attr("src" , this.result);
        $("#infoform").find("div[name="+objname+"]").find("img").css("display", "block");
        $("#infoform").find("div[name="+objname+"]").find(".cover").css("display", "block");
        $("#infoform").find("div[name="+objname+"]").find(".cover").hover(function () {
            $("#infoform").find("div[name="+objname+"]").find(".cover").addClass("covers");
            $("#infoform").find("div[name="+objname+"]").find(".imgting").addClass("sss");
        }, function () {
            $("#infoform").find("div[name="+objname+"]").find(".cover").removeClass("covers");
            $("#infoform").find("div[name="+objname+"]").find(".imgting").removeClass("sss");
        });
    };
}

function imgSpan(objname) {
    $("#infoform").find("div[name="+objname+"]").find("input[type=file]").val("");
}

//删除图片
function delimg(objname) {
    $("#infoform").find("div[name="+objname+"]").find("div[name=prompt3]").css("display", "block");
    $("#infoform").find("div[name="+objname+"]").find("img").css("display", "none");
    $("#infoform").find("div[name="+objname+"]").find(".cover").css("display", "none");
    $("#infoform").find("div[name="+objname+"]").find("input[type=file]").val("");
}


//图表预览
function viewChartWindow(that) {
    var parames = "";
    if (that != null) {//指定了执行自定义类
        if ($(that).attr("bo-function") != null && $(that).attr("bo-function") != "") {
            parames = $(that).attr("bo-function");
        }
        if(parames==null||parames==""){
            alert("ERROR！未定义ChartID，请联系系统管理员！");
            return;
        }
    } else {
        var datas = $table.bootstrapTable('getSelections');
        parames="?chartID="+datas[0].chart_id;
    }
    var frameSrc = CONTEXT_PATH +"/iframe/chart-window"+parames;
    // alert(frameSrc);
    $("#selectIframe").attr("src", frameSrc);
    $('#selectWindowModal .modal-title').text("导入窗口");
    //居中
    $('#selectWindowModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#selectWindowModal .modal-dialog').height() / 2;
        var modalWidth=$(window).width() / 2 - $('#selectWindowModal .modal-dialog').width() / 2-200;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    $('#selectWindowModal').modal({ show: true, backdrop: 'static' });
    //模态框隐藏的事件
    // var n=1;
    // $('#selectWindowModal').on('hidden.bs.modal', function (e) {
    //     if(n-->0)
    //         callback();
    // });
}


//行内事件获取点击行数据例子，必须要有index这个参数，通过这个参数来找数据
function xxx(that,index){
    var  row =$table.bootstrapTable('getRowByIndex',index);
    alert(JSON.stringify(row));
}


//将表单数据转为json
function form2Json(id) {

    var arr = $("#" + id).serializeArray()
    var jsonStr = "";

    jsonStr += '{';
    for (var i = 0; i < arr.length; i++) {
        jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",'
    }
    jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
    jsonStr += '}'

    var json = JSON.parse(jsonStr)
    $("#" + id).find(":checkbox").each(function(){//处理复选框取值转换为1 0
        var name=$(this).attr("name")+"";
        if(name!="")
            json[name] = $(this).prop('checked')?"1":"0"  ;
    });
    return json
}


//blur计算合计值
function computeMoney(pricecol,numcol,moneycol){
    //form页面
    if(editIndex==-1) {
         var price=$("input[name='"+pricecol+"']").val();
         var num=$("input[name='"+numcol+"']").val();
         if(price==null||price=="") price="0";
         if(num==null||num=="") num="0";
         $("input[name='"+moneycol+"']").val(Math.round(price*num*100)/100.0);

    }else{//grid页面
          //  alert(JSON.stringify(row));
        var rows = $table.bootstrapTable('getData', {useCurrentPage: true, includeHiddenRows: true});

        var price=getTableTdValBbyIndex(editIndex,pricecol);//rows[editIndex][pricecol];
        var num=getTableTdValBbyIndex(editIndex,numcol);//rows[editIndex][numcol];
        if(price==null||price=="") price="0";
        if(num==null||num=="") num="0";

        var money=(Math.round(price*num*100)/100.0);
        rows[editIndex][moneycol]=money;
        setTableTdValBbyIndex(editIndex,moneycol,money);

        rows[editIndex]["isUpdateData"] = "1";//标记记录被修改
    }
}
//获取编辑中的grid值
function getTableTdValBbyIndex(editIndex,colname){
    var n=0
    for(var key in editData) {//遍历 检查必填项
        if(key==colname) break;
        n++;
    }
    n=n+1;
    let  temp = $table.find("tbody tr").eq(editIndex).find("td").eq(n);
    if(temp.text()==temp.html()){
        return temp.html()
    }else{
        try{
            return temp.find("input").val()
        }catch(e){}
    }
    return null;
}

//设置编辑中的grid值
function setTableTdValBbyIndex(editIndex,colname,value){
    var n=0
    for(var key in editData) {//遍历 检查必填项
        if(key==colname) break;
        n++;
    }
    n=n+1;
    let  temp = $table.find("tbody tr").eq(editIndex).find("td").eq(n);
    if(temp.text()==temp.html()){
        temp.html(value)
    }else{
        try{
            temp.find("input").val(value)
        }catch(e){}
    }
}


//弹出修改密码窗口
function systemChangePWDWindow(){
    // var datas=$table.bootstrapTable('getSelections');
    // if(datas==null||datas.length==0){
    //     alert("请选择至少一条记录后再操作！");
    //     return;
    // }
    // var row=datas[0];
    $("#changePwdForm input[name=userId]").val(CURRENTUSERID);
    // $("#changePwdForm input[name=userCode]").val(CURRENTUSERCODE);
    $("#changePwdForm input[name=userName]").val(CURRENTUSERNAME);

    //居中
    $('#systemChangePwdModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#systemChangePwdModal .modal-dialog').height() / 2;
        var modalWidth=$(window).width() / 2 - $('#systemChangePwdModal .modal-dialog').width() / 2-100;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    $('#systemChangePwdModal').modal({ show: true, backdrop: 'static' });

}
//修改密码
function systemChangePassword(){
    var userId=$("#changePwdForm input[name=userId]").val();
    var userCode=$("#changePwdForm input[name=userCode]").val();
    var userName=$("#changePwdForm input[name=userName]").val();
    var oldPassword=$("#changePwdForm input[name=oldPassword]").val();
    var newPassword=$("#changePwdForm input[name=newPassword]").val();
    var newPassword2=$("#changePwdForm input[name=newPassword2]").val();
       if(oldPassword ==""){
           alert("旧密码不能为空！");
           $("#changePwdForm input[name=oldPassword]").focus();
           return;
       }
    if(newPassword ==""||newPassword2==""){
        alert("新密码或新密码缺认不能为空！");
        $("#changePwdForm input[name=newPassword]").focus();
        return;
    }
    if(newPassword!=newPassword2){
        alert("新密码和确认新密码不一致，请重新输入");
        $("#changePwdForm input[name=newPassword]").val("");
        $("#changePwdForm input[name=newPassword2]").val("");
        $("#changePwdForm input[name=newPassword]").focus();
        return;
    }
    if(newPassword.length<6){
        alert("新密码长度小于6位，请重新输入！");
        $("#changePwdForm input[name=newPassword]").focus();
        return;
    }
    var parames={userId:userId,userCode:userCode,userName:userName,newPassword:$.base64.encode(newPassword),oldPassword:$.base64.encode(oldPassword)};
    ajaxSumit('/system/changePassword'  ,  parames, callback);
}



//codeEditor
function codeEditorWindow(that,filepath){
    var parames="";
    if(that!=null){//指定了执行自定义类
        if($(that).attr("bo-function")!=null&&$(that).attr("bo-function")!=""){
            if($(that).attr("bo-function").indexOf("/")>-1) url=$(that).attr("bo-function");
            else parames=$(that).attr("bo-function");
        }
    }
    if(filepath==null||filepath==""){
        alert("文件路径为空，请先设置文件路径！");
        return;
    }

    var url = CONTEXT_PATH + "/iframe/codeeditor-window?mainDIR=SYSTEMDIR&filepath="+filepath+ parames;
    // alert(encodeURI(url));
    top.$("#selectIframe").attr("src", encodeURI(url));
    top.$('#selectWindowModal .modal-title').text("代码编辑器");
    //居中
    top.$('#selectWindowModal').on('show.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=top.$(window).height() / 2 - top.$('#selectWindowModal .modal-dialog').height() / 2;
        var modalWidth=top.$(window).width() / 2 - top.$('#selectWindowModal .modal-dialog').width() / 2-100;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
            'margin-left': modalWidth
        });
    });
    top.$('#selectWindowModal').modal({ show: true, backdrop: 'static' });
}

//提交审批
function putCheckWindow(){
    var datas=$table.bootstrapTable('getSelections');
    if(auditColumn==null&&(datas[0][auditColumn]!="0"||datas[0][auditColumn]!="3")){
        alert("未定义审批状态字段或该记录已提交过审批！");
        return;
    }

    function getnextNodesCallBackFun(data){
        if(data.success){
            var nodes=data.data.nodes;
            // alert(JSON.stringify(data.data));
            if(nodes!=null&&nodes.length>0) {
                $("#nextNodeRodio").html("");
                for(var i=0;i<nodes.length;i++){
                    var radio=" <input type=\"radio\" name=\"nextRadio\" value=\""+nodes[i]["node_id"]+"\" style=\"margin-left:20px;\" "+(i==0?"checked":"")+"/> <label "+(nodes[i]["node_name"]=="结束"?"style=\"color:red;\"":"")+">" + nodes[i]["node_name"]+"</label>";
                    $("#nextNodeRodio").append(radio);
                }

                $("#putCheckWindowModal").find("input[name='wf_id']").val(data.data.wf_id);
                $("#putCheckWindowModal").find("input[name='step_no']").val(data.data.step_no);
            }
            try{
                //居中
                $('#putCheckWindowModal').on('show.bs.modal', function (e) {
                    // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
                    $(this).css('display', 'block');
                    var modalHeight=$(window).height() / 2 - $('#putCheckWindowModal .modal-dialog').height() / 2;
                    var modalWidth=$(window).width() / 2 - $('#putCheckWindowModal .modal-dialog').width() / 2-100;
                    $(this).find('.modal-dialog').css({
                        'margin-top': modalHeight,
                        'margin-left': modalWidth
                    });
                });
                $('#putCheckWindowModal').modal({ show: true, backdrop: 'static' });
            }catch(e){}
        }
    }

    ajaxSumit( "/workflow/getnextNodes?funId="+funId,{dataId:datas[0][keyIDColumn]},getnextNodesCallBackFun);

}

//提交审批
function checkPut() {
    var datas=$table.bootstrapTable('getSelections');
    var wfId=$("#putCheckWindowModal").find("input[name='wf_id']").val();
    var nodeId=$("#putCheckWindowModal").find("input[name='nextRadio']").val();
    var stepNo=$("#putCheckWindowModal").find("input[name='step_no']").val();
    var keyId=datas[0][keyIDColumn];
    if(nodeId==null||nodeId==""){
        alert("请选择节点！");
        return;
    }
    if(keyId==null||keyId==""){
        alert("审批记录获取失败！");
        return;
    }
    if(wfId==null||wfId==""){
        alert("审批流获定义id取失败！");
        return;
    }

    var sponsor_user_id=$("#putCheckWindowModal").find("input[name='sponsor_user_id']").val();
    var sponsor_user_name=$("#putCheckWindowModal").find("input[name='sponsor_user_name']").val();

    if(sponsor_user_id==null||sponsor_user_id==""){
        alert("主办人不能为空！");
        return;
    }
    var operator_user_id=$("#putCheckWindowModal").find("input[name='operator_user_id']").val();
    var operator_user_name=$("#putCheckWindowModal").find("input[name='operator_user_name']").val();
    var copy_user_id=$("#putCheckWindowModal").find("input[name='copy_user_id']").val();
    var copy_user_name=$("#putCheckWindowModal").find("input[name='copy_user_name']").val();

    var auditColumn=auditColumn;
    if(auditColumn==null||auditColumn=="") auditColumn="auditing";
    var jsonData={
        wfId:wfId,
        nodeId:nodeId,
        auditColumn:auditColumn,
        keyId:keyId,
        stepNo:stepNo,
        sponsor_user_id:sponsor_user_id,
        sponsor_user_name:sponsor_user_name,
        operator_user_id:operator_user_id,
        operator_user_name:operator_user_name,
        operator_user_id:operator_user_id,
        copy_user_id:copy_user_id,
        copy_user_name:copy_user_name};
    jsonData[auditColumn] ="2";//状态改为2，审批中

    ajaxSumit( "/workflow/putNext?funId="+funId,jsonData,null);
    $('#putCheckWindowModal').modal("hide");
}


//
// //退回节点
// function backCheckWindow(){
//
//     try{
//         //居中
//         $('#backCheckWindowModal').on('show.bs.modal', function (e) {
//             // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
//             $(this).css('display', 'block');
//             var modalHeight=$(window).height() / 2 - $('#backCheckWindowModal .modal-dialog').height() / 2;
//             var modalWidth=$(window).width() / 2 - $('#backCheckWindowModal .modal-dialog').width() / 2-100;
//             $(this).find('.modal-dialog').css({
//                 'margin-top': modalHeight,
//                 'margin-left': modalWidth
//             });
//         });
//         $('#backCheckWindowModal').modal({ show: true, backdrop: 'static' });
//     }catch(e){}
// }
// //委托
// function entrustCheckWindow(){
//
//     try{
//         //居中
//         $('#entrustCheckWindowModal').on('show.bs.modal', function (e) {
//             // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
//             $(this).css('display', 'block');
//             var modalHeight=$(window).height() / 2 - $('#entrustCheckWindowModal .modal-dialog').height() / 2;
//             var modalWidth=$(window).width() / 2 - $('#entrustCheckWindowModal .modal-dialog').width() / 2-100;
//             $(this).find('.modal-dialog').css({
//                 'margin-top': modalHeight,
//                 'margin-left': modalWidth
//             });
//         });
//         $('#entrustCheckWindowModal').modal({ show: true, backdrop: 'static' });
//     }catch(e){}
// }


//弹出新窗口打开
function openCheckForm(that,index) {
    var  row =$table.bootstrapTable('getRowByIndex',index);
    var wfID= row["wf_id"];
    var dataID= row["data_id"];
    var funid= row["fun_id"];
    var wfUserid= row["wf_userid"];
    var title = row["data_title"]+"——"+row["data_no"];

    if(top.$("#navigation").length>0) openTabFrame(dataID,CONTEXT_PATH +"/iframe/check-window?funId="+funid+"&DataId="+dataID+"&wfID="+wfID+"&wfUserid="+wfUserid,title);
    else window.open(CONTEXT_PATH +"/iframe/check-window?funId="+funid+"&DataId="+dataID+"&wfID="+wfID+"&wfUserid="+wfUserid, "_blank");
}




//弹出新窗口打开查看
function openViewCheckForm(that,index) {
    var  row =$table.bootstrapTable('getRowByIndex',index);
    var wfID= row["wf_id"];
    var dataID= row["data_id"];
    var funid= row["fun_id"];
    var wfUserid= row["wf_userid"];
    var title = row["data_title"]+"——"+row["data_no"];

    if(top.$("#navigation").length>0) openTabFrame(dataID,CONTEXT_PATH +"/iframe/check-window?funId="+funid+"&DataId="+dataID+"&wfID="+wfID+"&wfUserid="+wfUserid+"&isView=1",title);
    else window.open(CONTEXT_PATH +"/iframe/check-window?funId="+funid+"&DataId="+dataID+"&wfID="+wfID+"&wfUserid="+wfUserid+"&isView=1", "_blank");
}

