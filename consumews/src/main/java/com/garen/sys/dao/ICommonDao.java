package com.garen.sys.dao;

import java.util.List;
import java.util.Map;

import com.garen.common.IBaseDao;
import com.garen.common.JsonPage;

public interface ICommonDao extends IBaseDao<Map<String, Object>> {

	
	List<Map<String,Object>> callProc(JsonPage jp, String procName,String procParams,Map<String,Object> paramMap);
	
	List<Map<String,Object>>  queryForList(JsonPage jp,String sql, Map<String, Object> paramMap);
	
}
