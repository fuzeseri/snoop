package com.glueball.snoop.bean;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.glueball.snoop.entity.DocumentPath;

public class Main {

	public static void main(final String[] args) throws InterruptedException, IOException {

		@SuppressWarnings("resource")
		final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application.xml");
		final DataLoader loader = (DataLoader) ctx.getBean("dataLoader");

		System.out.println(DocumentPath.getCreateTable());
		//System.in.read();
		final Thread th = new Thread(loader);
		th.start();
		th.join();

	}

}
