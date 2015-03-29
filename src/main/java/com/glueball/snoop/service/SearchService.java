package com.glueball.snoop.service;

import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;

import com.glueball.snoop.module.main.model.SearchResult;
import com.glueball.snoop.module.main.model.SearchResults;
import com.glueball.snoop.module.main.model.ServerMessage;

@Path("/search")
public class SearchService<QueryParser> {
	
	private static Logger LOG = LogManager.getLogger(SearchService.class);

	private static final int MAX_SCORE_DOCS = 1000;

	//private ClassPathXmlApplicationContext context;

	@Autowired
	private Directory directory;

	public void setDirectory(final Directory directory) {
		this.directory = directory;
	}

	private IndexReader indexReader;
	
	private IndexSearcher indexSearcher;

	@Autowired
	private MultiFieldQueryParser parser;

	public void setParser(final MultiFieldQueryParser parser) {
		this.parser = parser;
	}

	private int hitsPerPage = 50;

	public void setHitsPerPage(int hitsPerPage) {
		this.hitsPerPage = hitsPerPage;
	}

	public void init() {

		try {

			refreshReader();
		} catch (final IOException e) {

			LOG.info("No directroy available");
			LOG.debug(e.getMessage());
		}
	}

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{keyword}")
	public Response search(
			@PathParam(value = "keyword") @DefaultValue("") String searchString,
			@FormParam("page") @DefaultValue("1") int page) {

		try {

			refreshReader();
		} catch (final IOException e) {

			LOG.debug(e.getMessage());
			return Response.ok(ServerMessage.MESSAGE_CANT_OPEN_INDEX, MediaType.APPLICATION_JSON).build();
		}

		//final IndexSearcher indexSearcher = new IndexSearcher(this.indexReader);
		if (indexSearcher == null) {

			return Response.ok(ServerMessage.MESSAGE_INDEX_NOT_READY, MediaType.APPLICATION_JSON).build();
		}

    	LOG.debug("Searching for: " + searchString);

		final Query query = createQuery(searchString);

		LOG.debug("query: " + query);

		final TopScoreDocCollector collector = TopScoreDocCollector.create(MAX_SCORE_DOCS, true);
		ScoreDoc[] hits = new ScoreDoc[]{};
		try {

			int startIndex = (page - 1) * hitsPerPage;
			this.indexSearcher.search(query, collector);
			final TopDocs topDocs = collector.topDocs(startIndex, hitsPerPage);
			hits = topDocs.scoreDocs;
		} catch (final IOException e1) {

			LOG.debug(e1.getMessage());
		}

		final SearchResults results = extractResults(hits, indexSearcher);
		results.setTotalHits(collector.getTotalHits());
		results.setPages(getPages(collector.getTotalHits(), hitsPerPage));
		results.setCurrentPage(page);

		if (results.isEmpty()) {

			return Response.ok(ServerMessage.MESSAGE_NO_HITS, MediaType.APPLICATION_JSON).build();
		}

    	return Response.ok(results, MediaType.APPLICATION_JSON).build();
    }

	private SearchResults extractResults(final ScoreDoc[] hits, final IndexSearcher indexSearcher) {

		final SearchResults results = new SearchResults(hits.length);

		for (final ScoreDoc hit : hits) {

			final SearchResult res = new SearchResult();
			Document doc;

			try {

				doc = indexSearcher.doc(hit.doc);
				res.setTitle(doc.get("title"));
				res.setAuthor(doc.get("author"));
				res.setDescription(doc.get("description"));
				res.setFileName(doc.get("fileName"));
				res.setUri(doc.get("uri"));
				res.setContentType(doc.get("contentType"));
				results.add(res);
			} catch (final IOException e) {

				LOG.debug(e.getMessage());
			}
		}

		return results;
	}

	private void refreshReader() throws IOException {

		if (this.indexReader == null) {

			this.indexReader   = DirectoryReader.open(this.directory);
			this.indexSearcher = new IndexSearcher(this.indexReader);
		} else {

			final IndexReader newReader = DirectoryReader.openIfChanged((DirectoryReader) this.indexReader);
			if (newReader != null) {

	    		this.indexReader   = newReader;
	    		this.indexSearcher = new IndexSearcher(this.indexReader);
			}
		}
	}

	public synchronized Query createQuery(final String searchString) {

		Query query = new WildcardQuery(new Term("content:"));

		//final MultiFieldQueryParser parser = (MultiFieldQueryParser) context.getBean("parserProxyFactory");
		try {

			query = parser.parse(searchString);
		} catch (final ParseException e) {

			LOG.debug("ERROR when parsing query: " + searchString, e);
		}

		return query;
	}

	private int[] getPages(int totalHits, final int hitsPerPage) {

		if (totalHits > MAX_SCORE_DOCS) {

			totalHits = MAX_SCORE_DOCS;
		}

		int pagesNum = totalHits < hitsPerPage ? 
				1 : (( totalHits / hitsPerPage ) + ( totalHits % hitsPerPage == 0 ? 0 : 1 ));

		int[] pages = new int[pagesNum];
		for (int i = 0 ; i < pagesNum ; i++) {
			pages[i] = i + 1;
		}

		return pages;
	}
}
