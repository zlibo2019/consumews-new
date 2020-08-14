package com.garen.sys.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.stereotype.Repository;

import com.garen.common.BaseDao;
import com.garen.common.JsonPage;
import com.garen.sys.dao.ICommonDao;

/*通用语句*/
@Repository
public class CommonDaoImpl extends BaseDao<Map<String, Object>> implements ICommonDao {

	@PostConstruct
	private void init(){
		initOrm(Map.class);
	}

	public List<Map<String,Object>>  queryForList(JsonPage jp,String sql, Map<String, Object> paramMap){
		List<Map<String,Object>> maplist = queryForList(sql, paramMap);
		jp.setRecord(maplist);
		jp.setTotal(maplist.size());
		return maplist;
	}

	/*
	 * 调用存储过程
	 * procParams格式:
	 * @p1,$p2,#p3
	 * @表示输入参数，且不能为空
	 * $表示输入参数,可以为空
	 * #表示输出参数
	 * 返回值:为查询集合
	 * 若参数含有输出参数，则值存在paramMap中，
	 * 若输出参数为total,则同时存入JsonPage jp字段total中
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String,Object>> callProc(final JsonPage jp,final String procName,
				String procParams,final Map<String,Object> paramMap){
		String key = null,newParams = "",params1 = null,name = null;
		final List<String> outList = new ArrayList<>(),pList = new ArrayList<>();
		//参数验证
		for(String p : procParams.split(",")){
			name = null;
			key = null;
			String str = "?";
			if(p.startsWith("@")){
				key = p.replaceFirst("@", "");
				if(paramMap.containsKey(key) == false){
					jp.setRetInfo(-1, key + "参数不能为空");
					return null;
				}
				name = ":" + key;
			}else if(p.startsWith("#")) {//输出参数
				key = p.replaceFirst("#", "");
				name =  ":" + key;
				outList.add(key);
			}else if(p.startsWith("$")){//可选
				key = p.replaceFirst("\\$", "");
				if(paramMap.containsKey(key)){
					name =  ":" + key;
				}else {
					name = "null";
					str = "null";
					key = null;//参数为空
				}
			}else{
				name = p;
				str = p;
			}
			if(key != null) pList.add(key);
			if("".equals(newParams)) {
				newParams = name;
				params1 = str;
			}else {
				newParams += "," + name;
				params1 += "," + str;
			}
		}
		List<Map<String,Object>> maplist = null;
		if(outList.isEmpty()){
			StringBuilder sb = new StringBuilder(" exec " + procName +" "+ newParams);
			maplist = queryForList(sb.toString(), paramMap);
		}else{
			final String params = params1;
			maplist = (List<Map<String, Object>>) getJdbcTemp0().execute(
			     new CallableStatementCreator() {
			        public CallableStatement createCallableStatement(Connection con) throws SQLException {
			        	String sql = " exec " + procName +" "+ params;
			        	log.debug(sql);
			        	CallableStatement cs = con.prepareCall(sql);
			        	int i = 1;
			        	for(String key : pList){//保持参数顺序
			        		if(outList.contains(key)){
			        			cs.registerOutParameter(i,Types.VARCHAR);
			        		}else
			        			cs.setObject(i, paramMap.get(key));
			        		i++;
			        	}
			            return cs;
			        }
			     }, new CallableStatementCallback() {
			        public Object doInCallableStatement(CallableStatement cs) throws SQLException,DataAccessException {
			        	cs.execute();
			        	//结果集转换
			        	List<Map<String,Object>> mapList = resultSet2ListMap(cs.getResultSet());
			        	int j = 1;
			        	for(String k : pList){//保持参数顺序
			        		if(outList.contains(k)){
			        			Object obj = null;
			        			if("total".equals(k)){
			        				obj = cs.getInt(j);
			        				jp.setTotal((Integer)obj);
			        			}else{
			        				obj = cs.getString(j);
			        			}
			        			paramMap.put(k, obj);
			        		}
			        		j++;
			        	}
				        return  mapList;
			        }
			  });
		}
		jp.setRecord(maplist);
		if(jp.getTotal() == null){
			jp.setTotal(maplist.size());
		}
		return maplist;
	}

	static List<Map<String,Object>> resultSet2ListMap(ResultSet rs ) throws SQLException{
		List<Map<String,Object>> mapList = new ArrayList<>();
		if(rs == null) return mapList;
    	ResultSetMetaData meta = rs.getMetaData();
    	int colCount = meta.getColumnCount();
    	Set<String> colset = new HashSet<>();
    	for(int i = 1;i <= colCount;i++){//获取所有列
    		colset.add(meta.getColumnName(i));
    	}
    	while(rs.next()){
    		Map<String,Object> map = new HashMap<String, Object>();
    		for(String k : colset){
    			map.put(k,rs.getObject(k));
    		}
    		mapList.add(map);
    	}
    	return mapList;
	}
}
