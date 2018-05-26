package MassiveETL.MassiveETL;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.univocity.parsers.common.processor.ConcurrentRowProcessor;
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class Extractor implements Runnable {
	
	final static Logger logger = Logger.getLogger(Extractor.class);

	private String[] headers;
	private List<String[]> rows;
	private File file;

	public Extractor(File file) {
		super();
		this.file = file;
	}

	public void run() {
		
		logger.info("Parsing CSV file: " + file.getAbsolutePath());
		long s = System.currentTimeMillis();
		// The settings object provides many configuration options
		CsvParserSettings parserSettings = new CsvParserSettings();

		// You can configure the parser to automatically detect what line
		// separator sequence is in the input
		parserSettings.setLineSeparatorDetectionEnabled(true);

		// A RowListProcessor stores each parsed row in a List.
		RowListProcessor rowProcessor = new RowListProcessor();

		// You can configure the parser to use a RowProcessor to process the
		// values of each parsed row.
		// You will find more RowProcessors in the
		// 'com.univocity.parsers.common.processor' package, but you can also
		// create your own.
		parserSettings.setProcessor(new ConcurrentRowProcessor(rowProcessor));

		// Let's consider the first parsed row as the headers of each column in
		// the file.
		parserSettings.setHeaderExtractionEnabled(true);

		// creates a parser instance with the given settings
		CsvParser parser = new CsvParser(parserSettings);

		// the 'parse' method will parse the file and delegate each parsed row
		// to the RowProcessor you defined
		parser.parse(file);

		// get the parsed records from the RowListProcessor here.
		// Note that different implementations of RowProcessor will provide
		// different sets of functionalities.
		headers = rowProcessor.getHeaders();
		rows = rowProcessor.getRows();
		
		long e = System.currentTimeMillis();
		
		logger.info(headers.length + " headers, " + rows.size() + " rows processed in " + ((e-s)/1000) + " seconds. The throughput is: " + rows.size()/((e-s)/1000) + " rows/sec");
		
		logger.info(Arrays.toString(headers));
		
		logger.info("Parsing complete for file: " + file.getAbsolutePath());

	}

}
