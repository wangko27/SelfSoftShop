package net.shopxx.action.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.entity.Area;
import net.shopxx.service.AreaService;
import net.shopxx.util.JsonUtil;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 后台Action类 - 地区
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXAFBAC38E9CCED8343FEA91C4E57AB4B7
 * ============================================================================
 */

@ParentPackage("shop")
public class AreaAction extends BaseShopAction {

	private static final long serialVersionUID = 1321099291073671591L;

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	// 根据ID获取地区
	@InputConfig(resultName = "ajaxError")
	public String ajaxArea() {
		List<Area> areaList = areaService.getAreaList(id);
		List<Map<String, String>> optionList = new ArrayList<Map<String, String>>();
		for (Area area : areaList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", area.getName());
			map.put("value", area.getId());
			optionList.add(map);
		}
		return ajax(JsonUtil.toJson(optionList));
	}

}