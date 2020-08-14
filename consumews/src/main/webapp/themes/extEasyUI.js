/**
 * EasyUI组件二次封装
 */
(function ($) {
	var garenUI =  garen_require("garen_ui");
	var easyUI =  garen_require("garen_easyui");
	//支持的easyui的组件
	var EasyUINames = [
	   {
		   name:'layout',//组件名称
		   tag:'div',//html组件
		   onCreate:function(jqObj,uiOpts){//回调函数
			   var factory = this;
			   var layOut = jqObj;
			   var node = null;
				//子元素
				var childNode = uiOpts.elements;
				if($.isArray(childNode) == false) return null;
				uiOpts.elements = [];
				$.each(childNode,function(i,param){
					if(param.region == undefined) return true;//继续
					if(param.region == 'west'){
						node = $.extend({width: 180, title: '布局西', split: true},param);
					}else if(param.region == 'center'){
						flag = true;
						node = $.extend({ title: '布局中'},param);
					}else{
						node = $.extend({},param);
					}
					//删除子元素
					delete node.elements;
					layOut.layout('add',node);
					//获取生成的组件
					node.jqObj = layOut.layout('panel',param.region);
					//继续创建子元素
					node.elements = factory.createUI(param.elements,node.jqObj);
					uiOpts.elements.push(node);
				});
		   }
	   },
	   {
		   name:'tabs',//组件名称
		   tag:'div',//html组件
		   onCreate:function(jqObj,uiOpts){//回调函数
			   var factory = this;
			   var mytabs = jqObj;
			   var node = null;
				//子元素
				var childNode = uiOpts.elements;
				if($.isArray(childNode) == false) return null;
				uiOpts.elements = [];
				$.each(childNode,function(i,param){
					var node = $.extend({bodyCls:'dialog_panelbody'},param);
					delete node.elements;
					if(i == 0) node['selected'] = true;
					else node['selected'] = false;
					mytabs.tabs('add',node);
					//获取生成的组件
					node.jqObj = mytabs.tabs('getTab',i);
					//继续创建子元素并更新子元素引用
					node.elements = factory.createUI(param.elements,node.jqObj);
					uiOpts.elements.push(node);
				});
		   }
	   },
	   {
		   name:'datagrid',//组件名称
		   tag:'table',//html组件
		   onCreate:function(jqObj,uiOpts){//回调函数
			   var factory = this;
			   var mygrid = jqObj;
			   var gridOpts = uiOpts.jqOpts;
			   if($.isArray(uiOpts.toolbarEx)){
				   var toolbar = {
				    	eName:'toolbar',
				    	addMode:1,
				    	elements:uiOpts.toolbarEx
				   };
				   var panel = mygrid.datagrid("getPanel");
				   panel.find('div.datagrid-toolbar').remove();//删除旧的工具栏
				   uiOpts.toolbarUI = factory.createUI(toolbar,panel);
				   mygrid.datagrid('resize');//重绘datagrid 
				   gridOpts.toolbarUI = uiOpts.toolbarUI;
				   uiOpts.toolbarEx = null;
				   gridOpts.toolbarEx = null;
			   }
			   return;//退出
		   }
	   },{
		   name:'combobox',tag:'input',
		   onCreate:function(jqObj,uiOpts){
			   var jqOpts = uiOpts.jqOpts;
			   uiOpts.val = function(){//获取当前值
					return jqObj.combobox("getValue");
				};
				uiOpts.text = function(){//获取当前值
					return jqObj.combobox("getText");
				};
				uiOpts.getData = function(){//获取当前值
					return jqObj.combobox("getData");
				}
		   }
	   },
	   {name:'textbox',tag:'input'},{name:'datebox',tag:'input'},{name:'datetimebox',tag:'input'},{name:'filebox',tag:'input'},
	   {name:'tree',tag:'ul'},
	   {name:'numberbox',tag:'input'},{name:'combo',tag:'input'},{name:'combobox',tag:'input'},{name:'switchbutton',tag:'input'},
	   {name:'combotree',tag:'input'},{name:'combogrid',tag:'input'},{name:'linkbutton',tag:'a'},
	   {name:'datetimespinner',tag:'input'},{name:'numberspinner',tag:'input'},{name:'searchbox',tag:'input'}
	];
	
	/*
	 * 创建EasyUI
	 * UIData:组件定义数据
	 * jqObj UI组件容器
	 * 步骤：
	 * 1查询定义语法，null 退出
	 * 2 创建html元素
	 * 3 创建easyui 组件
	 * 4 调用回调函数
	 * 5 完毕
	 * 返回值:
	 * 	null 创建失败
	 *  obj 返回定义options
	 */
	easyUI.regFn(function(jqObj,UIData){
		//debug();
		//查找UI定义
		var uiObj = findEasyUI(EasyUINames,UIData.eName);
		if(uiObj == null) return 0;//未找到
		var eName = UIData.eName;
		UIData.uname= eName;//复制ename
		UIData.nname = UIData.name;//复制name
		UIData.eName = uiObj.tag;
		var childNodes = UIData.elements;
		UIData.elements = null;//不包含子子节点
		var myobj = garenUI.createUI(jqObj,UIData);
		if(myobj <= 0) return -1;//创建错误
		var htmlUI = myobj.findUI();
		htmlUI.elements = childNodes;
		htmlUI.eName = eName;
		myobj.unbind();//取消所有事件绑定
		myobj[htmlUI.eName](htmlUI);//创建组件
		if(uiObj.onCreate){//创建时回调函数
			uiObj.onCreate.call(factory,jqObj,htmlUI);
		}
		return myobj;
	});
	
	//查询EasyUI组件定义
	function findEasyUI(EasyUINames,name){
		var obj = null;
		$.each(EasyUINames,function(i,ui){
			if(name == ui.name){
				obj = ui;
				return false;
			}
		});
		return obj;
	}
	
})(jQuery);

//扩展组件方法,即JQuery EasyUI组件对象都可以调用
$.fn.extend({
	updateOpt : function(uifun,newOpt){
	    try{
	    	if(this.length <= 0) return;
	        $.extend(this[uifun].call(this,'options'),newOpt);
	    }catch(err){
	        $.printLog(err);
	    }
	}
});
/*
 * date组件扩展
 */
$.extend($.fn.datebox.defaults, {
	closeText:'',//去掉日期面板文本按钮
	currentText:''
});

/*
 * combotree组件扩展
 */
$.extend($.fn.textbox.defaults, {
	//tipPosition:'top'
});

/*
 * combotree组件扩展
 */
$.extend($.fn.numberbox.defaults, {
	//tipPosition:'top'
});

/*
 * form表单扩展,flag 0 前缀无效 1 添加  2 去除
 */
$.extend($.fn.form.methods,{
	form2Json : function(sform,formjson,prefix,flag){//form表单，解析为form对象
		if(!$(sform).form('validate')) return false;
		formjson = formjson || {};
		var opt = $(sform).form('options');
		prefix = opt.prefix || '';
		flag = opt.flag || 0;
		var forms = $(sform).serializeArray();
		var newforms = {};
		$.each(forms,function(i,item){
			var key = item['name'];
			var val = $.trim(item['value']);
			if(flag == 1) key = prefix + '.' +  key;
			else if(flag == 2) 
				key = key.replace(prefix + ".","");
			if(newforms[key])  newforms[key] += ',' + val;
			else newforms[key] = val;
		});
		$.extend(formjson,newforms);
		return true;
	}
});

/*
 * Tree组件方法扩展
 */
$.extend($.fn.tree.methods,{
	//设置节点checkbox状态，但不触发事件.参数：{'node':node,'status':'check'}
	setNodeCheck:function(jq,params){
		return jq.each(function () {
			var node = params.node;
			var status = params.status;
			var nodebox = $('#' + node.domId,this).find('.tree-checkbox');
			if(status == 'check'){
				node.checked = true;
	            nodebox.addClass('tree-checkbox1');
	            nodebox.removeClass('tree-checkbox2');
			}else if(status =='uncheck'){
				nodebox.addClass('tree-checkbox0');
	          	nodebox.removeClass('tree-checkbox1');
	          	nodebox.removeClass('tree-checkbox2');
	          	node.checked = false;	
			}else if(status =='indeterminate'){
				node.checked = false;
	            nodebox.addClass('tree-checkbox2');
	            nodebox.removeClass('tree-checkbox1');
			}
		});
	},
	//设置节点select状态，但不触发事件.参数：{'node':node,'select':true or false}
	setNodeSelect:function(jq,params){
		return jq.each(function () {
			var node = params.node;
			var select = params.select;
			var nodebox = $('#' + node.domId,this);
			if(select){//选中
				//node.checked = true;
	            nodebox.addClass('tree-node-selected');
			}else{//未选中
	          	nodebox.removeClass('tree-node-selected');
	          	//node.checked = false;	
			}
		});
	},
	//获取上一个兄弟节点，空返回Null
	getPrevNode:function(jq,target){
		var nodes = null;
		var node = jq.tree('getParent',target);
		//没有父节点
		if(node == null){
			 nodes = jq.tree('getRoots');
		}else{
			nodes = jq.tree('getChildren',node.target);
		}
		node = null;
		$.each(nodes,function(i,n){
			if(n.target == target){
				return false;
			}
			node = n;
		});
		return node;
	},
	//获取下一个兄弟节点，空返回Null
	getNextNode:function(jq,target){
		var nodes = null;
		var node = jq.tree('getParent',target);
		//没有父节点
		if(node == null){
			 nodes = jq.tree('getRoots');
		}else{
			nodes = jq.tree('getChildren',node.target);
		}
		node = null;
		//$.printLog(nodes);
		var flag = false;
		$.each(nodes,function(i,n){
			if(n.target == target){
				flag = true;//设置标志，获取下一个节点
			}else if(flag){
				node = n;
				return false;
			}
		});
		return node;
	}
});

/*
 * datagrid组件默认值
 */
$.extend($.fn.datagrid.defaults, {
	fit:true,
	pagination:true,
	singleSelect:true,
	pageList:[30,60],pageSize:30,
	onBeforeLoad:function(params){
		var mygrid = $(this);
		var opts = $(this).datagrid('options');
		//首次加载页面，是否查询数据
		if(opts.autoload == false) {
			opts.autoload = true;
			return false;
		}
		//分页参数重命名
		if(params['rows']){
			params['pageSize'] = params['rows'];
			delete params['rows']; 
		}
		if(params['page']){
			params['pageNum'] = params['page'];
			delete params['page'];
		}
		//工具栏表单验证及数据封装
		var myform = opts.toolbarUI;
		if(myform){
			if(myform.form2Json(params) == false)
           	 	return false;
		}
		if(opts.onBeforeLoadEx){
			if(false == opts.onBeforeLoadEx.call(this,params)){
				$(this).datagrid('loadData',[]);//清空数据
				opts.queryParamsEx = null;//清空条件
				return false;
			}
		}
		//清空选中情况
        var data = $.data($(this).get(0),'datagrid');
        data.selectedRows = [];
        data.checkedRows = [];
        //缓存查询条件
        opts.queryParamsEx = params;
        
		return true;
	}
});



