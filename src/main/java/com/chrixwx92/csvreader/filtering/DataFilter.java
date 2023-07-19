package com.chrixwx92.csvreader.filtering;

import com.chrixwx92.csvreader.deserialization.Csv;

import java.util.ArrayList;

import static com.chrixwx92.csvreader.filtering.DataFilter.Filter.GREATER_THAN_COLUMN;

public class DataFilter {

    private final String dataBreak = " - ";

    private Csv csv;

    private ArrayList<String> results;

    private ArrayList<String> filterColumn;

    public DataFilter(Csv csv) {
        this.csv = csv;
    }

    public String filter(String header, Filter filter, String query) {
        return this.filter(header, filter, query, (PreFilter) null);
    }

    public String filter(String header, Filter filter, String query, PreFilter... preFilters) {

        if (csv.isDeserialized()) {

            this.results = new ArrayList<>();
            ArrayList<String> comparedColumn;

            filterColumn(header);
            preFilter(preFilters);

            if (filter == GREATER_THAN_COLUMN) { comparedColumn = csv.getData().get(query);// TODO: If compared column doesn't exist - bad query
            }

            int counter = 0;
            int index = 0;

            for (String value : this.filterColumn) {
                index++;
                switch (filter) {
                    case EQUALS_TEXT -> {
                        if (value.equals(query)) {
                            counter++;
                            this.results.add(index + dataBreak + getName(index-1) + dataBreak + getCompanyName(index-1)); //TODO Generify
                        }
                    }
                    case CONTAINS_TEXT -> {
                        if (value.contains(query)) {
                            counter++;
                            this.results.add(index + dataBreak + getName(index-1) + dataBreak + getCompanyName(index-1)); //TODO Generify
                        }
                    }
                    case LENGTH_EQUALS -> {
                        int length;
                        try {
                            length = Integer.parseInt(query);
                            if (value.length() == length) {
                                counter++;
                                this.results.add(index + dataBreak + getName(index - 1) + dataBreak + getCompanyName(index - 1)); //TODO Generify
                            }
                        }
                        catch(NumberFormatException nfe) { /*TODO: Incorrect query */}
                    }
                    case LENGTH_EXCEEDS -> {
                        int length;
                        try {
                            length = Integer.parseInt(query);
                            if (value.length() > length) {
                                counter++;
                                this.results.add(index + dataBreak + getName(index-1) + dataBreak + getCompanyName(index-1)); //TODO Generify
                            }
                        }
                        catch(NumberFormatException nfe) { /*TODO: Incorrect query */}
                    }
                    case GREATER_THAN_COLUMN -> {
                        if (Integer.parseInt(value.replaceAll("[^\\d]", "")) < Integer.parseInt(comparedColumn.get(index-1).replaceAll("[^\\d]", ""))) {
                            counter++;
                            this.results.add(index + dataBreak + getName(index-1) + dataBreak + getCompanyName(index-1)); //TODO Generify
                        }
                    }
                }

            }

            return writeOutput(counter);

        }

        return null;

    }

    private void filterColumn(String header) {
        this.filterColumn = csv.getData().get(header);

        if (this.filterColumn.isEmpty()) {
            //column is empty, or doesn't exist
        }
    }

    public String initialWordLengthOnlyNumbersEquals(String column, int length) {

        if (csv.isDeserialized()) {

            this.results = new ArrayList<>();

            ArrayList<String> columnValues = csv.getData().get(column);

            if (columnValues.isEmpty()) {
                //column is empty, or doesn't exist
            }

            int counter = 0;
            int index = 0;



            return writeOutput(counter);

        }

        return null;

    }

    // Three generic types of filters required:

    // Pre-filter can change column contents for querying:
    // Filters: Truncate, numerical data only

    // Column query - subtypes: contains text, equals text, data length size assessment
    // Column comparison - subtypes: Numerical

//                    case 1 -> System.out.println(dataFilter.stringContains("company_name", "Esq"));
//                case 2 -> System.out.println(dataFilter.stringContains("county", "Derbyshire"));
//                case 3 -> System.out.println(dataFilter.initialWordLengthEquals("address", 3));
//                case 4 -> System.out.println(dataFilter.lengthExceeds("web", 35));
//                case 5 -> System.out.println(dataFilter.initialWordLengthOnlyNumbersEquals("postal", 1));
//                case 6 -> System.out.println(dataFilter.numericalDataNonEqualsComparison("phone2", "phone1"));

    private void preFilter(PreFilter... preFilters) {
        for (PreFilter preFilter : preFilters) {
            ArrayList<String> newFilterColumn = new ArrayList<>();
            for (String value : this.filterColumn) {
                switch (preFilter) {
                    case TRUNCATE -> newFilterColumn.add(value.substring(0, value.indexOf(" ")).replaceAll("[^\\d]", ""));
                    case NUMERICAL -> newFilterColumn.add(value.replaceAll("[^\\d]", ""));
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

    public enum Filter {
        CONTAINS_TEXT,
        EQUALS_TEXT,
        LENGTH_EQUALS,
        LENGTH_EXCEEDS,
        GREATER_THAN_COLUMN;
    }

    public enum PreFilter {
        TRUNCATE,
        NUMERICAL;
    }


}