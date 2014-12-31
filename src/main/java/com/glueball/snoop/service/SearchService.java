package com.glueball.snoop.service;

import java.io.IOException;

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
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;

import com.glueball.snoop.module.main.model.SearchResult;
import com.glueball.snoop.module.main.model.SearchResults;
import com.glueball.snoop.module.main.model.ServerMessage;

@Path("/search")
public class SearchService<QueryParser> {
	
	private static Logger LOG = LogManager.getLogger(SearchService.class);
	
	@Autowired
	private Directory directory;

	public void setDirectory(final Directory directory) {
		this.directory = directory;
	}

	private IndexReader indexReader;
	
	private IndexSearcher indexSearcher;

//    @Autowired
//    private Analyzer analyzer;
//    
//	public void setAnalyzer(final Analyzer analyzer) {
//		this.analyzer = analyzer;
//	}

	@Autowired
	private MultiFieldQueryParser parser;

	public void setParser(final MultiFieldQueryParser parser) {
		this.parser = parser;
	}

	public void init() {
		try {
			indexReader = DirectoryReader.open(directory);
		} catch (final IOException e) {
			LOG.info("No directroy available");
			LOG.debug(e.getMessage());
		}
	}

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{keyword}")
	public Response search(@PathParam(value = "keyword") String searchString) {

		refreshReader();
		if (this.indexSearcher == null) {

			return Response.ok(ServerMessage.MESSAGE_INDEX_NOT_READY, MediaType.APPLICATION_JSON).build();
		}

		if (searchString == null) {

			searchString = "";
		}

    	LOG.debug("Searching for: " + searchString);

		final Query query = createQuery(searchString);

		LOG.debug("query: " + query);

		ScoreDoc[] hits = new ScoreDoc[]{};
		try {

			hits = indexSearcher.search(query, null, 100).scoreDocs;
		} catch (final IOException e1) {

			LOG.debug(e1.getMessage());
		}

		final SearchResults results = extractResults(hits, indexSearcher);
		if (results.isEmpty()) {

			return Response.ok(ServerMessage.MESSAGE_NO_HITS, MediaType.APPLICATION_JSON).build();
		}

    	return Response.ok(results, MediaType.APPLICATION_JSON).build();
    }

	private SearchResults extractResults(final ScoreDoc[] hits, final IndexSearcher indexSearcher) {

		final SearchResults results = new SearchResults();

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

	private synchronized void refreshReader() {

		if (this.indexReader == null) {

			try {

				this.indexReader = DirectoryReader.open(directory);
			} catch (IOException e) {

				LOG.info("Can't open index");
				LOG.debug(e);
			}
		} else {

		   	IndexReader newReader = null;
			try {

				newReader = DirectoryReader.openIfChanged((DirectoryReader) indexReader);
			} catch (final IOException e2) {

				LOG.debug(e2.getMessage());
			}
	
	    	if (newReader != null) {

	    		this.indexReader = newReader;
	    	}
		}

		this.indexSearcher = new IndexSearcher(indexReader);
	}

	public synchronized Query createQuery(final String searchString) {

		Query query = new WildcardQuery(new Term("content:"));
		try {

			query = parser.parse(searchString);
		} catch (final ParseException e) {

			LOG.debug("ERROR when parsing query: " + searchString, e);
		}

		return query;
	}
}
