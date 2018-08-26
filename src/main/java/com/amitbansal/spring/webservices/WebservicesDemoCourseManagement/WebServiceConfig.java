package com.amitbansal.spring.webservices.WebservicesDemoCourseManagement;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

// Spring configuration 
// Enable spring webservices
@Configuration
@EnableWs
public class WebServiceConfig {
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
	
}


// /ws/courses.wsdl
//spring will use this schema course-details.xsd and generate wsdl