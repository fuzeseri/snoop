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
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.QueryParserHelper;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;

import com.glueball.snoop.module.main.model.SearchResult;
import com.glueball.snoop.module.main.model.SearchResults;

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

    @Autowired
    private Analyzer analyzer;
    
	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	@Autowired
	private QueryParserHelper queryParserHelper;

	public void setQueryParserHelper(final QueryParserHelper _queryParserHelper) {
		this.queryParserHelper = _queryParserHelper;
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

		this.queryParserHelper.getQueryConfigHandler().set(ConfigurationKeys.ALLOW_LEADING_WILDCARD, true);
		this.queryParserHelper.getQueryConfigHandler().set(ConfigurationKeys.ANALYZER, this.analyzer);

		if (searchString == null) {
			searchString = "";
		}
		refreshReader();

    	LOG.debug("Searching for: " + searchString);

		final Query query = createQuery(searchString);

		LOG.debug("query: " + query);

		ScoreDoc[] hits = new ScoreDoc[]{};
		try {
			hits = indexSearcher.search(query, null, 100).scoreDocs;
		} catch (final IOException e1) {
			LOG.debug(e1.getMessage());
		}

    	return Response.ok(extractResults(hits, indexSearcher), MediaType.APPLICATION_JSON).build();
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

	   	IndexReader newReader = null;
		try {
			newReader = DirectoryReader.openIfChanged((DirectoryReader) indexReader);
		} catch (final IOException e2) {
			LOG.debug(e2.getMessage());
		}

    	if (newReader != null) {
    		this.indexReader = newReader;
    	}

		this.indexSearcher = new IndexSearcher(indexReader);
	}

	public synchronized Query createQuery(final String searchString) {

//		String fileNameQuery = "";
//		if (!StringUtils.isEmpty(searchString)) {
//			String[] words = searchString.split("\\s");
//			for (String word : words) {
//				fileNameQuery += "fileName:" + word + " ";
//			}
//		}
	
		Query query = new WildcardQuery(new Term("content:"));
		if (searchString.startsWith("file:")) {
			final String[] parts = searchString.split(":");
			query = new WildcardQuery(new Term("fileName", "*" + parts[1].trim() + "*"));
		} else {

			try {
				query = (Query) queryParserHelper.parse(searchString, "content");
			} catch (final QueryNodeException e2) {
				LOG.debug("ERROR when parsing query: " + searchString,e2);			
			}
		}
		return query;
	}
}
