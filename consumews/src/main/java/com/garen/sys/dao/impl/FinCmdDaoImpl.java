package com.garen.sys.dao.impl;

import com.garen.sys.dao.IFinCmdDao;
import com.garen.sys.entity.FinCmd;
import com.garen.common.BaseDao;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;


@Repository
public class FinCmdDaoImpl extends BaseDao<FinCmd> implements IFinCmdDao {

	@PostConstruct
	private void init(){
		initOrm(FinCmd.class);
	}
	
}
