package net.shopxx.action.admin;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.entity.Goods;
import net.shopxx.entity.GoodsCategory;
import net.shopxx.entity.GoodsType;
import net.shopxx.service.CacheService;
import net.shopxx.service.GoodsCategoryService;
import net.shopxx.service.GoodsTypeService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商品分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXF8D13DDE8B51433D52B3F73D96C223F8
 * ============================================================================
 */

@ParentPackage("admin")
public class GoodsCategoryAction extends BaseAdminAction {

	private static final long serialVersionUID = 3066159260207583127L;
	
	private String parentId;
	private GoodsCategory goodsCategory;

	@Resource(name = "goodsCategoryServiceImpl")
	private GoodsCategoryService goodsCategoryService;
	@Resource(name = "goodsTypeServiceImpl")
	private GoodsTypeService goodsTypeService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// 是否已存在标识 ajax验证
	public String checkSign() {
		String oldSign = getParameter("oldValue");
		String newSign = goodsCategory.getSign();
		if (goodsCategoryService.isUniqueBySign(oldSign, newSign)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		goodsCategory = goodsCategoryService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		return LIST;
	}

	// 删除
	public String delete() {
		GoodsCategory goodsCategory = goodsCategoryService.load(id);
		Set<GoodsCategory> childrenGoodsCategorySet = goodsCategory.getChildren();
		redirectUrl = "goods_category!list.action";
		if (childrenGoodsCategorySet != null && childrenGoodsCategorySet.size() > 0) {
			addActionError("此商品分类存在下级分类,删除失败!");
			return ERROR;
		}
		Set<Goods> goodsSet = goodsCategory.getGoodsSet();
		if (goodsSet != null && goodsSet.size() > 0) {
			addActionError("此商品分类下存在商品,删除失败!");
			return ERROR;
		}
		goodsCategoryService.delete(id);
		logInfo = "删除商品分类: " + goodsCategory.getName();
		
		cacheService.flushGoodsListPageCache(getRequest());
		
		return SUCCESS;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "goodsCategory.name", message = "分类名称不允许为空!"),
			@RequiredStringValidator(fieldName = "goodsCategory.sign", message = "标识不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "goodsCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		},
		regexFields = { 
			@RegexFieldValidator(fieldName = "goodsCategory.sign", expression = "^\\w+$", message = "标识只允许包含英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (goodsCategoryService.isExistBySign(goodsCategory.getSign())) {
			addActionError("标识已存在!");
			return ERROR;
		}
		if (StringUtils.isNotEmpty(parentId)) {
			GoodsCategory parent = goodsCategoryService.load(parentId);
			goodsCategory.setParent(parent);
		} else {
			goodsCategory.setParent(null);
		}
		if (StringUtils.isEmpty(goodsCategory.getGoodsType().getId())) {
			goodsCategory.setGoodsType(null);
		}
		goodsCategoryService.save(goodsCategory);
		logInfo = "添加商品分类: " + goodsCategory.getName();
		
		cacheService.flushGoodsListPageCache(getRequest());
		
		redirectUrl = "goods_category!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "goodsCategory.name", message = "分类名称不允许为空!"),
			@RequiredStringValidator(fieldName = "goodsCategory.sign", message = "标识不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "goodsCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		},
		regexFields = { 
			@RegexFieldValidator(fieldName = "goodsCategory.sign", expression = "^\\w+$", message = "标识只允许包含英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		GoodsCategory persistent = goodsCategoryService.load(id);
		if (!goodsCategoryService.isUniqueBySign(persistent.getSign(), goodsCategory.getSign())) {
			addActionError("标识已存在!");
			return ERROR;
		}
		if (StringUtils.isEmpty(goodsCategory.getGoodsType().getId())) {
			goodsCategory.setGoodsType(null);
		}
		BeanUtils.copyProperties(goodsCategory, persistent, new String[]{"id", "createDate", "modifyDate", "path", "parent", "children", "goodsSet"});
		goodsCategoryService.update(persistent);
		logInfo = "更新商品分类: " + goodsCategory.getName();
		
		cacheService.flushGoodsListPageCache(getRequest());
		
		redirectUrl = "goods_category!list.action";
		return SUCCESS;
	}
	
	// 获取商品分类树
	public List<GoodsCategory> getGoodsCategoryTreeList() {
		return goodsCategoryService.getGoodsCategoryTreeList();
	}
	
	// 获取所有商品类型集合
	public List<GoodsType> getAllGoodsTypeList() {
		return goodsTypeService.getAllList();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public GoodsCategory getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(GoodsCategory goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

}