package com.theepicsplit.state;

public class FilterState {
    public boolean isFilteringEnabled;
    public String filteringPattern;

    public FilterState() {
    }

    public FilterState(boolean isFilteringEnabled, String filteringPattern) {
        this.isFilteringEnabled = isFilteringEnabled;
        this.filteringPattern = filteringPattern;
    }
}
