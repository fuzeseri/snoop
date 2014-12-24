package com.glueball.snoop.bean;

import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

public class Main {

	public static void main(final String[] args) throws InterruptedException, IOException, SAXException, TikaException {

		final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application.xml");
		final DataLoader loader  = (DataLoader) ctx.getBean("dataLoader");
		final DataIndexer indexer = (DataIndexer) ctx.getBean("dataIndexer");

		final Thread th = new Thread(loader);
		th.start();

//		final Thread ti1 = new Thread(indexer);
//		ti1.start();

		final Thread th2 = new Thread(loader);
		th2.start();
		th2.join();

		final Thread ti2 = new Thread(indexer);
		ti2.start();

//		final Thread th3 = new Thread(loader);
//		th3.start();

		final Thread ti3 = new Thread(indexer);
		ti3.start();

//		final Thread th4 = new Thread(loader);
//		th4.start();
//		th4.join();

	}
}
