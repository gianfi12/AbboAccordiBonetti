package com.SafeStreets.model;

import java.time.LocalDate;

/**
 * It represent a filter to apply to a query to the database.
 * It can consist to a pure query to apply as-is or to three filters: from, until and place to use on
 * another query created by the user of this class
 */
public class QueryFilter {
    /**
     * It indicates whether to apply the query in the attribute query as-is, without other additions
     */
    private boolean isApplyQuery;
    /**
     * It contains an query to the database
     */
    private String query;
    /**
     * It indicates whether the result of the query contains only one attribute (or column)
     */
    private boolean isOneResult;
    /**
     * It indicates whether to use as filter the attributes from, until and place
     */
    private boolean isApplyFilter;
    /**
     * Starting date
     */
    private LocalDate from;
    /**
     * Ending date
     */
    private LocalDate until;
    /**
     * Place
     */
    private Place place;

    /**
     * It construct one QueryFilter with the given query and isOneResult
     * @param query new query
     * @param isOneResult whether the result of the query contains only one attribute (or column)
     */
    public QueryFilter(String query, boolean isOneResult) {
        this.isApplyQuery=true;
        this.query = query;
        this.isOneResult=isOneResult;
        this.isApplyFilter=false;
    }

    /**
     * It construct one QueryFilter with the from date, until date and place
     * @param from starting date
     * @param until ending date
     * @param place place
     */
    public QueryFilter(LocalDate from, LocalDate until, Place place) {
        this.isApplyQuery=false;
        this.isApplyFilter=true;
        this.from = from;
        this.until = until;
        this.place = place;
    }

    /**
     * It indicates whether to use as filter the attributes from, until and place
     * @return whether to use as filter the attributes from, until and place
     */
    public boolean isApplyQuery() {
        return isApplyQuery;
    }

    /**
     * It returns the query
     * @return query
     */
    public String getQuery() {
        return query;
    }

    /**
     * It indicates whether the result of the query contains only one attribute (or column)
     * @return whether the result of the query contains only one attribute (or column)
     */
    public boolean isOneResult() {
        return isOneResult;
    }

    /**
     * It indicates whether to use as filter the attributes from, until and place
     * @return whether to use as filter the attributes from, until and place
     */
    public boolean isApplyFilter() {
        return isApplyFilter;
    }

    /**
     * It returns the from date
     * @return from date
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * It returns the until date
     * @return until date
     */
    public LocalDate getUntil() {
        return until;
    }

    /**
     * It returns the place
     * @return place
     */
    public Place getPlace() {
        return place;
    }
}
