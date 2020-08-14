package com.garen.sys.web;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.garen.common.BaseAction;
import com.garen.common.JsonPage;
import com.garen.sys.dao.ICommonDao;

@Controller  
@RequestMapping("/sys")  
public final class MenuAction extends BaseAction {

	/**
	 * sys-菜单和权限查询
	 * 操作:获取单个菜单权限
	 * 方法：menuGet
	 * 输入、输出参数均为JSON格式
	 */
	protected static Log log = LogFactory.getLog(SysAction.class);   

	@Autowired
	private ICommonDao commonDao;
	
	/*
	 *获取单个菜单权限信息
	 */
	@RequestMapping("/menu/get")
	public ModelAndView menuGet(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String sql = "select a.menu_bh,a.menu_set,c.menu_lname ,c.menu_path"
				+ " from wt_gly_menu a,wt_gly_module b,WT_MENU_LIST c"
				+ " where a.parent_bh=b.bh and b.Module_id=c.Module_id and b.module_id='0043' and a.Menu_bh=c.Bh"
				+ " and b.group_no=(select Gly_group from wt_gly where Gly_no=:gly_no)"
				+ " and b.lx=1 and a.lx=0 and a.Menu_bh=:menu_bh ";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}
}
