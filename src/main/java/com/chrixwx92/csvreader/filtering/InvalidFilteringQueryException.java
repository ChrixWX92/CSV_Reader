package com.chrixwx92.csvreader.filtering;

public class InvalidFilteringQueryException extends IllegalArgumentException {
    public InvalidFilteringQueryException() {
        super("Invalid argument(s) passed to selected filtering option.");
    }
}
