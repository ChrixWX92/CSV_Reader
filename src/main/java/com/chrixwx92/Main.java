package com.chrixwx92;

import com.chrixwx92.csvreader.deserialization.Csv;
import com.chrixwx92.csvreader.deserialization.Utils;
import com.chrixwx92.csvreader.filtering.DataFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.chrixwx92.csvreader.filtering.DataFilter.Filter.*;
import static com.chrixwx92.csvreader.filtering.DataFilter.PreFilter.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Csv csv = Utils.createCsvFromSample();

        DataFilter dataFilter = new DataFilter(csv);

        System.out.println("Please select an option to filter CSV results:");

        Map<Integer, String> options = new HashMap<>();
        options.put(1, "Company names containing \"Esq\"");
        options.put(2, "County: Derbyshire");
        options.put(3, "House number length: 3 digits");
        options.put(4, "URL length: >35 characters");
        options.put(5, "Postcode area value: single-digit");
        options.put(6, "First phone number > second phone number");

        for (Map.Entry<Integer, String> entry : options.entrySet()) {System.out.println(entry.getKey() + ". " + entry.getValue());}

        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        if (options.containsKey(input)) {
            switch (input) {
                case 1 -> System.out.println(dataFilter.filter("company_name", CONTAINS_TEXT, "Esq"));
                case 2 -> System.out.println(dataFilter.filter("county", EQUALS_TEXT, "Derbyshire"));
                case 3 -> System.out.println(dataFilter.filter("address", LENGTH_EQUALS, "3", TRUNCATE));
                case 4 -> System.out.println(dataFilter.filter("web", LENGTH_EXCEEDS, "35"));
                case 5 -> System.out.println(dataFilter.filter("postal", LENGTH_EQUALS, "1", TRUNCATE, NUMERICAL));
                case 6 -> System.out.println(dataFilter.numericalDataNonEqualsComparison("phone2", "phone1"));
            }
        } else {
            System.out.println("Invalid input. Please try choose between 1 and 6.");
        }
        scanner.close();

    }

}