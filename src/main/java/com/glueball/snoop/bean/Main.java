package com.glueball.snoop.bean;

import java.io.IOException;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.spring.JAXRSServerFactoryBeanDefinitionParser.SpringJAXRSServerFactoryBean;
import org.apache.tika.exception.TikaException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

public class Main {

	private static final SpringBusFactory busFactory = new SpringBusFactory();
	private static final String JETTY_CONTEXT_XML = "src/main/resources/spring/jetty-server.xml";

	public static void main(final String[] args) throws InterruptedException, IOException, SAXException, TikaException {

		final Bus bus = busFactory.createBus(JETTY_CONTEXT_XML);
		//SpringBusFactory.setDefaultBus(bus);

		final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application.xml");
		final String address = (String) ctx.getBean("listenAddress");
		final JAXRSServerFactoryBean jaxRsServerFactory = (SpringJAXRSServerFactoryBean) ctx.getBean("restContainer");
		jaxRsServerFactory.setBus(bus);
		jaxRsServerFactory.setAddress(address);
		final Server server = jaxRsServerFactory.create();

	}
}
