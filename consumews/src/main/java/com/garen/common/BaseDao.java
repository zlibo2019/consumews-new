package com.garen.common;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class BaseDao<T> extends AbstractDao<T>{

	@Resource(name="dataSource")
	public void setDS(DataSource ds){
		setJdbcTemp(new NamedParameterJdbcTemplate(ds));
		setJdbcTemp0( new JdbcTemplate(ds));
	}
	
}
