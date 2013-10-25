package net.shopxx.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.shopxx.bean.MailTemplateConfig;
import net.shopxx.bean.PageTemplateConfig;
import net.shopxx.bean.PrintTemplateConfig;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
/**
 * 模版工具类
 * @author iven
 *
 */
public class TemplateConfigUtil {
    
    private static Logger logger=Logger.getLogger(TemplateConfigUtil.class);
	
	private static final String CACHE_MANAGER_BEAN_NAME = "cacheManager";// cacheManager Bean名称
	private static final String TEMPLATE_XML_FILE_PATH = "WEB-INF/template/template.xml";// 模板配置文件名称
	private static final String TEMPLATE_CACHE_KEY_PREFIX = "joyin.mall_TEMPLATE";// 缓存Key前缀
	
	/**
	 * 获取所有页面模板配置集合
	 * 
	 * @return PageTemplateConfig集合
	 */
	@SuppressWarnings("unchecked")
	public static List<PageTemplateConfig> getAllPageTemplateConfigList() {
		File templateXmlFile = null;
		Document document = null;
		try {
			templateXmlFile = new File(CommonUtil.getWebRootPath() + TEMPLATE_XML_FILE_PATH);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(templateXmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element htmlConfigElement = (Element)document.selectSingleNode("/shopxx/page");
		Iterator<Element> iterator = htmlConfigElement.elementIterator();
		List<PageTemplateConfig> allPageTemplateConfigList = new ArrayList<PageTemplateConfig>();
	    while(iterator.hasNext()) {
	    	Element element = (Element)iterator.next();
	    	String description = element.element("description").getTextTrim();
	    	String templatePath = element.element("templatePath").getTextTrim();
	    	
	    	PageTemplateConfig pageTemplateConfig = new PageTemplateConfig();
	    	if (element.element("htmlPath") != null) {
				String htmlPath = element.element("htmlPath").getTextTrim();
				pageTemplateConfig.setHtmlPath(htmlPath);
			}
	    	pageTemplateConfig.setName(element.getName());
	    	pageTemplateConfig.setDescription(description);
	    	pageTemplateConfig.setTemplatePath(templatePath);
	    	allPageTemplateConfigList.add(pageTemplateConfig);
	    }
		return allPageTemplateConfigList;
	}
	
	/**
	 * 根据名称获取PageTemplateConfig对象
	 * 
	 * @return PageTemplateConfig对象
	 */
	public static PageTemplateConfig getPageTemplateConfig(String name) {
		PageTemplateConfig pageTemplateConfig = null;
		String cacheKey = TEMPLATE_CACHE_KEY_PREFIX + "_PAGE_" + name;
		logger.info("cacheKey:"+cacheKey);
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			pageTemplateConfig = (PageTemplateConfig) generalCacheAdministrator.getFromCache(cacheKey);
		} catch (NeedsRefreshException needsRefreshException) {
			boolean updateSucceeded = false;
			try {
				Document document = null;
				try {
					File templateXmlFile = new File(CommonUtil.getWebRootPath() + TEMPLATE_XML_FILE_PATH);

			        logger.info("templateXmlFile path:"+templateXmlFile.getAbsolutePath());
					SAXReader saxReader = new SAXReader();
					document = saxReader.read(templateXmlFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Element element = (Element)document.selectSingleNode("/shopxx/page/" + name);
				String description = element.element("description").getTextTrim();
				String templatePath = element.element("templatePath").getTextTrim();
				
				pageTemplateConfig = new PageTemplateConfig();
				if (element.element("htmlPath") != null) {
					String htmlPath = element.element("htmlPath").getTextTrim();
					pageTemplateConfig.setHtmlPath(htmlPath);
				}
				pageTemplateConfig.setName(element.getName());
				pageTemplateConfig.setDescription(description);
				pageTemplateConfig.setTemplatePath(templatePath);
				
				generalCacheAdministrator.putInCache(cacheKey, pageTemplateConfig);
				updateSucceeded = true;
			} finally {
	            if (!updateSucceeded) {
	            	generalCacheAdministrator.cancelUpdate(cacheKey);
	            }
	        }
		}
		return pageTemplateConfig;
	}
	
	/**
	 * 根据PageTemplateConfig对象读取模板文件内容
	 * 
	 * @return 模板文件内容
	 */
	public static String readTemplateFileContent(PageTemplateConfig pageTemplateConfig) {
		File templateFile = new File(CommonUtil.getWebRootPath() + pageTemplateConfig.getTemplatePath());
		String templateFileContent = null;
		try {
			templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateFileContent;
	}
	
	/**
	 * 写入模板文件内容
	 * 
	 */
	public static String writeTemplateFileContent(PageTemplateConfig pageTemplateConfig, String templateFileContent) {
		File templateFile = new File(CommonUtil.getWebRootPath() + pageTemplateConfig.getTemplatePath());
		try {
			FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateFileContent;
	}
	
	/**
	 * 获取所有邮件模板配置集合
	 * 
	 * @return MailTemplateConfig集合
	 */
	@SuppressWarnings("unchecked")
	public static List<MailTemplateConfig> getAllMailTemplateConfigList() {
		File templateXmlFile = null;
		Document document = null;
		try {
			templateXmlFile = new File(CommonUtil.getWebRootPath() + TEMPLATE_XML_FILE_PATH);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(templateXmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element htmlConfigElement = (Element)document.selectSingleNode("/shopxx/mail");
		Iterator<Element> iterator = htmlConfigElement.elementIterator();
		List<MailTemplateConfig> allMailTemplateConfigList = new ArrayList<MailTemplateConfig>();
	    while(iterator.hasNext()) {
	    	Element element = (Element)iterator.next();
	    	String description = element.element("description").getTextTrim();
	    	String subject = element.element("subject").getTextTrim();
	    	String templatePath = element.element("templatePath").getTextTrim();
	    	MailTemplateConfig mailTemplateConfig = new MailTemplateConfig();
	    	mailTemplateConfig.setName(element.getName());
	    	mailTemplateConfig.setDescription(description);
	    	mailTemplateConfig.setSubject(subject);
	    	mailTemplateConfig.setTemplatePath(templatePath);
	    	allMailTemplateConfigList.add(mailTemplateConfig);
	    }
		return allMailTemplateConfigList;
	}
	
	/**
	 * 根据名称获取MailTemplateConfig对象
	 * 
	 * @return MailTemplateConfig对象
	 */
	public static MailTemplateConfig getMailTemplateConfig(String name) {
		MailTemplateConfig mailTemplateConfig = null;
		String cacheKey = TEMPLATE_CACHE_KEY_PREFIX + "_MAIL_" + name;
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			mailTemplateConfig = (MailTemplateConfig) generalCacheAdministrator.getFromCache(cacheKey);
		} catch (NeedsRefreshException needsRefreshException) {
			boolean updateSucceeded = false;
			try {
				Document document = null;
				try {
					File templateXmlFile = new File(CommonUtil.getWebRootPath() + TEMPLATE_XML_FILE_PATH);
					SAXReader saxReader = new SAXReader();
					document = saxReader.read(templateXmlFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Element element = (Element)document.selectSingleNode("/shopxx/mail/" + name);
				String description = element.element("description").getTextTrim();
				String subject = element.element("subject").getTextTrim();
				String templatePath = element.element("templatePath").getTextTrim();
				
				mailTemplateConfig = new MailTemplateConfig();
				mailTemplateConfig.setName(element.getName());
				mailTemplateConfig.setDescription(description);
				mailTemplateConfig.setSubject(subject);
				mailTemplateConfig.setTemplatePath(templatePath);
				
				generalCacheAdministrator.putInCache(cacheKey, mailTemplateConfig);
				updateSucceeded = true;
			} finally {
	            if (!updateSucceeded) {
	            	generalCacheAdministrator.cancelUpdate(cacheKey);
	            }
	        }
		}
		return mailTemplateConfig;
	}
	
	/**
	 * 根据MailTemplateConfig对象读取模板文件内容
	 * 
	 * @return 模板文件内容
	 */
	public static String readTemplateFileContent(MailTemplateConfig mailTemplateConfig) {
		File templateFile = new File(CommonUtil.getWebRootPath() + mailTemplateConfig.getTemplatePath());
		String templateFileContent = null;
		try {
			templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateFileContent;
	}
	
	/**
	 * 写入模板文件内容
	 * 
	 */
	public static String writeTemplateFileContent(MailTemplateConfig mailTemplateConfig, String templateFileContent) {
		File templateFile = new File(CommonUtil.getWebRootPath() + mailTemplateConfig.getTemplatePath());
		try {
			FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateFileContent;
	}
	
	/**
	 * 获取所有打印模板配置集合
	 * 
	 * @return PrintTemplateConfig集合
	 */
	@SuppressWarnings("unchecked")
	public static List<PrintTemplateConfig> getAllPrintTemplateConfigList() {
		File templateXmlFile = null;
		Document document = null;
		try {
			templateXmlFile = new File(CommonUtil.getWebRootPath() + TEMPLATE_XML_FILE_PATH);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(templateXmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element htmlConfigElement = (Element)document.selectSingleNode("/shopxx/print");
		Iterator<Element> iterator = htmlConfigElement.elementIterator();
		List<PrintTemplateConfig> allPrintTemplateConfigList = new ArrayList<PrintTemplateConfig>();
	    while(iterator.hasNext()) {
	    	Element element = (Element)iterator.next();
	    	String description = element.element("description").getTextTrim();
	    	String templatePath = element.element("templatePath").getTextTrim();
	    	PrintTemplateConfig printTemplateConfig = new PrintTemplateConfig();
	    	printTemplateConfig.setName(element.getName());
	    	printTemplateConfig.setDescription(description);
	    	printTemplateConfig.setTemplatePath(templatePath);
	    	allPrintTemplateConfigList.add(printTemplateConfig);
	    }
		return allPrintTemplateConfigList;
	}
	
	/**
	 * 根据名称获取PrintTemplateConfig对象
	 * 
	 * @return PrintTemplateConfig对象
	 */
	public static PrintTemplateConfig getPrintTemplateConfig(String name) {
		Document document = null;
		try {
			File templateXmlFile = new File(CommonUtil.getWebRootPath() + TEMPLATE_XML_FILE_PATH);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(templateXmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element element = (Element)document.selectSingleNode("/shopxx/print/" + name);
		String description = element.element("description").getTextTrim();
		String templatePath = element.element("templatePath").getTextTrim();
		PrintTemplateConfig printTemplateConfig = new PrintTemplateConfig();
		printTemplateConfig.setName(element.getName());
		printTemplateConfig.setDescription(description);
		printTemplateConfig.setTemplatePath(templatePath);
		return printTemplateConfig;
	}
	
	/**
	 * 根据PrintTemplateConfig对象读取模板文件内容
	 * 
	 * @return 模板文件内容
	 */
	public static String readTemplateFileContent(PrintTemplateConfig printTemplateConfig) {
		File templateFile = new File(CommonUtil.getWebRootPath() + printTemplateConfig.getTemplatePath());
		String templateFileContent = null;
		try {
			templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateFileContent;
	}
	
	/**
	 * 写入模板文件内容
	 * 
	 */
	public static String writeTemplateFileContent(PrintTemplateConfig printTemplateConfig, String templateFileContent) {
		File templateFile = new File(CommonUtil.getWebRootPath() + printTemplateConfig.getTemplatePath());
		try {
			FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templateFileContent;
	}

}