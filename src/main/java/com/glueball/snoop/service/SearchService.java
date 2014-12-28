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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.QueryBuilder;
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

    @Autowired
    private Analyzer analyzer;
    
	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
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
	public Response search(@PathParam(value = "keyword") final String searchString) {

    	final SearchResults results = new SearchResults();

    	IndexReader newReader = null;
		try {
			newReader = DirectoryReader.openIfChanged((DirectoryReader) indexReader);
		} catch (final IOException e2) {
			LOG.debug(e2.getMessage());
		}

    	if (newReader != null) {
    		this.indexReader = newReader;
    	}

		final IndexSearcher indexSearcher = new IndexSearcher(indexReader);

    	final QueryBuilder queryBuilder = new QueryBuilder(analyzer);
		final Query query = queryBuilder.createPhraseQuery("content", searchString);

		LOG.debug("Searching for: " + query.toString());

		ScoreDoc[] hits = new ScoreDoc[]{};
		try {
			hits = indexSearcher.search(query, null, 1000).scoreDocs;
		} catch (IOException e1) {
			LOG.debug(e1.getMessage());
		}

		final SearchResult res = new SearchResult();
		for (final ScoreDoc hit : hits) {
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

    	return Response.ok(results, MediaType.APPLICATION_JSON).build();
    }
}
