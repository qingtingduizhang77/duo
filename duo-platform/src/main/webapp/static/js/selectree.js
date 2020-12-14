(function ($) {
    "use strict";
    //default selecttree
    var defaultOptions = {
        width: 'auto',
        height: '300px',
        isSimpleNode: true, //是否启用简单的节点属性（id,ame）,次配置是form表单传递到后台的参数值，数据通过json传递
        pIcon: '',
        cIcon: '',
        debug: false,
        data: [{
            id: '10011001',
            name: "父节点1",
            children: [
                {id: '10011001', name: "子节点1"},
                {id: '10011002', name: "子节点2"}
            ]
        }]
    }
    //define some global dom
    var SelectTree = function (element, options) {
        this.options = options;
        this.$element = $(element);
        this.$containner = $('<div></div>');
        this.$ztree = $('<ul   id="ztree" class="ztree"></ul>');
        this.$pInput = $('<input id="nodes" type="hidden"/>'); // 这里为隐藏域数据 id='nodes',选中的数都存储在这。
    }
    //prototype method
    SelectTree.prototype = {
        constructor: SelectTree,
        init: function () {
            var that = this,
                st = this.$element;
            // style
            that.$containner.css({'display': 'inline-block', 'position': 'relative'});
            that.$ztree.css({
                'position': 'absolute',
                'z-index': 10000,
                'border-radius': '3px',
                'border': '1px #ccc solid',
                'overflow': 'auto',
                'background': '#fff',
                'margin-top': '30px'
            });
            that.$ztree.css({'width': this.options.width || that.defaultOptions.width});
            that.$ztree.css({'height': this.options.height || taht.defaultOptions.height});
            // dom
            if (that.$element.data('pname')) {
                that.$pInput.attr('name', that.$element.data('pname'));
            }
            if (that.$element.attr('name')) {
                that.$element.removeAttr('name');
            }
            that.$ztree.attr('id', 'selectTree-ztree');
            that.$ztree.css('display', 'none');
            that.$element.attr('readonly', 'readonly');
            st.wrap(that.$containner);
            st.after(that.$pInput);
            st.after(that.$ztree);

            //listener
            that.$element.bind('click', function (e) {
                that.$ztree.toggle();
            });

            //ztree
            //listener
            this.options.ztree.setting.callback = {
                onCheck: function (event, treeId, treeNode) {
                    return that._onSelectTreeCheck(event, treeId, treeNode);
                }
            }
            $.fn.zTree.init(that.$ztree, this.options.ztree.setting, this.options.ztree.data);

            // this 这个东西啊你理解成 它是当前选中的数据 赋给 selectttt ，aaaa 这是一个function 往下看
            aaaa.selectttt(this);
        },
        _onSelectTreeCheck: function (event, treeId, treeNode) {
            var that = this;
            //获得所有选中节点
            var pValue = '',
                text = '';
            var treeObj = $.fn.zTree.getZTreeObj(that.$ztree.attr('id'));
            console.log(treeObj)
            if (treeObj) {
                var nodes = treeObj.getCheckedNodes(true);
                if (this.options.debug) {
                    console.log("选中的节点：");
                    console.log(nodes);
                }
                for (var i = 0; i < nodes.length; i++) {
                    if (nodes[i].isParent) {
                        text = text + nodes[i].name + '：';
                    } else {
                        text = text + nodes[i].name + '，';
                    }
                }
                if (this.options.isSimpleNode) {
                    nodes = common._transformToSimpleNodes(nodes);
                }
                if (this.options.debug) {
                    console.log("提交到表单的数据结构：");
                    console.log(JSON.stringify(nodes));
                }
                // nodes数据 从这赋给隐藏域input(id='nodes')
                that.$pInput.val(JSON.stringify(nodes));
                text = text ? text.substr(0, text.length - 1) : '';
                that.$element.val(text);
                that.$element.attr('title', text);
            }
        }
    }
    var common = {
        //这里组织默认参数，用户传过来的参数,ztree的一些固定参数
        _getSelectTreeOptions: function (options) {
            options = options ? options : {};
            return {
                width: options.width || defaultOptions.width,
                height: options.height || defaultOptions.height,
                isSimpleNode: options.isSimpleNode || defaultOptions.isSimpleNode,
                pIcon: options.pIcon || defaultOptions.pIcon,
                cIcon: options.cIcon || defaultOptions.cIcon,
                debug: options.debug || defaultOptions.debug,
                ztree: {
                    data: options.data || defaultOptions.data,
                    setting: {
                        check: {
                            enable: true,
                            chkStyle: "checkbox",
                            chkboxType: {"Y": "ps", "N": "ps"}
                        }
                    }
                }
            }
        },
        //转换ztree为简单的节点，只包含id,name
        _transformToSimpleNodes: function (nodes) {
            var newNodes = [];
            if (nodes instanceof Array) {
                for (var i = 0; i < nodes.length; i++) {
                    var pNode = nodes[i].getParentNode();
                    var node = {};
                    node.pId = pNode ? pNode.id : null;
                    node.id = nodes[i].id;
                    node.name = nodes[i].name;
                    newNodes.push(node);
                }
            }
            return newNodes;
        }
    }
    $.fn.selectTree = function (options) {
        var data = new SelectTree(this, common._getSelectTreeOptions(options));
        return data.init();
    }
    $.fn.selectTree.Constructor = SelectTree;

    // 为什么这有一个aaaa？他的作用是用来回显你当前选中的数据，回显展示在input输入框内 ，这个是需求问题，如果不需要回显，可以忽略这段代码
    var aaaa ={
        selectttt:function (e) {
            debugger
            var that = e;
            //获得所有选中节点
            var pValue = '',
                text = '';
            var treeObj = $.fn.zTree.getZTreeObj(that.$ztree.attr('id'));
            console.log(treeObj)
            if (treeObj) {
                var nodes = treeObj.getCheckedNodes(true);
                if (that.options.debug) {
                    console.log("选中的节点：");
                    console.log(nodes);
                }
                for (var i = 0; i < nodes.length; i++) {
                    if (nodes[i].isParent) {
                        text = text + nodes[i].name + '：';
                    } else {
                        text = text + nodes[i].name + '，';
                    }
                }
                if (that.options.isSimpleNode) {
                    nodes = common._transformToSimpleNodes(nodes);
                }
                if (that.options.debug) {
                    console.log("提交到表单的数据结构：");
                    console.log(JSON.stringify(nodes));
                }
                that.$pInput.val(JSON.stringify(nodes));
                text = text ? text.substr(0, text.length - 1) : '';
                that.$element.val(text);
                that.$element.attr('title', text);
            }
        }
    }
})(jQuery)