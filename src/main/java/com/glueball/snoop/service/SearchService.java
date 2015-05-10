package com.glueball.snoop.service;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.IOException;
import java.net.URISyntaxException;

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

/**
 * Restful document search service.
 *
 * @author karesz
 */
@Path("/search")
public class SearchService {

    /**
     * Logger instance.
     */
    private static Logger LOG = LogManager
            .getLogger(SearchService.class);

    /**
     * Maximum number of document selected from the index.
     */
    private static final int MAX_SCORE_DOCS = 1000;

    /**
     * Default maximum number of hits on a page.
     */
    private static final int DEFAULT_HITS_PER_PAGE = 50;
    /**
     * The lucene directory object.
     */
    @Autowired
    private Directory directory;

    /**
     * @param pDirectory
     *            the directori instance to set
     */
    public final void setDirectory(final Directory pDirectory) {

        this.directory = pDirectory;
    }

    /**
     * Lucene index reader object.
     */
    private IndexReader indexReader;

    /**
     * Lucene index searcher object.
     */
    private IndexSearcher indexSearcher;

    /**
     * Lucene MultiFieldQueryParser object.
     */
    @Autowired
    private MultiFieldQueryParser parser;

    /**
     * @param pParser
     *            the query parser instance to set.
     */
    public final void setParser(final MultiFieldQueryParser pParser) {

        this.parser = pParser;
    }

    /**
     * The maximum number of hits on a page.
     */
    private int hitsPerPage = DEFAULT_HITS_PER_PAGE;

    /**
     * @param pHitsPerPage
     *            the number of hits per page to set.
     */
    public final void setHitsPerPage(final int pHitsPerPage) {

        this.hitsPerPage = pHitsPerPage;
    }

    /**
     * Initialization method.
     */
    public final void init() {

        try {

            refreshReader();
        } catch (final IOException e) {

            LOG.info("No directroy available");
            LOG.debug(e.getMessage());
        }
    }

    /**
     * Restful json search service.
     *
     * @param searchString
     *            the text to search for.
     * @param page
     *            the exact page to get.
     * @return HTTP response with the json representation of the SearchResults
     *         entity.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{keyword}")
    public final Response search(
            @PathParam(
                    value = "keyword") @DefaultValue("") String searchString,
            @FormParam("page") @DefaultValue("1") int page) {

        try {

            refreshReader();
        } catch (final IOException e) {

            LOG.debug(e.getMessage());
            return Response.ok(ServerMessage.MESSAGE_CANT_OPEN_INDEX,
                    MediaType.APPLICATION_JSON).build();
        }

        if (indexSearcher == null) {

            return Response.ok(ServerMessage.MESSAGE_INDEX_NOT_READY,
                    MediaType.APPLICATION_JSON).build();
        }

        LOG.debug("Searching for: " + searchString);

        final Query query = createQuery(searchString);

        LOG.debug("query: " + query);

        final TopScoreDocCollector collector = TopScoreDocCollector.create(
                MAX_SCORE_DOCS);
        ScoreDoc[] hits = new ScoreDoc[] {};
        try {

            final int startIndex = (page - 1) * hitsPerPage;
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

            return Response.ok(ServerMessage.MESSAGE_NO_HITS,
                    MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(results, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Extracts the search result from the hits array to the a SearchResult
     * object.
     *
     * @param hits
     *            the array on results provided by the index.
     * @param indexSearcher
     *            the lucene IndexSearcher object.
     * @return the SearchResults object.
     * @throws URISyntaxException
     */
    private final SearchResults extractResults(final ScoreDoc[] hits,
            final IndexSearcher indexSearcher) {

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
                res.setUri(doc.get("path"));
                res.setContentType(doc.get("contentType"));
                results.add(res);
            } catch (final IOException e) {

                LOG.debug(e.getMessage());
            }
        }

        return results;
    }

    /**
     * Reset the index reader filed if it was null or the index has changed.
     *
     * @throws IOException
     *             on unsuccessful read of the index files.
     */
    private final void refreshReader() throws IOException {

        if (this.indexReader == null) {

            this.indexReader = DirectoryReader.open(this.directory);
            this.indexSearcher = new IndexSearcher(this.indexReader);
        } else {

            final IndexReader newReader = DirectoryReader
                    .openIfChanged((DirectoryReader) this.indexReader);
            if (newReader != null) {

                this.indexReader = newReader;
                this.indexSearcher = new IndexSearcher(this.indexReader);
            }
        }
    }

    /**
     * Creates a lucene query from the search string.
     *
     * @param searchString
     *            the search string received from the client.
     * @return the lucene query.
     */
    public final synchronized Query createQuery(final String searchString) {

        Query query = new WildcardQuery(new Term("content:"));
        try {

            query = parser.parse(searchString);
        } catch (final ParseException e) {

            LOG.debug("ERROR when parsing query: " + searchString, e);
        }

        return query;
    }

    /**
     * Provides the array of page numbers of a result set.
     *
     * @param totalHits
     *            the number of the total hits matched in the index at the
     *            search.
     * @param hitsPerPage
     *            the maximum allowed number of results aon single page.
     * @return the array of page numbers.
     */
    private static final int[] getPages(final int totalHits,
            final int hitsPerPage) {

        int total = totalHits > MAX_SCORE_DOCS ? MAX_SCORE_DOCS : totalHits;

        int pagesNum = total < hitsPerPage ? 1
                : ((total / hitsPerPage) + (total % hitsPerPage == 0 ? 0
                        : 1));

        int[] pages = new int[pagesNum];
        for (int i = 0; i < pagesNum; i++) {
            pages[i] = i + 1;
        }

        return pages;
    }
}
