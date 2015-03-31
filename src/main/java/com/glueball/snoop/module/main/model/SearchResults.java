package com.glueball.snoop.module.main.model;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(
        name = "results")
@JsonIgnoreProperties(
        ignoreUnknown = true)
public final class SearchResults implements Serializable,
        Iterable<SearchResult> {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * List of the search results.
     */
    private List<SearchResult> results;

    /**
     * The total hits.
     */
    private int totalHits;

    /**
     * Number of the pages.
     */
    private int[] pages;

    /**
     * The current page.
     */
    private int currentPage;

    /**
     * Default Constructor.
     */
    public SearchResults() {

    }

    /**
     * Constructor.
     * 
     * @param size
     *            the size of the result set.
     */
    public SearchResults(int size) {

        results = new ArrayList<SearchResult>(size);
    }

    /**
     * @param res
     *            add a single search result ot the result set.
     */
    public void add(final SearchResult res) {

        results.add(res);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<SearchResult> iterator() {

        return results.iterator();
    }

    /**
     * @return true if the result set is empty.
     */
    public boolean isEmpty() {

        return results.isEmpty();
    }

    /**
     * @return the results
     */
    public List<SearchResult> getResults() {

        return results;
    }

    /**
     * @param pResults
     *            the results to set
     */
    public void setResults(final List<SearchResult> pResults) {

        this.results = pResults;
    }

    /**
     * @return the totalHits
     */
    public int getTotalHits() {

        return totalHits;
    }

    /**
     * @param pTotalHits
     *            the totalHits to set
     */
    public void setTotalHits(final int pTotalHits) {

        this.totalHits = pTotalHits;
    }

    /**
     * @return the pages
     */
    public int[] getPages() {

        return pages;
    }

    /**
     * @param pPages
     *            the pages to set
     */
    public void setPages(final int[] pPages) {

        this.pages = pPages;
    }

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {

        return currentPage;
    }

    /**
     * @param pCurrentPage
     *            the currentPage to set
     */
    public void setCurrentPage(final int pCurrentPage) {

        this.currentPage = pCurrentPage;
    }
}
