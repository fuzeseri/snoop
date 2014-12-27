package com.glueball.snoop.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.glueball.snoop.dao.DocumentPathBean;
import com.glueball.snoop.dao.IndexedDocumentBean;
import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.IndexedDocument;

public class TestDatabase {
	
	public static void main(String[] args) {
		
		
		final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application.xml");
		final DocumentPathBean dpath = (DocumentPathBean)ctx.getBean("documentPath");
		final IndexedDocumentBean idoc = (IndexedDocumentBean)ctx.getBean("indexedDocument");
		
//		for (final DocumentPath doc : dpath.selectAll()) {
//			System.out.println(doc.toString());
//		}

		for (final IndexedDocument doc : idoc.selectAll()) {
			System.out.println(doc.toString());
		}

	}

}
