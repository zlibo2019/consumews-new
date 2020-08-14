package com.garen.sys.biz;

import com.garen.sys.entity.FinCmd;
import java.util.List;
import com.garen.common.*;


public interface IFinCmdBiz {

	/*
	*分页查询
	*/
	List<FinCmd> getFinCmdList(PageBean<FinCmd> pb,FinCmd finCmd);

	/*
	*查询实体
	*/
	FinCmd getFinCmd(FinCmd finCmd);

	/*
	*保存
	*/
	int saveFinCmd(MsgBean mbean,FinCmd finCmd);

	/*
	*删除
	*/
	int delFinCmd(MsgBean mbean,FinCmd finCmd);

}
