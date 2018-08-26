package com.amitbansal.spring.webservices.WebservicesDemoCourseManagement;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;

import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

// Spring configuration 
// Enable spring webservices
@Configuration
@EnableWs
public class WebServiceConfig extends WsConfigurerAdapter{
	// Message dispatcher servlet
	// spring application context
	// url */
	@Bean
	ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context){
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<MessageDispatcherServlet>(messageDispatcherServlet, "/ws/*");
	}
	
	@Bean(name="courses")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema courseSchema){
		DefaultWsdl11Definition definition  = new DefaultWsdl11Definition();
		// port type
		definition.setPortTypeName("CoursePort");
		
		// name space
		definition.setTargetNamespace("https://amitbansal.com/courses");
		
		// ws
		definition.setLocationUri("/ws");
		
		// schema
		definition.setSchema(courseSchema);
		return definition;
	}
	
	@Bean
	XsdSchema courseSchema(){
		return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
	}
	
	//xwsSecurityInterceptor
		// callback handler for validating user name password
		// security policy
	// add interceptor
	
	
	@Bean
	public XwsSecurityInterceptor securityInterceptor(){
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		securityInterceptor.setCallbackHandler(callbackHandler());
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return securityInterceptor;
	}

	@Bean
	public  SimplePasswordValidationCallbackHandler callbackHandler() {
		// TODO Auto-generated method stub
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		handler.setUsersMap(Collections.singletonMap("user", "password"));
		return handler;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		// TODO Auto-generated method stub
		interceptors.add(securityInterceptor());
	}	
	
}


// /ws/courses.wsdl
//spring will use this schema course-details.xsd and generate wsdl