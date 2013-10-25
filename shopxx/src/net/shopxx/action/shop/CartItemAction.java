package net.shopxx.action.shop;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.bean.CartItemCookie;
import net.shopxx.bean.Setting;
import net.shopxx.bean.Setting.ScoreType;
import net.shopxx.entity.CartItem;
import net.shopxx.entity.Goods;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.service.CartItemService;
import net.shopxx.service.ProductService;
import net.shopxx.util.JsonUtil;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 购物车项
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX25805FB3BE09EA71895EAB4E18BEB912
 * ============================================================================
 */

@ParentPackage("shop")
public class CartItemAction extends BaseShopAction {

	private static final long serialVersionUID = 4404250263935012169L;
	
	private Integer quantity;// 购买数量
	private Integer totalProductQuantity;// 商品总数
	private BigDecimal totalProductPrice;// 商品总价格
	private Integer totalScore;// 总积分
	
	private List<CartItem> cartItemList = new ArrayList<CartItem>();

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;

	// 添加购物车项
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "商品ID不允许为空")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "quantity", min = "1", message = "购买数量必须为正整数!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxAdd() {
		Product product = productService.load(id);
		if (!product.getIsMarketable()) {
			return ajax(Status.error, "此商品已下架");
		}
		
		totalProductQuantity = 0;
		totalProductPrice = new BigDecimal(0);
		
		Member loginMember = getLoginMember();
		
		if (loginMember != null) {
			boolean isExist = false;
			Set<CartItem> previousCartItemList = loginMember.getCartItemSet();
			if (previousCartItemList != null) {
				for (CartItem cartItem : previousCartItemList) {
					Product previousCartItemProduct = cartItem.getProduct();
					if (StringUtils.equals(previousCartItemProduct.getId(), id)) {
						isExist = true;
						if (product.getStore() != null && (product.getFreezeStore() + cartItem.getQuantity() + quantity) > product.getStore()) {
							return ajax(Status.error, "添加购物车失败,商品库存不足!");
						}
						cartItem.setQuantity(cartItem.getQuantity() + quantity);
						cartItemService.update(cartItem);
					}
					
					totalProductQuantity += cartItem.getQuantity();
					totalProductPrice =  previousCartItemProduct.getPreferentialPrice(loginMember).multiply(new BigDecimal(cartItem.getQuantity())).add(totalProductPrice);
				}
			}
			
			if (!isExist) {
				if (product.getStore() != null && (product.getFreezeStore() + quantity) > product.getStore()) {
					return ajax(Status.error, "添加购物车失败,商品库存不足!");
				}
				CartItem cartItem = new CartItem();
				cartItem.setMember(loginMember);
				cartItem.setProduct(product);
				cartItem.setQuantity(quantity);
				cartItemService.save(cartItem);
				
				totalProductQuantity += cartItem.getQuantity();
				totalProductPrice =  product.getPreferentialPrice(loginMember).multiply(new BigDecimal(cartItem.getQuantity())).add(totalProductPrice);
			}
		} else {
			boolean isExist = false;
			List<CartItemCookie> cartItemCookieList = new ArrayList<CartItemCookie>();
			String cartItemListCookie = getCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
			if (StringUtils.isNotEmpty(cartItemListCookie)) {
				try {
					List<CartItemCookie> previousCartItemCookieList = JsonUtil.toObject(cartItemListCookie, new TypeReference<ArrayList<CartItemCookie>>() {});
					for (CartItemCookie cartItemCookie : previousCartItemCookieList) {
						Product cartItemCookieProduct = productService.get(cartItemCookie.getI());
						if (cartItemCookieProduct != null) {
							if (StringUtils.equals(cartItemCookie.getI(), id)) {
								isExist = true;
								if (product.getStore() != null && (product.getFreezeStore() + cartItemCookie.getQ() + quantity) > product.getStore()) {
									return ajax(Status.error, "添加购物车失败,商品库存不足!");
								}
								cartItemCookie.setQ(cartItemCookie.getQ() + quantity);
							}
							cartItemCookieList.add(cartItemCookie);
							totalProductQuantity += cartItemCookie.getQ();
							totalProductPrice =  cartItemCookieProduct.getPrice().multiply(new BigDecimal(cartItemCookie.getQ())).add(totalProductPrice);
						}
					}
				} catch (JsonParseException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				} catch (JsonMappingException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				} catch (IOException e) {
					e.printStackTrace();
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				}
			}
			
			if (!isExist) {
				if (product.getStore() != null && (product.getFreezeStore() + quantity) > product.getStore()) {
					return ajax(Status.error, "添加购物车失败,商品库存不足!");
				}
				CartItemCookie cartItemCookie = new CartItemCookie();
				cartItemCookie.setI(id);
				cartItemCookie.setQ(quantity);
				cartItemCookieList.add(cartItemCookie);
				
				totalProductQuantity += cartItemCookie.getQ();
				totalProductPrice =  product.getPrice().multiply(new BigDecimal(cartItemCookie.getQ())).add(totalProductPrice);
			}
			
			setCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, JsonUtil.toJson(cartItemCookieList), CartItemCookie.CART_ITEM_LIST_COOKIE_MAX_AGE);
		}
		
		totalProductPrice = SettingUtil.setPriceScale(totalProductPrice);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, Status.success);
		jsonMap.put(MESSAGE_PARAMETER_NAME, "添加至购物车成功!");
		jsonMap.put("totalProductQuantity", totalProductQuantity.toString());
		jsonMap.put("totalProductPrice", totalProductPrice);
		return ajax(jsonMap);
	}
	
	// 购物车项列表
	@SuppressWarnings("unchecked")
	@InputConfig(resultName = "error")
	public String list() {
		totalProductQuantity = 0;
		totalProductPrice = new BigDecimal(0);
		totalScore = 0;
		
		Setting setting = getSetting();
		Member loginMember = getLoginMember();
		
		if (loginMember != null) {
			Set<CartItem> cartItemSet = loginMember.getCartItemSet();
			if (cartItemSet != null) {
				cartItemList = new ArrayList<CartItem>(cartItemSet);
				for (CartItem cartItem : cartItemList) {
					Goods goods = cartItem.getProduct().getGoods();
					totalProductQuantity += cartItem.getQuantity();
					if (setting.getScoreType() == ScoreType.goodsSet) {
						totalScore = goods.getScore() * cartItem.getQuantity() + totalScore;
					}
					totalProductPrice = cartItem.getProduct().getPreferentialPrice(loginMember).multiply(new BigDecimal(cartItem.getQuantity())).add(totalProductPrice);
				}
			}
		} else {
			String cartItemListCookie = getCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
			if (StringUtils.isNotEmpty(cartItemListCookie)) {
				try {
					List<CartItemCookie> cartItemCookieList = JsonUtil.toObject(cartItemListCookie, new TypeReference<ArrayList<CartItemCookie>>() {});
					for (CartItemCookie cartItemCookie : cartItemCookieList) {
						Product product = productService.get(cartItemCookie.getI());
						if (product != null) {
							Goods goods = product.getGoods();
							totalProductQuantity += cartItemCookie.getQ();
							if (setting.getScoreType() == ScoreType.goodsSet) {
								totalScore = goods.getScore() * cartItemCookie.getQ() + totalScore;
							}
							totalProductPrice = product.getPrice().multiply(new BigDecimal(cartItemCookie.getQ())).add(totalProductPrice);
							
							CartItem cartItem = new CartItem();
							cartItem.setProduct(product);
							cartItem.setQuantity(cartItemCookie.getQ());
							cartItemList.add(cartItem);
						}
					}
				} catch (JsonParseException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				} catch (JsonMappingException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				} catch (IOException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				}
			}
		}
		
		totalProductPrice = SettingUtil.setPriceScale(totalProductPrice);
		if (setting.getScoreType() == ScoreType.orderAmount) {
			totalScore = totalProductPrice.multiply(new BigDecimal(getSetting().getScoreScale().toString())).setScale(0, RoundingMode.DOWN).intValue();
		}
		return LIST;
	}
	
	// 修改购物车项
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "商品ID不允许为空")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "quantity", min = "1", message = "购买数量必须为正整数!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxEdit() {
		totalProductQuantity = 0;
		totalProductPrice = new BigDecimal(0);
		totalScore = 0;
		BigDecimal subtotalPrice = new BigDecimal(0);
		Setting setting = getSetting();
		Member loginMember = getLoginMember();
		if (loginMember != null) {
			Set<CartItem> cartItemSet = loginMember.getCartItemSet();
			if (cartItemSet != null) {
				for (CartItem cartItem : cartItemSet) {
					Product product = cartItem.getProduct();
					Goods goods = product.getGoods();
					if (StringUtils.equals(id, cartItem.getProduct().getId())) {
						if (product.getStore() != null && (product.getFreezeStore() + quantity) > product.getStore()) {
							return ajax(Status.error, "商品库存不足!");
						}
						cartItem.setQuantity(quantity);
						cartItemService.update(cartItem);
						subtotalPrice = cartItem.getSubtotalPrice();
					}
					totalProductQuantity += cartItem.getQuantity();
					if (setting.getScoreType() == ScoreType.goodsSet) {
						totalScore = goods.getScore() * cartItem.getQuantity() + totalScore;
					}
					totalProductPrice = product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity())).add(totalProductPrice);
				}
			}
		} else {
			String cartItemListCookie = getCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
			if (StringUtils.isNotEmpty(cartItemListCookie)) {
				try {
					List<CartItemCookie> cartItemCookieList = JsonUtil.toObject(cartItemListCookie, new TypeReference<ArrayList<CartItemCookie>>() {});
					for (Iterator<CartItemCookie> iterator = cartItemCookieList.iterator(); iterator.hasNext();) {
						CartItemCookie cartItemCookie = iterator.next();
						Product product = productService.get(cartItemCookie.getI());
						Goods goods = product.getGoods();
						if (product != null) {
							if (StringUtils.equals(id, cartItemCookie.getI())) {
								if (product.getStore() != null && (product.getFreezeStore() + quantity) > product.getStore()) {
									return ajax(Status.error, "商品库存不足!");
								}
								cartItemCookie.setQ(quantity);
								subtotalPrice = product.getPrice().multiply(new BigDecimal(quantity));
							}
							totalProductQuantity += cartItemCookie.getQ();
							if (setting.getScoreType() == ScoreType.goodsSet) {
								totalScore = goods.getScore() * cartItemCookie.getQ() + totalScore;
							}
							totalProductPrice = product.getPrice().multiply(new BigDecimal(cartItemCookie.getQ().toString())).add(totalProductPrice);
						} else {
							iterator.remove();
						}
						setCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, JsonUtil.toJson(cartItemCookieList), CartItemCookie.CART_ITEM_LIST_COOKIE_MAX_AGE);
					}
				} catch (JsonParseException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				} catch (JsonMappingException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				} catch (IOException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				}
			}
		}
		
		totalProductPrice = SettingUtil.setPriceScale(totalProductPrice);
		subtotalPrice = SettingUtil.setPriceScale(subtotalPrice);
		if (setting.getScoreType() == ScoreType.orderAmount) {
			totalScore = totalProductPrice.multiply(new BigDecimal(setting.getScoreScale().toString())).setScale(0, RoundingMode.DOWN).intValue();
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, Status.success);
		jsonMap.put("subtotalPrice", subtotalPrice);
		jsonMap.put("totalProductQuantity", totalProductQuantity.toString());
		jsonMap.put("totalScore", totalScore.toString());
		jsonMap.put("totalProductPrice", totalProductPrice);
		return ajax(jsonMap);
	}
	
	// 删除购物车项
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "商品ID不允许为空")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxDelete() {
		totalProductQuantity = 0;
		totalScore = 0;
		totalProductPrice = new BigDecimal(0);
		Setting setting = getSetting();
		Member loginMember = getLoginMember();
		if (loginMember != null) {
			Set<CartItem> cartItemSet = loginMember.getCartItemSet();
			if (cartItemSet != null) {
				for (CartItem cartItem : cartItemSet) {
					Product product = cartItem.getProduct();
					Goods goods = product.getGoods();
					if (StringUtils.equals(product.getId(), id)) {
						cartItemService.delete(cartItem);
					} else {
						totalProductQuantity += cartItem.getQuantity();
						if (setting.getScoreType() == ScoreType.goodsSet) {
							totalScore = goods.getScore() * cartItem.getQuantity() + totalScore;
						}
						totalProductPrice = product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity())).add(totalProductPrice);
					}
				}
			}
		} else {
			String cartItemListCookie = getCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
			if (StringUtils.isNotEmpty(cartItemListCookie)) {
				try {
					List<CartItemCookie> cartItemCookieList = JsonUtil.toObject(cartItemListCookie, new TypeReference<ArrayList<CartItemCookie>>() {});
					for (Iterator<CartItemCookie> iterator = cartItemCookieList.iterator(); iterator.hasNext();) {
						CartItemCookie cartItemCookie = iterator.next();
						if (StringUtils.equals(cartItemCookie.getI(), id)) {
							iterator.remove();
						} else {
							Product product = productService.get(cartItemCookie.getI());
							Goods goods = product.getGoods();
							if (product != null) {
								totalProductQuantity += cartItemCookie.getQ();
								if (setting.getScoreType() == ScoreType.goodsSet) {
									totalScore = goods.getScore() * cartItemCookie.getQ() + totalScore;
								}
								totalProductPrice = product.getPrice().multiply(new BigDecimal(cartItemCookie.getQ())).add(totalProductPrice);
							} else {
								iterator.remove();
							}
						}
					}
					setCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, JsonUtil.toJson(cartItemCookieList), CartItemCookie.CART_ITEM_LIST_COOKIE_MAX_AGE);
				} catch (JsonParseException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				} catch (JsonMappingException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				} catch (IOException e) {
					removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
				}
			}
		}
		totalProductPrice = SettingUtil.setPriceScale(totalProductPrice);
		if (getSetting().getScoreType() == ScoreType.orderAmount) {
			totalScore = totalProductPrice.multiply(new BigDecimal(getSetting().getScoreScale().toString())).setScale(0, RoundingMode.DOWN).intValue();
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, Status.success);
		jsonMap.put(MESSAGE_PARAMETER_NAME, "商品删除成功!");
		jsonMap.put("totalProductQuantity", totalProductQuantity.toString());
		jsonMap.put("totalScore", totalScore.toString());
		jsonMap.put("totalProductPrice", totalProductPrice);
		return ajax(jsonMap);
	}
	
	// 清空购物车项
	@SuppressWarnings("unchecked")
	@InputConfig(resultName = "ajaxError")
	public String ajaxClear() {
		Member loginMember = getLoginMember();
		if (loginMember == null) {
			removeCookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME);
		} else {
			Set<CartItem> cartItemSet = loginMember.getCartItemSet();
			if (cartItemSet != null) {
				for (CartItem cartItem : cartItemSet) {
					cartItemService.delete(cartItem);
				}
			}
		}
		return ajax(Status.success, "购物车清空成功!");
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getTotalProductQuantity() {
		return totalProductQuantity;
	}

	public void setTotalProductQuantity(Integer totalProductQuantity) {
		this.totalProductQuantity = totalProductQuantity;
	}

	public BigDecimal getTotalProductPrice() {
		return totalProductPrice;
	}

	public void setTotalProductPrice(BigDecimal totalProductPrice) {
		this.totalProductPrice = totalProductPrice;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

}