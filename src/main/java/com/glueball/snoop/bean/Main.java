package com.glueball.snoop.bean;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.spring.JAXRSServerFactoryBeanDefinitionParser.SpringJAXRSServerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class Main {

	private static final SpringBusFactory busFactory = new SpringBusFactory();

	private final ApplicationContext ctx;
	private final Bus bus;
	private final JAXRSServerFactoryBean jaxRsServerFactory;
	private Server server;

	public Main() {

		ctx = new ClassPathXmlApplicationContext("classpath:spring/application.xml");
		final String address = (String) ctx.getBean("listenAddress");

		bus = busFactory.createBus(new ClassPathResource("spring/jetty-server.xml").getPath());
		
		jaxRsServerFactory = (SpringJAXRSServerFactoryBean) ctx.getBean("restContainer");
		jaxRsServerFactory.setBus(bus);
		jaxRsServerFactory.setAddress(address);
	}

	public static void main(final String[] args) {

		final Main main = new Main();
		main.start();
	}

	public void start() {

		this.server = jaxRsServerFactory.create();
	}

	public void stop() {

		this.server.destroy();
		System.exit(1);
	}
}
