package com.glueball.snoop.module.main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "results")
public class SearchResults implements Serializable, Iterable<SearchResult> {

	private static final long serialVersionUID = 1L;
	private List<SearchResult> results = new ArrayList<SearchResult>();
	private int totalHits;
	private int[] pages;
	private int   currentPage;

	public List<SearchResult> getResults() {
		return results;
	}

	public void setResults(List<SearchResult> results) {
		this.results = results;
	}

	public void add(final SearchResult res) {
		results.add(res);
	}

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
