package _00_config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitiallizer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		// 【Spring ContextLoaderListener】
//		servletContext.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
//		servletContext.setInitParameter("contextConfigLocation", "_00_config.WebSocketConfig");
//		servletContext.addListener(new ContextLoaderListener());
		
		// 【Spring MVC 前置控制器】(必須！Spring WebSocket才有作用！)
		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcherServlet", new DispatcherServlet());
		registration.setLoadOnStartup(1);
		registration.addMapping("/");				 
		registration.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
		registration.setInitParameter("contextConfigLocation", "_00_config.WebMvcConfig");
		
		
//		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
//		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", new MyEncodingFilter());
//		encodingFilter.setInitParameter("encoding", "UTF-8");
//		encodingFilter.setInitParameter("forceEncoding", "true");
//		encodingFilter.addMappingForUrlPatterns(null , true, "/*");
		
	}

}
