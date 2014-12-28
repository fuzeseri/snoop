package com.glueball.snoop.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.glueball.snoop.entity.SearchResult;

@Path("/search")
public class SearchService {
	
	@Autowired
	private IndexReader indexReader;

    public void setIndexReader(IndexReader indexReader) {
		this.indexReader = indexReader;
	}

    @Autowired
    private Analyzer analyzer;
    
	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{keyword}")
	public Response getById(@PathParam(value = "keyword") final String searchString) {

    	final List<SearchResult> result = new ArrayList<SearchResult>();

    	IndexReader indexReader = new IndexReader();
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		Analyzer analyzer = new StandardAnalyzer();
    	
    	
    	final QueryParser queryParser = new QueryParser(analyzer);
		final Query query = queryParser.parse(searchString);
		final Hits hits = indexSearcher.search(query);

		Iterator<Hit> it = hits.iterator();
		while (it.hasNext()) {
			final Hit hit = it.next();
			final Document doc = hit.getDocument();
			final SearchResult res = new SearchResult();
			res.setTitle(doc.get("title"));
			res.setAuthor(doc.get("author"));
			res.setDescription(doc.get("description"));
			res.setFileName(doc.get("fileName"));
			res.setUri(doc.get("uri"));
			res.setContentType(doc.get("contentType"));
		}
    	
    	return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }
}
