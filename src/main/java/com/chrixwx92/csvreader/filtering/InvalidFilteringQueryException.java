package com.chrixwx92.csvreader.filtering;

public class InvalidFilteringQueryException extends IllegalArgumentException {
    public InvalidFilteringQueryException() {
        super("Invalid arguments passed to selected filtering option.");
    }
}
