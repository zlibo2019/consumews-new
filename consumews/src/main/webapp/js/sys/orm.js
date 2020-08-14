

$(function(){
	
	//点击事件
	$(".divTable").click(function(){
		$(this).toggleClass('divTableFocus');
	});
	
	//点击事件
	$(".divPackage").click(bagClick);
	
	
	//包点击事件
	function bagClick(){
		var thisBag = this;
		$(".divPackage").each(function(){
			if(this == thisBag)
				$(this).addClass('divPackageFocus');
			else
				$(this).removeClass('divPackageFocus');
		});
	}
	
	//添加包
	$('#btn_addBag').click(function(){
		//$("#myBag").removeClass('divPackageFocus');
		var newbag = $("#myBag").clone();
		newbag.removeAttr('id');
		newbag.find("td:last").html('');
		newbag.removeClass('divPackageFocus');
		newbag.click(bagClick);
		$("#myBag").after(newbag);
	});
	
	//删除包
	$('#btn_delBag').click(function(){
		if($(".divPackageFocus").length < 1 ||
				$(".divPackageFocus").prop('id') == 'myBag') return;
		$.confirm( '确定要删除此包吗?', function(r){
            if (r){
            	//复制表
            	$('.divPackageFocus').find('.divTableFocus')
						.appendTo($("#srcTable"));
            	
            	$(".divPackageFocus").remove();
            }
         });
	});
	
	//检索框
	$("#table_search").searchbox({
		prompt:'输入表名进行过滤',
		searcher:function(q){
			var select = $('#srcTable').find('div:contains('+q+')');
			$('#srcTable').find('div').removeClass('divTable').hide();
			select.addClass('divTable').show();
		}
	});
	
	//全选
	$('#btn_allTable').click(function(){
		//$.printLog($("#table_search").searchbox('options'));
		$('#srcTable').find('.divTable').addClass('divTableFocus');
	});
	
	//取反
	$('#btn_notAllTable').click(function(){
		var select = $('#srcTable').find('.divTableFocus');
		$('#srcTable').find('.divTable').addClass('divTableFocus');
		select.removeClass('divTableFocus');
	});
	
	
	//
	$('#left2right').click(function(){
		$('#srcTable').find('.divTableFocus')
		.appendTo($(".divPackageFocus").find('td:last'));
		
	});
	
	
	//
	$('#right2left').click(function(){
		
		$('.divPackageFocus').find('.divTableFocus')
				.appendTo($("#srcTable"));
	});
	
	//导出
	$('#btn_expBag').click(function(){
		var bags = [];
		var savePath = $('#txt_savepath').val();
		$(".divPackage").each(function(){
			
			var packName = $(this).find('input').val();
			if(packName.length < 2){
				$.alert('包名不合 !');
				bags = null;//清空
				return false;
			}
			
			//遍历表
			$(this).find('.divTable').each(function(){
				var bag = {};
				bag.packName = packName;
				bag.savePath = savePath;
				bag.tableName = $(this).text();
				bags.push(bag);
			});
		});
		if(bags != null && bags.length > 0){
			var param = {'json':$.toJSON(bags)};
			$.post('convert.do',param,function(retJson){
				$.alert(retJson['info']);
			},"json");
		}else{
			$.alert('包不能为空 !');
		}
	});
	
}
);