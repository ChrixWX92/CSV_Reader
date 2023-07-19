package com.chrixwx92.csvreader.filtering;

import com.chrixwx92.csvreader.deserialization.Csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static com.chrixwx92.csvreader.filtering.Filter.*;

public class CsvDataFilter {

    private final String dataBreak = " - ";

    private Csv csv;

    private ArrayList<String> results;

    private ArrayList<String> filterColumn;
    private ArrayList<String> compareColumn;

    public CsvDataFilter(Csv csv) {
        this.csv = csv;
    }

    public String filter(String header, Filter filter, String query) {
        return this.filter(header, filter, query, (PreFilter) null);
    }

    public String filter(String header, Filter filter, String query, PreFilter... preFilters) {

        if (csv.isDeserialized()) {

            this.results = new ArrayList<>();

            fetchColumns(header, filter, query);
            if (!Arrays.stream(preFilters).allMatch(Objects::isNull)) {
                preFilter(preFilters);
            }

            int counter = 0;
            int index = 0;

            for (String value : this.filterColumn) {

                index++;
                int currentCounter = counter;

                switch (filter) {
                    case EQUALS_TEXT -> {
                        if (value.equals(query)) counter++;
                    }
                    case CONTAINS_TEXT -> {
                        if (value.contains(query)) counter++;
                    }
                    case LENGTH_EQUALS -> {
                        int length;
                        try {
                            length = Integer.parseInt(query);
                            if (value.length() == length) counter++;
                        }
                        catch(NumberFormatException nfe) { throw new InvalidFilteringQueryException(); }
                    }
                    case LENGTH_EXCEEDS -> {
                        int length;
                        try {
                            length = Integer.parseInt(query);
                            if (value.length() > length) counter++;
                        }
                        catch(NumberFormatException nfe) { throw new InvalidFilteringQueryException(); }
                    }
                    case GREATER_THAN_COLUMN -> {
                        if (Integer.parseInt(value) > Integer.parseInt(compareColumn.get(index-1).replaceAll("[^\\d]", ""))) {
                            counter++;
                        }
                    }
                }
                if (currentCounter < counter) {
                    this.results.add(index + dataBreak + getName(index-1) + dataBreak + getCompanyName(index-1));
                }
            }

            return writeOutput(counter);

        }

        return null;

    }

    private void fetchColumns(String header, Filter filter, String query) {

        try {

            this.filterColumn = csv.getData().get(header);
            if (filter == GREATER_THAN_COLUMN) {
                this.compareColumn = csv.getData().get(query);
            }

        }

        catch (NullPointerException npe) {
            throw new InvalidFilteringQueryException();
        }

    }

    private void preFilter(PreFilter... preFilters) {
        for (PreFilter preFilter : preFilters) {
            ArrayList<String> newFilterColumn = new ArrayList<>();
            if (preFilter != null) {
                for (String value : this.filterColumn) {
                    switch (preFilter) {
                        case TRUNCATE -> newFilterColumn.add(value.substring(0, value.indexOf(" ")));
                        case NUMERICAL -> newFilterColumn.add(value.replaceAll("[^\\d]", ""));
                    }
                }
            }
            this.filterColumn = newFilterColumn;
        }
    }

    public String writeOutput(int counter) {
        StringBuilder output = new StringBuilder("Count: " + counter + "\n\n");

        for (String result : results) {
            output.append(result).append("\n");
        }

        return output.toString();
    }

    private String getName(int index) {
        return csv.getData().get("first_name").get(index) + " " + csv.getData().get("last_name").get(index);
    }

    private String getCompanyName(int index) {
        return csv.getData().get("company_name").get(index);
    }

}