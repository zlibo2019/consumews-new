/**
 * 
 */

$(function(){
	$.print("hello");
	$("body").loadUI([{
		eName:"div",
		height:100,
		 
		elements:[{
			eName:"linkbutton",
			text:"登录",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({account:'admin',password:'123'});
				send("sys/login.do",str);
			}
		},{
			eName:"linkbutton",
			text:"部门获取",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({ip:20,gly:'admin',
					data:[{a:12,b:"33"},{a:34,b:"hello"}]});
				send("test/formjson.do",str);
			}
		},{
			eName:"linkbutton",
			text:"部门获取JSON参数",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({aa:'20000001',b4:10000});
				send("test/queryJson.do",{aa:'20000001',b4:10000});
			}
		},{
			eName:"linkbutton",
			text:"获取商户部门",
			cssStyle:"margin:10px;",
			onClick:function(){
				send("finweb/merch/dep/qryMerchDep.do",{aa:'20000001',b4:10000});
			}
		},{
			eName:"linkbutton",
			text:"新增部门",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({dep_name:'部门6',dep_parent:10000,dep_serial:'',lx:0,ip:'10.1.0.21',gly_no:'admin'});
				send("finweb/merch/dep/save.do",str);
			}
		},{
			eName:"linkbutton",
			text:"显示部门名称",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({dep_serial:'200005'});
				send("finweb/merch/dep/update.do",str);
			}
		},{
			eName:"linkbutton",
			text:"修改部门",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({dep_name:'部门1215',dep_serial:'200005',dep_parent:10000,lx:1,ip:'10.1.0.21',gly_no:'admin'});
				send("finweb/merch/dep/saveupd.do",str);
			}
		},{
			eName:"linkbutton",
			text:"删除部门",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({dep_serial:200010,lx:2,ip:'10.1.0.21',gly_no:'admin'});
				send("finweb/merch/dep/del.do",str);
			}
		},{
			eName:"linkbutton",
			text:"商户列表",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({dep_serial:200007,lx:2,ip:'10.1.0.21',gly_no:'admin'});
				send("finweb/base/qryMerch.do",str);
			}
		},{
			eName:"linkbutton",
			text:"新增商户",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({merchant_name:'测试商户2',fee_rate:0,merchant_account_type:0,merchant_dep:200008,lx:0,ip:'10.1.0.21',gly_no:'admin'});
				send("finweb/merch/register/save.do",str);
			}
		},{
			eName:"linkbutton",
			text:"商户展示",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({merchant_account_id:500001});
				send("finweb/merch/account/update.do",str);
			}
		},{
			eName:"linkbutton",
			text:"修改商户",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({merchant_account_id:1,merchant_name:'测试商户1111',fee_rate:0,merchant_account_type:0,merchant_dep:200008,lx:1,ip:'10.1.0.21',gly_no:'admin'});
				send("finweb/merch/account/saveupd.do",str);
			}
		},{
			eName:"linkbutton",
			text:"商户销户",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({id:2,ip:'10.1.0.21',gly_no:'admin'});
				send("finweb/merch/account/del.do",str);
			}
		},{
			eName:"linkbutton",
			text:"取款查询",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({user_no:'21000001',dep_serial:200003,user_lname:'',user_id:'',gly_no:''});
				send("finweb/acc/withdraw/filterQry.do",str);
			}
		},{
			eName:"linkbutton",
			text:"取款明细",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({gly_no:'admin'});
				send("finweb/acc/withdraw/qryWithdraw.do",str);
			}
		},{
			eName:"linkbutton",
			text:"取现金",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({account_id:5,withdraw_amt:60,gly_no:'admin',media_id:'',card_number:'',withdraw_lx:1});
				send("finweb/acc/withdraw/cashOut.do",str);
			}
		},{
			eName:"linkbutton",
			text:"查询读卡器",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({cardreader_id:'241'});
				send("finweb/acc/cardReader/qryCardReader.do",str);
			}
		},{
			eName:"linkbutton",
			text:"卡座查询",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({cardreader_id:'241'});
				send("finweb/acc/cardReader/qryCardSlot.do",str);
			}
		},{
			eName:"linkbutton",
			text:"系统参数查询",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({data:[{bh:'0010004',merchant_account_id:3,type:2},{bh:'0010004',merchant_account_id:3,type:2}],ip:'10.1.0.21',gly_no:'admin'});
				send("finweb/dev/mode/devSet.do",str);
				var str = $.toJSON({});
				send("finweb/acc/cardReader/sysKeySet.do",str);
			}
		},{
			eName:"linkbutton",
			text:"卡片参数查询",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({is_write:'0'});
				send("finweb/acc/cardReader/cardTypeSet.do",str);
			}
		},{
			eName:"linkbutton",
			text:"帐户筛选查询",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({user_no:'',dep_serial:'',identity_id:'',user_lname:''});
				send("finweb/acc/account/filterQry.do",str);
			}
		},{
			eName:"linkbutton",
			text:"帐户保存",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({data:[{account_id:'5',account_end_date:'2018-01-01',finger_enable:'1',account_state:'0'}],gly_no:'admin'});
				send("finweb/acc/account/save.do",str);
			}
		},{
			eName:"linkbutton",
			text:"卡管理筛选",
			cssStyle:"margin:10px;",
			onClick:function(){
				var str = $.toJSON({user_no:'1',dep_serial:'',identity_id:'',user_lname:''});
				send("finweb/acc/card/filterQry.do",str);
			}
		}
		 
		
		]
	},{
		eName:"fieldset",
		height:300,
		cssStyle:"border:1px solid #aaa;overflow:auto;",
		elements:[{
			eName:"legend",
			text:"返回结果"
		},{
			eName:"div",
			id:"msg_div"
		}]
	}]);
	var msgDiv = $('#msg_div');
	
	function send(url,str){
		msgDiv.html("参数:" + str + "<br>");
		postJson(url,str,function(retJson){
			if(retJson.result)
				msgDiv.append($.toJSON(retJson.obj || retJson.record));
			else 
				msgDiv.append(retJson.info);
		});
		
	}
	
	function postJson(url,params,fn){
		if(typeof params != "string")
			 params = $.toJSON(params);
		$.postEx({
			url:url,
			contentType:"application/json",
			params:params,
			onLoad:fn
		});
	}
	
});

