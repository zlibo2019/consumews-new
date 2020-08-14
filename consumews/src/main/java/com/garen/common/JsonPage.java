package com.garen.common;

import java.util.HashMap;

public class JsonPage extends MsgBean {

	private Integer total;
	
	private Object retDatas;
	
	private Object record;
	//分页统计信息：金额，记录数
	private  Object retData;//Map<String,Object> 
	//private List<Map<String,Object>> footer;

	public Object getRecord() {
		return record;
	}

	public void setRecord(Object record) {
		this.record = record;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Object getRetData() {
		return retData == null?new HashMap<String,Object>():retData;
	}

	public void setRetData(Object retData) {
		this.retData = retData;
	}

	public Object getRetDatas() {
		return retDatas;
	}

	public void setRetDatas(Object retDatas) {
		this.retDatas = retDatas;
	}

	
}
