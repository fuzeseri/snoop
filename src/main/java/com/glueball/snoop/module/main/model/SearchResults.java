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
public class SearchResults implements Serializable, Iterable<SearchResult> {

    private static final long serialVersionUID = 1L;
    private List<SearchResult> results;
    private int totalHits;
    private int[] pages;
    private int currentPage;

    public SearchResults() {

        results = new ArrayList<SearchResult>();
    }

    public SearchResults(int size) {

        results = new ArrayList<SearchResult>(size);
    }

    public List<SearchResult> getResults() {

        return results;
    }

    public void setResults(List<SearchResult> results) {

        this.results = results;
    }

    public void add(final SearchResult res) {

        results.add(res);
    }

    @Override
    public Iterator<SearchResult> iterator() {

        return results.iterator();
    }

    public boolean isEmpty() {

        return results.isEmpty();
    }

    public int getTotalHits() {

        return totalHits;
    }

    public void setTotalHits(int totalHits) {

        this.totalHits = totalHits;
    }

    public int[] getPages() {

        return pages;
    }

    public void setPages(int[] pages) {

        this.pages = pages;
    }

    public int getCurrentPage() {

        return currentPage;
    }

    public void setCurrentPage(int currentPage) {

        this.currentPage = currentPage;
    }
}
