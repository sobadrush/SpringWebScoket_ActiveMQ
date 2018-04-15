package _00_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@Import(WebSocketConfig.class) // 匯入 Spring WebSocket 配置
@ComponentScan(basePackages = "com.ctbc.controller.**")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// super.configureDefaultServletHandling(configurer);
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setContentType("text/html; charset=UTF-8");
		viewResolver.setExposeContextBeansAsAttributes(true);
		viewResolver.setPrefix("/WEB-INF/pages/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Bean
	public org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
		threadPoolExecutor.setCorePoolSize(5);
		threadPoolExecutor.setMaxPoolSize(10);
		threadPoolExecutor.setWaitForTasksToCompleteOnShutdown(false);
		return threadPoolExecutor;
	}
}
