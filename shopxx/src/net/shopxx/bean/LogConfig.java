package net.shopxx.bean;


/**
 * Bean类 - 日志配置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX338298D5B309F4DB47533314D6524A77
 * ============================================================================
 */

public class LogConfig {
	
	private String operation;// 操作名称
	private String actionClass;// Action类名称
	private String actionMethod;// Action方法名称
	
	public String getActionClass() {
		return actionClass;
	}
	
	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}
	
	public String getActionMethod() {
		return actionMethod;
	}
	
	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}