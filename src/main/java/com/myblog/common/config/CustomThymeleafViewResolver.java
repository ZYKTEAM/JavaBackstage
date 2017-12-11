package com.myblog.common.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring4.view.AbstractThymeleafView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
/**
 * 封装full-layout 布局页面
 * @author xiaojun
 *
 */
public class CustomThymeleafViewResolver extends ThymeleafViewResolver {

	@Autowired
	private CommonPropertyConfiguration config;
	
	@Override
	protected View loadView(String rawViewName, Locale locale) throws Exception {
		String viewName = rawViewName.startsWith(config.getLayout()) ? rawViewName : config.getFullLayout();
		AbstractThymeleafView view = (AbstractThymeleafView) super.loadView(viewName, locale);
		if(!rawViewName.startsWith(config.getLayout())){
//			view.addStaticVariable("user", UserUtil.getUser());
			view.addStaticVariable("templateName", rawViewName);
		}
		return view;
	}

}
