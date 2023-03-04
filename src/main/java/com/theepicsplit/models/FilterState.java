package com.theepicsplit.models;

public class FilterState {
	public boolean isFilterEnabled;
	public String filterRegex;

	public FilterState() {
	}

	public FilterState(boolean isFilterEnabled, String filterRegex) {
		this.isFilterEnabled = isFilterEnabled;
		this.filterRegex = filterRegex;
	}
}
