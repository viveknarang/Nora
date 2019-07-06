package com.viveknarang.nora.main;

import com.univocity.parsers.common.processor.ConcurrentRowProcessor;
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vivek Narang
 */
public class Extractor {

    final static Logger logger = Logger.getLogger(Extractor.class);

    public static String[] headers;
    private List<String[]> rows;
    private File file;

    public Extractor(File file) {
        super();
        this.file = file;
    }

    public void extract() {

        logger.info("Parsing CSV file: " + file.getAbsolutePath());
        long s = System.currentTimeMillis();

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setLineSeparatorDetectionEnabled(true);

        CsvFormat format = new CsvFormat();
        format.setDelimiter(',');
        format.setLineSeparator("\n");
        format.setCharToEscapeQuoteEscaping('\0');
        format.setQuote('"');
        parserSettings.setFormat(format);

        RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setProcessor(new ConcurrentRowProcessor(rowProcessor));
        parserSettings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(parserSettings);
        parser.parse(file);
        headers = rowProcessor.getHeaders();
        rows = rowProcessor.getRows();

        long e = System.currentTimeMillis();
        logger.info(headers.length + " headers, " + rows.size() + " rows loaded in " + ((e - s) / 1000) + " seconds. The throughput is: " + rows.size() / ((e - s) / 1000) + " rows/sec");
        logger.info(Arrays.toString(headers));
        logger.info("Parsing complete for file: " + file.getAbsolutePath());
    }

    public String[] getHeaders() {
        return headers;
    }

    public List<String[]> getRows() {
        return rows;
    }

}
