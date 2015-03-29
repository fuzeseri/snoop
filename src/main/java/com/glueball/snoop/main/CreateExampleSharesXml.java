package com.glueball.snoop.main;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.glueball.snoop.entity.NetworkShare;
import com.glueball.snoop.entity.NetworkShares;

public class CreateExampleSharesXml {

	public static void main(String[] args) throws JAXBException {

		final NetworkShares shares = new NetworkShares();

		final NetworkShare share1 = new NetworkShare();
		share1.setAktiv(true);
		share1.setName("laptop-java");
		share1.setLocalPath("/home/karesz/dokumentumok/java");
		share1.setRemotePath("\\\\laptop\\dokumentumok\\java");
		shares.getShares().add(share1);

		final NetworkShare share2 = new NetworkShare();
		share2.setAktiv(true);
		share2.setName("laptop-mysql");
		share2.setLocalPath("/home/karesz/dokumentumok/MySQL");
		share2.setRemotePath("\\\\laptop\\dokumentumok\\MySQL");
		shares.getShares().add(share2);

	    final JAXBContext jaxbContext = JAXBContext.newInstance(NetworkShares.class);
	    final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.marshal(shares, System.out);
	    jaxbMarshaller.marshal(shares, new File("etc/shares_example.xml"));

	}

}
