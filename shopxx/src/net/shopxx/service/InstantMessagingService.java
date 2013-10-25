package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.InstantMessaging;
import net.shopxx.entity.InstantMessaging.InstantMessagingType;

/**
 * Service接口 - 在线客服
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXB99DB2E7E2EF324F36B462FD6C183267
 * ============================================================================
 */

public interface InstantMessagingService extends BaseService<InstantMessaging, String> {
	
	/**
	 * 根据在线客服类型、最大结果数获取在线客服集合
	 *            
	 * @param instantMessagingType
	 *            在线客服类型,null表示无限制
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 在线客服集合
	 */
	public List<InstantMessaging> getInstantMessagingList(InstantMessagingType instantMessagingType, Integer maxResults);

}