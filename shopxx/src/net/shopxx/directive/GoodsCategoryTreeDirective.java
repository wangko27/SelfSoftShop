package net.shopxx.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.entity.GoodsCategory;
import net.shopxx.service.GoodsCategoryService;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("goodsCategoryTreeDirective")
public class GoodsCategoryTreeDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "goods_category_tree";
	
	@Resource(name = "goodsCategoryServiceImpl")
	private GoodsCategoryService goodsCategoryService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<GoodsCategory> goodsCategoryTree = goodsCategoryService.getGoodsCategoryTree();
		if (body != null && goodsCategoryTree != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(goodsCategoryTree);
			}
			body.render(env.getOut());
		}
	}

}