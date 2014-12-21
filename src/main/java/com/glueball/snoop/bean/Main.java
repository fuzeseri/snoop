package com.glueball.snoop.bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.odf.OpenDocumentParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class Main {

	public static void main(final String[] args) throws InterruptedException, IOException, SAXException, TikaException {

		//final ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application.xml");
		//final DataLoader loader  = (DataLoader) ctx.getBean("dataLoader");
		//final DataIndexer indexer = (DataIndexer) ctx.getBean("dataIndexer");

//		final Thread th = new Thread(loader);
//		th.start();
//		th.join();

//		final Thread th1 = new Thread(indexer);
//		th1.start();
//		th1.join();

		//loader.run();
		//indexer.run();
		
//		org.apache.tika.parser.odf.OpenDocumentContentParser p = new org.apache.tika.parser.odf.OpenDocumentContentParser();
//		org.apache.tika.parser.ParseContext c = new org.apache.tika.parser.ParseContext();
//
//		for (MediaType mt : p.getSupportedTypes(c)) {
//			System.out.println("tm: " + mt.getType().toString());
//		}
		
		/*
		 * , new OpenDocumentParser(), new Mp3Parser(), new OOXMLParser() 
		 */

//		final Parser[] parsers= new Parser[] {
//				new org.apache.tika.parser.image.TiffParser(),
//				new org.apache.tika.parser.image.PSDParser(),
//				new org.apache.tika.parser.image.ImageParser(),
//				new org.apache.tika.parser.audio.AudioParser(),
//				new org.apache.tika.parser.audio.MidiParser(),
//				new org.apache.tika.parser.video.FLVParser(),
//				new org.apache.tika.parser.mail.RFC822Parser(),
//				new org.apache.tika.parser.mbox.OutlookPSTParser(),
//				new org.apache.tika.parser.mbox.MboxParser(),
//				new org.apache.tika.parser.asm.ClassParser(),
//				new org.apache.tika.parser.executable.ExecutableParser(),
//				new org.apache.tika.parser.dwg.DWGParser(),
//				new org.apache.tika.parser.odf.OpenDocumentParser(),
//				new org.apache.tika.parser.jpeg.JpegParser(),
//				new org.apache.tika.parser.mp3.Mp3Parser(),
//				new org.apache.tika.parser.pdf.PDFParser(),
//				new org.apache.tika.parser.iwork.IWorkPackageParser(),
//				new org.apache.tika.parser.rtf.RTFParser(),
//				new org.apache.tika.parser.html.HtmlParser(),
//				new org.apache.tika.parser.prt.PRTParser(),
//				new org.apache.tika.parser.mp4.MP4Parser(),
//				new org.apache.tika.parser.pkg.PackageParser(),
//				new org.apache.tika.parser.pkg.CompressorParser(),
//				new org.apache.tika.parser.iptc.IptcAnpaParser(),
//				new org.apache.tika.parser.chm.ChmParser(),
//				new org.apache.tika.parser.envi.EnviHeaderParser(),
//				new org.apache.tika.parser.code.SourceCodeParser(),
//				new org.apache.tika.parser.epub.EpubParser(),
//				new org.apache.tika.parser.txt.TXTParser(),
//				new org.apache.tika.parser.feed.FeedParser(),
//				new org.apache.tika.parser.xml.DcXMLParser(),
//				new org.apache.tika.parser.xml.FictionBookParser(),
//				new org.apache.tika.parser.xml.XMLParser(),
//				new org.apache.tika.parser.crypto.Pkcs7Parser(),
//				new org.apache.tika.parser.microsoft.ooxml.OOXMLParser(),
//				new org.apache.tika.parser.microsoft.OfficeParser(),
//				new org.apache.tika.parser.microsoft.TNEFParser(),
//				new org.apache.tika.parser.mat.MatParser(),
//				new org.apache.tika.parser.netcdf.NetCDFParser(),
//				new org.apache.tika.parser.font.AdobeFontMetricParser(),
//				new org.apache.tika.parser.font.TrueTypeParser(),
//				new org.apache.tika.parser.hdf.HDFParser(),
//		};
//		StringBuilder builder = new StringBuilder();
//		for ( final Parser p : parsers ) {
//
//			final Set<MediaType> mts=p.getSupportedTypes(new ParseContext());
//			for (final MediaType mt : mts) {
//				builder.append("<entry key=\""+mt + "\" value-ref=\"snoop" + p.getClass().getSimpleName() +"\" />").append("\n");
//				System.out.println("<entry key=\""+mt + "\" value-ref=\"snoop" + p.getClass().getSimpleName() +"\" />" );
//			}
//		}
//		
//		File outFile = new File("/home/karesz/tmp/spring-parser-map.txt");
//		FileWriter writer = new FileWriter(outFile);
//		writer.write(builder.toString());
//		writer.flush();
//		writer.close();

//		final InputStream input = new FileInputStream("/home/karesz/dokumentumok/database/Relational Database Index Design and the Optimizers ~mahasonaz~/Your Design Here/Wiley Interscience - Relational Database Index Design and the Optimizers.pdf");
//		final BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
//		final Metadata metadata = new Metadata();
//
//		final PDFParser parser = new PDFParser();
//		final PDFParserConfig pconfig = new PDFParserConfig();
//		pconfig.setExtractInlineImages(false);
//		//pconfig.setAverageCharTolerance((float) 23.33);
//		pconfig.setEnableAutoSpace(true);
//		pconfig.setExtractAcroFormContent(true);
//		pconfig.setExtractAnnotationText(true);
//		pconfig.setUseNonSequentialParser(true);
//
//		parser.setPDFParserConfig(pconfig);
//
//		parser.parse(input, handler, metadata, new ParseContext());
//
//		final String plainText = handler.toString();
//
//		System.out.println(plainText);

		
		final InputStream input = new FileInputStream("/home/karesz/dokumentumok/önéletrajz/eucv_magyar.doc");
		final BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
		final Metadata metadata = new Metadata();

		final PDFParser parser = new PDFParser();
		final PDFParserConfig pconfig = new PDFParserConfig();
		pconfig.setExtractInlineImages(false);
		//pconfig.setAverageCharTolerance((float) 23.33);
		pconfig.setEnableAutoSpace(true);
		pconfig.setExtractAcroFormContent(true);
		pconfig.setExtractAnnotationText(true);
		pconfig.setUseNonSequentialParser(true);

		parser.setPDFParserConfig(pconfig);

		final OpenDocumentParser parser11 = new OpenDocumentParser();
		//final OpenDocumentParserConfig pconfig = new OpenDocumentParserConfig();
		
		final ParseContext context = new ParseContext();
		
		parser.parse(input, handler, metadata, context);

	final String plainText = handler.toString();
		for (final String name : metadata.names()) {
		System.out.println(name + " :  " + metadata.get(name));
	}
		
		//System.out.println(plainText);
	}
}
