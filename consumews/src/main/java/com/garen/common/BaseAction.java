package com.garen.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garen.sys.entity.DtUser;
import com.garen.sys.web.SysFilter;
import com.garen.utils.FileUtils;

/*
 * 加入关于用户登录的信息
 * */
public class BaseAction extends AbstractAction {

	//protected final  static String  QUERY_MAC = "query_mac";
	
	/*//获取登录用户帐号*/
	protected String getLoginName() {
		DtUser user = getSessionUser();
		return user.getUserLname();
	}
	
	//获取登录帐号
	protected DtUser getSessionUser() {
		return (DtUser)SysFilter.getSession().getAttribute(SysFilter.USER);
	}

	//json解析对象
	public static JSONObject getJSONObject(JSONObject jobj,String key) {
		if(jobj == null || key == null) return null;
		try{
			return jobj.getJSONObject(key);
		}catch(Exception ex){}
		return null;
	}
	
	//json解析数组
	protected JSONArray getJSONArray(JSONObject jobj,String key) {
		if(jobj == null || key == null) return null;
		try{
			return jobj.getJSONArray(key);
		}catch(Exception ex){}
		return null;
	}
	//解析json
	protected JSONObject parseJson(JsonPage jp) {
		InputStream in;
		try {
			in = SysFilter.getRequest().getInputStream();
			String jsonStr = new String(FileUtils.readbodydata(in, 0),"utf-8");
			return JSON.parseObject(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//解析json数据
	protected JSONArray parseJsonArray(JsonPage jp) {
		InputStream in;
		try {
			in = SysFilter.getRequest().getInputStream();
			String jsonStr = new String(FileUtils.readbodydata(in, 0),"utf-8");
			return JSON.parseArray(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	//解析json
	protected Map<String,Object> parseJsonMap(JsonPage jp) {
		JSONObject json = parseJson(jp);
		if(json != null) {
			return (Map<String,Object>)json;
		}
		return new HashMap<>();
	}
	
	//mapkey安装规整转换
	protected Map<String,Object> mapKeyTrans(Map<String,Object> map,
			Map<String,String> keyMap) {
		Map<String,Object> newMap = new HashMap<String, Object>();
		for(String key : map.keySet()){
			if(StringUtils.isEmpty(key)) continue;
			String newkey = keyMap.get(key);
			if(StringUtils.isEmpty(newkey)) newkey = key;
			newMap.put(newkey, map.get(key));
		}
		return newMap;
	}
}
