package com.garen.sys.biz.impl;

import com.garen.sys.dao.IFinCmdDao;
import com.garen.sys.entity.FinCmd;
import com.garen.sys.biz.IFinCmdBiz;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.garen.common.*;


@Service
public class FinCmdBizImpl implements IFinCmdBiz {

	@Autowired
	private IFinCmdDao finCmdDao;

	/*
	*分页查询
	*/
	@Override
	public List<FinCmd> getFinCmdList(PageBean<FinCmd> pb,FinCmd finCmd){
		return finCmdDao.getPageList(pb,finCmd);
	}

	/*
	*查询个体
	*/
	@Override
	public FinCmd getFinCmd(FinCmd finCmd){
		return finCmdDao.get(finCmd);
	}

	/*
	*保存
	*/
	@Override
	public int saveFinCmd(MsgBean mbean,FinCmd finCmd){
		int ret = -1;
		if(finCmd.getId() == null){
			ret = finCmdDao.add(finCmd);
		}else{
			ret = finCmdDao.update(finCmd);
		}
		return ret;
	}

	/*
	*删除
	*/
	@Override
	public int delFinCmd(MsgBean mbean,FinCmd finCmd){
		return finCmdDao.del(finCmd);
	}

}
