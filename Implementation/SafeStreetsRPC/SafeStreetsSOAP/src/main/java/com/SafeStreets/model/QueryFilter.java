package com.SafeStreets.model;

import java.time.LocalDate;

public class QueryFilter {
    private boolean isApplyQuery;
    private String query;
    private boolean isOneResult;
    private boolean isApplyFilter;
    private LocalDate from;
    private LocalDate until;
    private Place place;

    public QueryFilter(String query, boolean isOneResult) {
        this.isApplyQuery=true;
        this.query = query;
        this.isOneResult=isOneResult;
        this.isApplyFilter=false;
    }

    public QueryFilter(LocalDate from, LocalDate until, Place place) {
        this.isApplyQuery=false;
        this.isApplyFilter=true;
        this.from = from;
        this.until = until;
        this.place = place;
    }

    public boolean isApplyQuery() {
        return isApplyQuery;
    }

    public String getQuery() {
        return query;
    }

    public boolean isOneResult() {
        return isOneResult;
    }

    public boolean isApplyFilter() {
        return isApplyFilter;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getUntil() {
        return until;
    }

    public Place getPlace() {
        return place;
    }
}
