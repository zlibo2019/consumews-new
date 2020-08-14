package com.garen.sys.biz;

import com.garen.sys.web.OnLineListener;

public interface ISrvTimer {

	//获取服务状态:true 正常 false 暂停
	boolean getSvrStatus();
	
	void setOnlineListener(OnLineListener onlineListener);
	
}
