package com.SafeStreets.model;

import java.time.LocalDate;

public class QueryFilter {
    private LocalDate from;
    private LocalDate until;
    private Place place;

    public QueryFilter(LocalDate from, LocalDate until, Place place) {
        this.from = from;
        this.until = until;
        this.place = place;
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
