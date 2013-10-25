package net.shopxx.listener;
import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import magick.Magick;
import net.shopxx.entity.Article;
import net.shopxx.entity.Goods;
import net.shopxx.service.ArticleService;
import net.shopxx.service.GoodsService;
import net.shopxx.service.HtmlService;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

/**
 * SHOP++ 安装初始化
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXF493F4F9639AB767EED179DBF919AD85
 * ============================================================================
 */

@Component("Initializable")
public class InitializableApplicationListener implements ApplicationListener, ServletContextAware {
	
	private static final String BUILD_HTML_CONFIG_FILE_PATH = "/build_html.conf";
	
	private ServletContext servletContext;
	@Resource(name = "htmlServiceImpl")
	private HtmlService htmlService;
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		if(applicationEvent instanceof ContextRefreshedEvent) {
			File buildHtmlFileConfigFile = new File(servletContext.getRealPath(BUILD_HTML_CONFIG_FILE_PATH));
			if (true) {//buildHtmlFileConfigFile.exists()) {
				System.out.print("BUILD HTML ...");
				
				htmlService.buildIndexHtml();
				htmlService.buildLoginHtml();
				htmlService.buildRegisterAgreementHtml();
				htmlService.buildAdminJs();
				htmlService.buildShopJs();
				htmlService.buildErrorPageHtml();
				htmlService.buildErrorPageAccessDeniedHtml();
				htmlService.buildErrorPage500Html();
				htmlService.buildErrorPage404Html();
				htmlService.buildErrorPage403Html();
				
				System.out.print("..");
				
				List<Article> articleList = articleService.getAllList();
				if (articleList != null) {
					for (int i = 0; i < articleList.size(); i ++) {
						Article article = articleList.get(i);
						htmlService.buildArticleContentHtml(article);
						if (i % 10 == 0) {
							System.out.print("..");
						}
					}
				}
				
				List<Goods> goodsList = goodsService.getAllList();
				if (goodsList != null) {
					for (int i = 0; i < goodsList.size(); i ++) {
						Goods goods = goodsList.get(i);
						htmlService.buildGoodsContentHtml(goods);
						if (i % 10 == 0) {
							System.out.print("..");
						}
					}
				}
				
				System.out.println(" OK");
				buildHtmlFileConfigFile.delete();
			}
			try {
				System.setProperty("jmagick.systemclassloader", "no");
				new Magick();
				System.out.println("IMAGE MAGICK ENABLED");
			} catch (Throwable e) {
			}
		}
	}

}