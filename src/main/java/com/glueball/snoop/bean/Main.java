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
		th.join();
		
		for (int i = 0 ; i < 21; i++) {
			final Thread ith = new Thread(indexer);
			ith.start();
			th.join();
			System.out.println( i + "   threadf starteed");
		}

	}
}
