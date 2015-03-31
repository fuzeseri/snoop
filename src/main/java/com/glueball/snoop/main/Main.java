package com.glueball.snoop.main;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.spring.JAXRSServerFactoryBeanDefinitionParser.SpringJAXRSServerFactoryBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @author karesz
 */
public final class Main {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager.getLogger(Main.class);

    /**
     * Static SpringBusfactory instance.
     */
    private static final SpringBusFactory busFactory = new SpringBusFactory();

    /**
     * Spring context of the main application.
     */
    private final ApplicationContext ctx;

    /**
     * SpringBus instance to attach the jetty server with the cxf jaxrs server.
     */
    private final Bus bus;

    /**
     * Factory instance for the jaxrs server.
     */
    private final JAXRSServerFactoryBean jaxRsServerFactory;

    /**
     * Jetty http server instance.
     */
    private Server server;

    /**
     * Default constructor of the Main class. It initializes the spring context.
     * Creates the jetty and jaxrs servers.
     */
    public Main() {

        LOG.info("Initializing appication context...");

        ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application.xml");

        LOG.info("Appication context successfully initialized.");

        final String address = (String) ctx.getBean("listenAddress");

        LOG.info("Initializing jetty server...");

        bus = busFactory.createBus(new ClassPathResource(
                "spring/jetty-server.xml").getPath());

        LOG.info("Jetty server successfully initailized");

        LOG.info("Initializing jaxrs server...");

        jaxRsServerFactory = (SpringJAXRSServerFactoryBean) ctx
                .getBean("restContainer");
        jaxRsServerFactory.setBus(bus);
        jaxRsServerFactory.setAddress(address);

        LOG.info("Jaxrs server successfully initialized.");
    }

    /**
     * main method of the application. It starts the server.
     *
     * @param args
     *            command line arguments
     */
    public static void main(final String[] args) {

        final Main main = new Main();
        main.start();
    }

    /**
     * Starter method of the jaxrs server.
     */
    public void start() {

        LOG.info("Sarting server...");

        this.server = jaxRsServerFactory.create();

        LOG.info("Server successfully started.");
    }

    /**
     * Method to stop the application and exit.
     */
    public void stop() {

        LOG.info("Stopping server...");

        this.server.destroy();

        LOG.info("Server exit.");
    }
}
