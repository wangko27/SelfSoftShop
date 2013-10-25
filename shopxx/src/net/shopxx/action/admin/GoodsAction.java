package net.shopxx.action.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.bean.GoodsImage;
import net.shopxx.bean.GoodsParameter;
import net.shopxx.bean.SpecificationValue;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Goods;
import net.shopxx.entity.GoodsAttribute;
import net.shopxx.entity.GoodsCategory;
import net.shopxx.entity.GoodsType;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Product;
import net.shopxx.entity.Specification;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.service.BrandService;
import net.shopxx.service.CacheService;
import net.shopxx.service.GoodsCategoryService;
import net.shopxx.service.GoodsImageService;
import net.shopxx.service.GoodsService;
import net.shopxx.service.GoodsTypeService;
import net.shopxx.service.ProductService;
import net.shopxx.service.SpecificationService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商品
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX483FBE0A69F1A3F1D081F3F2D838E89C
 * ============================================================================
 */

@ParentPackage("admin")
public class GoodsAction extends BaseAdminAction {

	private static final long serialVersionUID = -4433964283757192334L;

	private String brandId;
	private String goodsTypeId;
	
	private Goods goods;
	private Product defaultProduct;
	private List<GoodsImage> goodsImageList;
	private List<File> goodsImageFileList;
	private String[] specificationIds;
	private List<Product> productList;
	private List<String[]> specificationValueIdsList; 
	private Map<String, String> goodsAttributeValueMap;
	private Map<String, String> goodsParameterValueMap;

	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	@Resource(name = "goodsCategoryServiceImpl")
	private GoodsCategoryService goodsCategoryService;
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	@Resource(name = "goodsTypeServiceImpl")
	private GoodsTypeService goodsTypeService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "goodsImageServiceImpl")
	private GoodsImageService goodsImageService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// AJAX唯一验证
	public String checkGoodsSn() {
		String oldGoodsSn = getParameter("oldValue");
		String newGoodsSn = goods.getGoodsSn();
		if (goodsService.isUniqueByGoodsSn(oldGoodsSn, newGoodsSn)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
	
	// AJAX唯一验证
	public String checkProductSn() {
		String oldProductSn = getParameter("oldValue");
		String newProductSn = defaultProduct.getProductSn();
		if (productService.isUniqueByProductSn(oldProductSn, newProductSn)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
	
	// AJAX获取商品属性
	@InputConfig(resultName = "ajaxError")
	public String ajaxGoodsAttribute() {
		GoodsType goodsType = goodsTypeService.load(id);
		Set<GoodsAttribute> goodsAttributeSet = goodsType.getGoodsAttributeSet();
		return ajax(goodsAttributeSet);
	}
	
	// AJAX获取商品参数
	@InputConfig(resultName = "ajaxError")
	public String ajaxGoodsParameter() {
		GoodsType goodsType = goodsTypeService.load(id);
		List<GoodsParameter> goodsParameterList = goodsType.getGoodsParameterList();
		return ajax(goodsParameterList);
	}
	
	// AJAX获取商品规格值
	@InputConfig(resultName = "ajaxError")
	public String ajaxSpecification() {
		Specification specification = specificationService.load(id);
		return ajax(specification);
	}
	
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		goods = goodsService.load(id);
		defaultProduct = goods.getDefaultProduct();
		return INPUT;
	}

	// 列表
	public String list() {
		pager = goodsService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception {
		StringBuffer logInfoStringBuffer = new StringBuffer("删除商品: ");
		for (String id : ids) {
			Goods goods = goodsService.load(id);
			Set<Product> productSet = goods.getProductSet();
			for (Product product : productSet) {
				Set<OrderItem> orderItemSet = product.getOrderItemSet();
				for (OrderItem orderItem : orderItemSet) {
					if (orderItem.getOrder().getOrderStatus() != OrderStatus.completed && orderItem.getOrder().getOrderStatus() != OrderStatus.invalid) {
						return ajax(Status.error, "商品[" + goods.getName() + "]订单处理未完成,删除失败!");
					}
				}
			}
			logInfoStringBuffer.append(goods.getName() + " ");
		}
		goodsService.delete(ids);
		logInfo = logInfoStringBuffer.toString();
		
		cacheService.flushGoodsListPageCache(getRequest());
		
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "goods.name", message = "商品名称不允许为空!"),
			@RequiredStringValidator(fieldName = "goods.goodsCategory.id", message = "商品分类不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "goods.isMarketable", message = "是否上架不允许为空!"),
			@RequiredFieldValidator(fieldName = "goods.isSpecificationEnabled", message = "是否开启商品规格不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isNotEmpty(goods.getGoodsSn()) && goodsService.isExistByGoodsSn(goods.getGoodsSn())) {
			addActionError("商品编号重复,请重新输入!");
			return ERROR;
		}
		
		if (StringUtils.isNotEmpty(brandId)) {
			Brand brand = brandService.load(brandId);
			goods.setBrand(brand);
		} else {
			goods.setBrand(null);
		}
		
		if (goodsImageList != null) {
			for (int i = 0; i < goodsImageList.size(); i ++) {
				GoodsImage goodsImage = goodsImageList.get(i);
				if (goodsImage == null) {
					continue;
				}
				File goodsImageFile = goodsImageFileList.get(i);
				GoodsImage g = goodsImageService.buildGoodsImage(goodsImageFile);
				goodsImage.setId(g.getId());
				goodsImage.setPath(g.getPath());
				goodsImage.setSourceImageFormatName(g.getSourceImageFormatName());
			}
			
			for (Iterator<GoodsImage> iterator = goodsImageList.iterator(); iterator.hasNext();) {
				GoodsImage goodsImage = iterator.next();
				if (goodsImage == null) {
					iterator.remove();
				}
			}
			goods.setGoodsImageList(goodsImageList);
		} else {
			goods.setGoodsImageList(null);
		}
		
		if (goods.getIsSpecificationEnabled()) {
			for (String specificationId : specificationIds) {
				Specification specification = specificationService.load(specificationId);
				goods.getSpecificationSet().add(specification);
			}
		} else {
			goods.setSpecificationSet(null);
		}
		
		if (StringUtils.isNotEmpty(goodsTypeId)) {
			GoodsType goodsType = goodsTypeService.load(goodsTypeId);
			
			Set<GoodsAttribute> goodsAttributeSet = goodsType.getGoodsAttributeSet();
			if (goodsAttributeSet != null) {
				for (GoodsAttribute goodsAttribute : goodsAttributeSet) {
					String goodsAttributeValue = goodsAttributeValueMap.get(goodsAttribute.getId());
					if (StringUtils.isNotEmpty(goodsAttributeValue)) {
						goods.setGoodsAttributeValue(goodsAttribute, goodsAttributeValue);
					}
				}
			}
			
			Map<String, String> destGoodsParameterValueMap = new HashMap<String, String>();
			List<GoodsParameter> goodsParameterList = goodsType.getGoodsParameterList();
			if (goodsParameterList != null) {
				for (GoodsParameter goodsParameter : goodsParameterList) {
					String goodsParameterValue = goodsParameterValueMap.get(goodsParameter.getId());
					destGoodsParameterValueMap.put(goodsParameter.getId(), goodsParameterValue);
				}
			}
			
			goods.setGoodsType(goodsType);
			goods.setGoodsParameterValueMap(destGoodsParameterValueMap);
		} else {
			goods.setGoodsType(null);
			goods.setGoodsAttributeValueToNull();
			goods.setGoodsParameterValueMap(null);
		}
		
		goodsService.save(goods);
		logInfo = "添加商品: " + goods.getName();
		
		if (goods.getIsSpecificationEnabled()) {
			for (int i = 0; i < productList.size(); i ++) {
				Product product = productList.get(i);
				if (product == null) {
					continue;
				}
				
				List<SpecificationValue> specificationValueList = new ArrayList<SpecificationValue>();
				String[] specificationValueIds = specificationValueIdsList.get(i);
				for (String specificationId : specificationIds) {
					for (String specificationValueId : specificationValueIds) {
						SpecificationValue specificationValue = specificationService.getSpecificationValue(specificationId, specificationValueId);
						if (specificationValue != null) {
							specificationValueList.add(specificationValue);
							break;
						}
					}
				}
				
				product.setGoods(goods);
				product.setSpecificationValueList(specificationValueList);
				productService.save(product);
			}
		} else {
			defaultProduct.setGoods(goods);
			defaultProduct.setSpecificationValueList(null);
			productService.save(defaultProduct);
		}
		
		cacheService.flushGoodsListPageCache(getRequest());
		
		redirectUrl = "goods!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "goods.name", message = "商品名称不允许为空!"),
			@RequiredStringValidator(fieldName = "goods.goodsCategory.id", message = "商品分类不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "goods.isMarketable", message = "是否上架不允许为空!"),
			@RequiredFieldValidator(fieldName = "goods.isSpecificationEnabled", message = "是否开启商品规格不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Goods persistent = goodsService.load(id);
		
		if (StringUtils.isNotEmpty(goods.getGoodsSn()) && !goodsService.isUniqueByGoodsSn(persistent.getGoodsSn(), goods.getGoodsSn())) {
			addActionError("商品编号重复,请重新输入!");
			return ERROR;
		}
		
		if (goods.getIsSpecificationEnabled()) {
			if (persistent.getIsSpecificationEnabled()) {
				for (Product product : persistent.getProductSet()) {
					if(!productList.contains(product)) {
						Set<OrderItem> orderItemSet = product.getOrderItemSet();
						for (OrderItem orderItem : orderItemSet) {
							if (orderItem.getOrder().getOrderStatus() != OrderStatus.completed && orderItem.getOrder().getOrderStatus() != OrderStatus.invalid) {
								addActionError("货品[" + product.getName() + "]订单处理未完成,删除失败!");
								return ERROR;
							}
						}
					}
				}
			} else {
				for (Product product : persistent.getProductSet()) {
					Set<OrderItem> orderItemSet = product.getOrderItemSet();
					for (OrderItem orderItem : orderItemSet) {
						if (orderItem.getOrder().getOrderStatus() != OrderStatus.completed && orderItem.getOrder().getOrderStatus() != OrderStatus.invalid) {
							addActionError("货品[" + product.getName() + "]订单处理未完成,删除失败!");
							return ERROR;
						}
					}
				}
			}
		} else {
			if (persistent.getIsSpecificationEnabled()) {
				for (Product product : persistent.getProductSet()) {
					Set<OrderItem> orderItemSet = product.getOrderItemSet();
					for (OrderItem orderItem : orderItemSet) {
						if (orderItem.getOrder().getOrderStatus() != OrderStatus.completed && orderItem.getOrder().getOrderStatus() != OrderStatus.invalid) {
							addActionError("货品[" + product.getName() + "]订单处理未完成,删除失败!");
							return ERROR;
						}
					}
				}
			}
		}
		
		if (StringUtils.isNotEmpty(brandId)) {
			Brand brand = brandService.load(brandId);
			goods.setBrand(brand);
		} else {
			goods.setBrand(null);
		}
		
		if (goodsImageList != null) {
			for (int i = 0; i < goodsImageList.size(); i ++) {
				GoodsImage goodsImage = goodsImageList.get(i);
				if (goodsImage == null) {
					continue;
				}
				if (StringUtils.isEmpty(goodsImage.getId())) {
					File goodsImageFile = goodsImageFileList.get(i);
					GoodsImage g = goodsImageService.buildGoodsImage(goodsImageFile);
					goodsImage.setId(g.getId());
					goodsImage.setPath(g.getPath());
					goodsImage.setSourceImageFormatName(g.getSourceImageFormatName());
				}
			}
			
			for (Iterator<GoodsImage> iterator = goodsImageList.iterator(); iterator.hasNext();) {
				GoodsImage goodsImage = iterator.next();
				if (goodsImage == null) {
					iterator.remove();
				}
			}
			goods.setGoodsImageList(goodsImageList);
		} else {
			goods.setGoodsImageList(null);
		}
		
		if (goods.getIsSpecificationEnabled()) {
			for (String specificationId : specificationIds) {
				Specification specification = specificationService.load(specificationId);
				goods.getSpecificationSet().add(specification);
			}
		} else {
			goods.setSpecificationSet(null);
		}
		
		if (StringUtils.isNotEmpty(goodsTypeId)) {
			GoodsType goodsType = goodsTypeService.load(goodsTypeId);
			
			Set<GoodsAttribute> goodsAttributeSet = goodsType.getGoodsAttributeSet();
			if (goodsAttributeSet != null) {
				for (GoodsAttribute goodsAttribute : goodsAttributeSet) {
					String goodsAttributeValue = goodsAttributeValueMap.get(goodsAttribute.getId());
					if (StringUtils.isNotEmpty(goodsAttributeValue)) {
						goods.setGoodsAttributeValue(goodsAttribute, goodsAttributeValue);
					}
				}
			}
			
			Map<String, String> destGoodsParameterValueMap = new HashMap<String, String>();
			List<GoodsParameter> goodsParameterList = goodsType.getGoodsParameterList();
			if (goodsParameterList != null) {
				for (GoodsParameter goodsParameter : goodsParameterList) {
					String goodsParameterValue = goodsParameterValueMap.get(goodsParameter.getId());
					destGoodsParameterValueMap.put(goodsParameter.getId(), goodsParameterValue);
				}
			}
			
			goods.setGoodsType(goodsType);
			goods.setGoodsParameterValueMap(destGoodsParameterValueMap);
		} else {
			goods.setGoodsType(null);
			goods.setGoodsAttributeValueToNull();
			goods.setGoodsParameterValueMap(null);
		}
		
		if (goods.getIsSpecificationEnabled()) {
			if (persistent.getIsSpecificationEnabled()) {
				for (Product product : persistent.getProductSet()) {
					if(!productList.contains(product)) {
						productService.delete(product);
					}
				}
			} else {
				for (Product product : persistent.getProductSet()) {
					productService.delete(product);
				}
			}
		} else {
			if (persistent.getIsSpecificationEnabled()) {
				for (Product product : persistent.getProductSet()) {
					productService.delete(product);
				}
			}
		}
		
		BeanUtils.copyProperties(goods, persistent, new String[] {"id", "createDate", "modifyDate", "freezeStore", "productSet"});
		goodsService.update(persistent);
		logInfo = "编辑商品: " + goods.getName();
		
		if (goods.getIsSpecificationEnabled()) {
			for (int i = 0; i < productList.size(); i ++) {
				Product product = productList.get(i);
				if (product == null) {
					continue;
				}
				
				List<SpecificationValue> specificationValueList = new ArrayList<SpecificationValue>();
				String[] specificationValueIds = specificationValueIdsList.get(i);
				for (String specificationId : specificationIds) {
					for (String specificationValueId : specificationValueIds) {
						SpecificationValue specificationValue = specificationService.getSpecificationValue(specificationId, specificationValueId);
						if (specificationValue != null) {
							specificationValueList.add(specificationValue);
							break;
						}
					}
				}
				
				product.setGoods(persistent);
				product.setSpecificationValueList(specificationValueList);
				
				if (StringUtils.isEmpty(product.getId())) {
					productService.save(product);
				} else {
					Product persistentProduct = productService.load(product.getId());
					BeanUtils.copyProperties(product, persistentProduct, new String[] {"id", "createDate", "modifyDate", "freezeStore"});
					productService.update(persistentProduct);
				}
			}
		} else {
			defaultProduct.setGoods(persistent);
			defaultProduct.setSpecificationValueList(null);
			
			if (StringUtils.isEmpty(defaultProduct.getId())) {
				productService.save(defaultProduct);
			} else {
				Product persistentProduct = productService.load(defaultProduct.getId());
				BeanUtils.copyProperties(defaultProduct, persistentProduct, new String[] {"id", "createDate", "modifyDate", "freezeStore"});
				productService.update(persistentProduct);
			}
		}
		
		cacheService.flushGoodsListPageCache(getRequest());
		
		redirectUrl = "goods!list.action";
		return SUCCESS;
	}
	
	// 获取商品分类树
	public List<GoodsCategory> getGoodsCategoryTreeList() {
		return goodsCategoryService.getGoodsCategoryTreeList();
	}
	
	// 获取所有商品类型
	public List<GoodsType> getAllGoodsTypeList() {
		return goodsTypeService.getAllList();
	}
	
	// 获取所有品牌
	public List<Brand> getAllBrandList() {
		return brandService.getAllList();
	}
	
	// 获取所有商品规格
	public List<Specification> getAllSpecificationList() {
		return specificationService.getAllList();
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Product getDefaultProduct() {
		return defaultProduct;
	}

	public void setDefaultProduct(Product defaultProduct) {
		this.defaultProduct = defaultProduct;
	}

	public List<GoodsImage> getGoodsImageList() {
		return goodsImageList;
	}

	public void setGoodsImageList(List<GoodsImage> goodsImageList) {
		this.goodsImageList = goodsImageList;
	}

	public List<File> getGoodsImageFileList() {
		return goodsImageFileList;
	}

	public void setGoodsImageFileList(List<File> goodsImageFileList) {
		this.goodsImageFileList = goodsImageFileList;
	}

	public String[] getSpecificationIds() {
		return specificationIds;
	}

	public void setSpecificationIds(String[] specificationIds) {
		this.specificationIds = specificationIds;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public List<String[]> getSpecificationValueIdsList() {
		return specificationValueIdsList;
	}

	public void setSpecificationValueIdsList(List<String[]> specificationValueIdsList) {
		this.specificationValueIdsList = specificationValueIdsList;
	}

	public Map<String, String> getGoodsAttributeValueMap() {
		return goodsAttributeValueMap;
	}

	public void setGoodsAttributeValueMap(Map<String, String> goodsAttributeValueMap) {
		this.goodsAttributeValueMap = goodsAttributeValueMap;
	}

	public Map<String, String> getGoodsParameterValueMap() {
		return goodsParameterValueMap;
	}

	public void setGoodsParameterValueMap(Map<String, String> goodsParameterValueMap) {
		this.goodsParameterValueMap = goodsParameterValueMap;
	}

}