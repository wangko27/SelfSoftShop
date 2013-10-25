package net.shopxx.action.admin;

import java.util.Date;

import javax.annotation.Resource;

import net.shopxx.entity.GoodsNotify;
import net.shopxx.service.GoodsNotifyService;
import net.shopxx.service.MailService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 到货通知
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
public class GoodsNotifyAction extends BaseAdminAction {

	private static final long serialVersionUID = 9134988941709810166L;

	@Resource(name = "goodsNotifyServiceImpl")
	private GoodsNotifyService goodsNotifyService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	
	// 列表
	public String list() {
		pager = goodsNotifyService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		goodsNotifyService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}
	
	// 发送
	public String send() {
		for (String id : ids) {
			GoodsNotify goodsNotify = goodsNotifyService.load(id);
			goodsNotify.setSendDate(new Date());
			goodsNotify.setIsSent(true);
			goodsNotifyService.update(goodsNotify);
			mailService.sendGoodsNotifyMail(goodsNotify);
		}
		return ajax(Status.success, "到货通知发送成功!");
	}

}