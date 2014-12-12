package com.glueball.snoop.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(final String[] args) throws InterruptedException {

		@SuppressWarnings("resource")
		final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application.xml");
		final DataLoader loader = (DataLoader) ctx.getBean("dataLoader");

		final Thread th = new Thread(loader);
		th.start();
		th.join();

	}

}
