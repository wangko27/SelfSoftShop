package net.shopxx.action.shop;

import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.entity.Area;
import net.shopxx.entity.Member;
import net.shopxx.entity.Receiver;
import net.shopxx.service.AreaService;
import net.shopxx.service.ReceiverService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 收货地址
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX0CF28CB746B2C279BD0B344431852EDA
 * ============================================================================
 */

@ParentPackage("shop")
@InterceptorRefs({
	@InterceptorRef(value = "memberVerifyInterceptor"),
	@InterceptorRef(value = "shopStack")
})
public class ReceiverAction extends BaseShopAction {

	private static final long serialVersionUID = 5947142304957196520L;
	
	private String areaId;// 收货地区ID;
	private Receiver receiver;
	private Set<Receiver> receiverSet;

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;

	// 收货地址添加
	public String add() {
		Member loginMember = getLoginMember();
		Set<Receiver> receiverSet = loginMember.getReceiverSet();
		if (receiverSet != null && Receiver.MAX_RECEIVER_COUNT != null && receiverSet.size() >= Receiver.MAX_RECEIVER_COUNT) {
			addActionError("只允许最多添加" + Receiver.MAX_RECEIVER_COUNT + "项收货地址!");
			return ERROR;
		}
		return INPUT;
	}
	
	// 收货地址编辑
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!")
		}
	)
	public String edit() {
		receiver = receiverService.load(id);
		if(receiver.getMember() != getLoginMember()) {
			addActionError("参数错误!");
			return ERROR;
		}
		return INPUT;
	}
	
	// 收货地址删除
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxDelete() {
		Receiver receiver = receiverService.load(id);
		if(receiver.getMember() != getLoginMember()) {
			return ajax(Status.error, "参数错误!");
		}
		receiverService.delete(receiver);
		return ajax(Status.success, "删除成功!");
	}
	
	// 收货地址列表
	public String list() {
		return LIST;
	}
	
	// 收货地址保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "areaId", message = "地区不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.name", message = "收货人不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.address", message = "联系地址不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.zipCode", message = "邮编不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isEmpty(receiver.getPhone()) && StringUtils.isEmpty(receiver.getMobile())) {
			addActionError("联系电话、联系手机必须填写其中一项!");
			return ERROR;
		}
		Member loginMember = getLoginMember();
		Set<Receiver> receiverSet = loginMember.getReceiverSet();
		if (receiverSet != null && Receiver.MAX_RECEIVER_COUNT != null && receiverSet.size() >= Receiver.MAX_RECEIVER_COUNT) {
			addActionError("只允许添加最多" + Receiver.MAX_RECEIVER_COUNT + "项收货地址!");
			return ERROR;
		}
		Area area = areaService.get(areaId);
		if (area == null) {
			addActionError("请选择收货地址!");
			return ERROR;
		}
		
		receiver.setArea(area);
		receiver.setMember(loginMember);
		receiverService.save(receiver);
		redirectUrl = "receiver!list.action";
		return SUCCESS;
	}
	
	// 收货地址更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "areaId", message = "地区不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.name", message = "收货人不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.address", message = "联系地址不允许为空!"),
			@RequiredStringValidator(fieldName = "receiver.zipCode", message = "邮编不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		if (StringUtils.isEmpty(receiver.getMobile()) && StringUtils.isEmpty(receiver.getPhone())) {
			addActionError("联系手机、联系电话必须填写其中一项!");
			return ERROR;
		}
		Receiver persistent = receiverService.load(id);
		if(persistent.getMember() != getLoginMember()) {
			addActionError("参数错误!");
			return ERROR;
		}
		Area area = areaService.get(areaId);
		if (area == null) {
			addActionError("请选择收货地址!");
			return ERROR;
		}
		
		receiver.setArea(area);
		BeanUtils.copyProperties(receiver, persistent, new String[] {"id", "createDate", "modifyDate", "member"});
		receiverService.update(persistent);
		redirectUrl = "receiver!list.action";
		return SUCCESS;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public Set<Receiver> getReceiverSet() {
		this.receiverSet = getLoginMember().getReceiverSet();
		return receiverSet;
	}

	public void setReceiverSet(Set<Receiver> receiverSet) {
		this.receiverSet = receiverSet;
	}

}