package com.chrixwx92.csvreader.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface CsvParser {

    String lookaheadRegex = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    HashMap<String, ArrayList<String>> parse(File csv) throws IOException;

}
