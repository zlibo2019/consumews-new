package com.garen.sys.web;

import com.garen.sys.biz.IFinCmdBiz;
import com.garen.sys.entity.FinCmd;
import org.springframework.stereotype.Controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import com.garen.common.*;
import org.springframework.web.servlet.ModelAndView;
import com.garen.sys.web.SysFilter;


@Controller
@RequestMapping("/sys")
public final class FinCmdAction extends BaseAction{

	protected static Log log = LogFactory.getLog(FinCmdAction.class);

	@Autowired
	private IFinCmdBiz iFinCmdBiz;

	@RequestMapping("/fincmd/json") 
	public ModelAndView json(PageBean<FinCmd> pb,FinCmd finCmd) {
		iFinCmdBiz.getFinCmdList(pb,finCmd);
		return Json(pb);
	}

	@RequestMapping("/fincmd/edit") 
	public ModelAndView edit(ModelAndView mav,FinCmd finCmd) {
		mav.setViewName("sys/FinCmdEdit");
		if(finCmd.getId() != null){
			finCmd=iFinCmdBiz.getFinCmd(finCmd);
		}
		mav.addObject("finCmd",finCmd);
		return mav;
	}

	@RequestMapping("/fincmd/save") 
	public ModelAndView save(FinCmd finCmd) {
		MsgBean mbean = SysFilter.getMsgBean(0, "保存成功 !");
		iFinCmdBiz.saveFinCmd(mbean,finCmd);
		return Json(mbean);
	}

	@RequestMapping("/fincmd/del") 
	public ModelAndView del(FinCmd finCmd) {
		MsgBean mbean = SysFilter.getMsgBean(0, "删除成功 !");
		iFinCmdBiz.delFinCmd(mbean,finCmd);
		return Json(mbean);
	}

}
