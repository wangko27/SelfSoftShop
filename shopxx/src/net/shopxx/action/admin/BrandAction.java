package net.shopxx.action.admin;

import java.io.File;

import javax.annotation.Resource;

import net.shopxx.entity.Brand;
import net.shopxx.service.BrandService;
import net.shopxx.service.CacheService;
import net.shopxx.util.ImageUtil;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 品牌
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX1DD6D6F9E9EE16D448DC6DB3F4425D5E
 * ============================================================================
 */

@ParentPackage("admin")
public class BrandAction extends BaseAdminAction {

	private static final long serialVersionUID = 1341979251224008699L;
	
	private Brand brand;
	private File logo;

	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		brand = brandService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = brandService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		brandService.delete(ids);
		
		cacheService.flushGoodsListPageCache(getRequest());
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "brand.name", message = "品牌名称不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "brand.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (logo != null) {
			String logoPath = ImageUtil.copyImageFile(getServletContext(), logo);
			brand.setLogoPath(logoPath);
		} else {
			brand.setLogoPath(null);
		}
		brandService.save(brand);
		redirectUrl = "brand!list.action";
		return SUCCESS;
	}
	
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "brand.name", message = "品牌名称不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "brand.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Brand persistent = brandService.load(id);
		if (logo != null) {
			String logoPath = ImageUtil.copyImageFile(getServletContext(), logo);
			persistent.setLogoPath(logoPath);
		}
		BeanUtils.copyProperties(brand, persistent, new String[]{"id", "createDate", "modifyDate", "logoPath", "goodsSet", "goodsTypeSet"});
		brandService.update(persistent);
		
		redirectUrl = "brand!list.action";
		cacheService.flushGoodsListPageCache(getRequest());
		return SUCCESS;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

}