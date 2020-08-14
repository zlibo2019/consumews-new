package com.garen.sys.biz;

//import java.util.Date;

import com.garen.common.MsgBean;
import com.garen.sys.entity.DtUser;

public interface ISysBiz {

	//用户登录
	DtUser login(MsgBean mbean,String account,String password);

}
