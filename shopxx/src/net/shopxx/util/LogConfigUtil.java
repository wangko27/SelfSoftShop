package net.shopxx.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.shopxx.bean.LogConfig;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 工具类 - 日志配置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX6A15A4BF2AF0A65F46FF20145368F1F4
 * ============================================================================
 */

public class LogConfigUtil {
	
	private static final String CACHE_MANAGER_BEAN_NAME = "cacheManager";// cacheManager Bean名称
	private static final String SHOPXX_XML_FILE_NAME = "shopxx.xml";// SHOPXX XML配置文件名称
	private static final String ALL_LOG_CONFIG_LIST_CACHE_KEY= "SHOPXX_ALL_LOG_CONFIG_LIST";// 缓存Key
	
	/**
	 * 获得所有日志配置集合
	 * 
	 * @return 所有日志配置集合
	 */
	@SuppressWarnings("unchecked")
	public static List<LogConfig> getAllLogConfigList() {
		List<LogConfig> allLogConfigList = null;
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			allLogConfigList = (List<LogConfig>) generalCacheAdministrator.getFromCache(ALL_LOG_CONFIG_LIST_CACHE_KEY);
		} catch (NeedsRefreshException needsRefreshException) {
			boolean updateSucceeded = false;
			try {
				File shopxxXmlFile = null;
				Document document = null;
				try {
					shopxxXmlFile = new ClassPathResource(SHOPXX_XML_FILE_NAME).getFile();
					SAXReader saxReader = new SAXReader();
					document = saxReader.read(shopxxXmlFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				List<Node> nodeList = document.selectNodes("/shopxx/logConfig/*");
				Iterator<Node> iterator = nodeList.iterator();
				
				allLogConfigList = new ArrayList<LogConfig>();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					
					LogConfig logConfig = new LogConfig();
					logConfig.setOperation(element.attributeValue("operation"));
					logConfig.setActionClass(element.attributeValue("actionClass"));
					logConfig.setActionMethod(element.attributeValue("actionMethod"));
					allLogConfigList.add(logConfig);
				}
				generalCacheAdministrator.putInCache(ALL_LOG_CONFIG_LIST_CACHE_KEY, allLogConfigList);
				updateSucceeded = true;
			} finally {
	            if (!updateSucceeded) {
	            	generalCacheAdministrator.cancelUpdate(ALL_LOG_CONFIG_LIST_CACHE_KEY);
	            }
	        }
		}
		return allLogConfigList;
	}
	
	/**
	 * 刷新日志配置缓存
	 * 
	 */
	public static void flush() {
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		generalCacheAdministrator.flushEntry(ALL_LOG_CONFIG_LIST_CACHE_KEY);
	}

}