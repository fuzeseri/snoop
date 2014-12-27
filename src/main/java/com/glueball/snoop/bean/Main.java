package com.glueball.snoop.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

public class Main {
	
	private static final List<Thread> thList = new ArrayList<Thread>();

	public static void main(final String[] args) throws InterruptedException, IOException, SAXException, TikaException {

		final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application.xml");
		final DataLoader loader  = (DataLoader) ctx.getBean("dataLoader");
		final DataIndexer indexer = (DataIndexer) ctx.getBean("dataIndexer");

//		final Thread th = new Thread(loader);
//		th.start();
//		th.join();
		loader.run();
		
		for (int i = 0; i < 22; i++) {
			indexer.run();
//			thList.add(new Thread(indexer));
		}
		
//		for (final Thread th : thList) {
//			th.start();
//		}
//		
//		while (true) {
//		}
	}
}
