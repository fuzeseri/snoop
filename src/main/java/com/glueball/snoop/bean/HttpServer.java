package com.glueball.snoop.bean;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.spring.JAXRSServerFactoryBeanDefinitionParser.SpringJAXRSServerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HttpServer {

	private final SpringBusFactory busFactory = new SpringBusFactory();
	private final JAXRSServerFactoryBean jaxRsServerFactory;
	private final String JETTY_CONTEXT_XML = "src/main/resources/spring/jetty-server.xml";
	private final String DEFAULT_HTTP_HOST_PORT = "http://localhost:8080/";
	private Server server;

	public HttpServer() throws Exception {

		final Bus bus = busFactory.createBus(JETTY_CONTEXT_XML);
		SpringBusFactory.setDefaultBus(bus);

		@SuppressWarnings("resource")
		final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/cxf-servlet.xml");
		jaxRsServerFactory = (SpringJAXRSServerFactoryBean) ctx.getBean("restContainer");
		jaxRsServerFactory.setAddress(DEFAULT_HTTP_HOST_PORT);

	}

	public final void start() {
		this.server = jaxRsServerFactory.create();
	}

	public final void stop() throws Exception {
		this.server.stop();
	}

	public final void setHttpHostPort(final String hostPort) {
		jaxRsServerFactory.setAddress(hostPort);
	}

	public static void main(String[] args) throws Throwable {
		final HttpServer httpServer = new HttpServer();
		httpServer.start();
	}

}
