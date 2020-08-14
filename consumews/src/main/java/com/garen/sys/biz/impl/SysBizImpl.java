package com.garen.sys.biz.impl;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garen.common.MsgBean;
import com.garen.sys.biz.ISysBiz;
//import com.garen.sys.dao.ICommonDao;
import com.garen.sys.entity.DtUser;

@Service
public class SysBizImpl implements ISysBiz {

//	@Autowired
//	private ICommonDao commonDao;

//	private static Log log = LogFactory.getLog(SysBizImpl.class);   
	
	@Override
	public DtUser login(MsgBean mbean, String loginName,String password) {
		
		if("admin".equals(loginName) && "123".equals(password)){
			DtUser user = new DtUser();
			user.setUserLname(loginName);
			user.setUserPassword(password);
			return user;
		}else mbean.setRetInfo(-1, "帐号或密码错误!");
		return null;
	}
	
}
