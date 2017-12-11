package com.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.myblog.common.config.CommonPropertyConfiguration;

/**
 *@author zyk
 */
@SpringBootApplication
@EnableConfigurationProperties(CommonPropertyConfiguration.class)
public class MyblogApplication extends SpringBootServletInitializer {
	
	/**
	 * springboot 加载thymeleaf full-layout 布局
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MyblogApplication.class);
	}
	
	/**
	 * spring boot 启动
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}
}
