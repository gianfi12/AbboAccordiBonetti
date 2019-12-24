package com.SafeStreets.model;

import java.util.Date;

public class QueryFilter {
    private Date from;
    private Date until;
    private Place place;

    public QueryFilter(Date from, Date until, Place place) {
        this.from = from;
        this.until = until;
        this.place = place;
    }

    public Date getFrom() {
        return from;
    }

    public Date getUntil() {
        return until;
    }

    public Place getPlace() {
        return place;
    }
}
