package com.glueball.snoop.bean;

import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

public class Main {

	public static void main(final String[] args) throws InterruptedException, IOException, SAXException, TikaException {

		final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application.xml");
		final DataLoader loader  = (DataLoader) ctx.getBean("dataLoader");
		final DataIndexer indexer = (DataIndexer) ctx.getBean("dataIndexer");

//		final Thread th = new Thread(loader);
//		th.start();
//		th.join();
//
//		final Thread th1 = new Thread(indexer);
//		th1.start();
//		th1.join();

		loader.run();
		indexer.run();
	}
}
