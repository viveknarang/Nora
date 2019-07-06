package com.viveknarang.nora.main;

import java.util.*;
import java.util.regex.Pattern;

import com.viveknarang.nora.model.ETLJob;
import org.apache.log4j.Logger;

import com.viveknarang.nora.model.Rule;

/**
 * 
 * @author Vivek Narang
 *
 */
public class Transformer {

	private static HashMap<Integer, TreeMap<Integer, List<String>>> rulesMap = new HashMap<>();
	private static HashMap<Integer, Rule> metaMap = new HashMap<>();

	private final static Logger logger = Logger.getLogger(Transformer.class);

	public Transformer() {
		super();
	}

	public static List<List<String>> transformedRows = new LinkedList<>();
	public static List<String> transformedRowsHeader = new LinkedList<>();

	public static void transform(ETLJob job, List<Rule> rules, List<String[]> rows, String fileName) {

		logger.info("Transformer:transform()::Start");
		long s = System.currentTimeMillis();
		rulesMap = new HashMap<>();

		int i;

		for (Rule rule : rules) {

			metaMap.put(rule.getFromFieldIndex(), rule);

			TreeMap<Integer, List<String>> rules$ = new TreeMap<>();

			if (rule.getFileName().equals(fileName)) {

				for (int ti = 0; ti < rule.getTransform().size(); ti++) {
					rules$.put(Integer.parseInt(rule.getTransform().get(ti).get(0)), rule.getTransform().get(ti));
				}

				rulesMap.put(rule.getFromFieldIndex(), rules$);
			}

		}

		int r = 0;

		for (String[] row : rows) {

			List<String> lst = new LinkedList<>();

			for (i = 0; i < row.length; i++) {

				if (rulesMap.containsKey(i)) {

					if (metaMap.get(i).getOverwrite().equalsIgnoreCase("true")) {

						if (r == 0) {
							if (metaMap.get(i) != null && metaMap.get(i).getMapToField() != null) {
								transformedRowsHeader.add(metaMap.get(i).getMapToField());
							} else {
								transformedRowsHeader.add(Extractor.headers[i]);
							}
						}

						lst.add(transform(row[i], rulesMap.get(i)));

					} else {

						lst.add(row[i]);
						lst.add(transform(row[i], rulesMap.get(i)));

						if (r == 0) {
							transformedRowsHeader.add(Extractor.headers[i]);
							transformedRowsHeader.add(metaMap.get(i).getMapToField());
						}
					}

				} else {

					if (r == 0) {
						transformedRowsHeader.add(Extractor.headers[i]);
					}

					lst.add(row[i]);
				}
			}

			transformedRows.add(lst);

			r++;

		}

		long e = System.currentTimeMillis();
		logger.info("Transformer:transform()::Complete >> Transformation completed in: " + ((e - s) / 1000)
				+ " seconds on a total of: " + rows.size() + " records & " + rulesMap.size() + " fields...");

	}

	private static String transform(String field, TreeMap<Integer, List<String>> rule_) {

		for (Integer rule : rule_.keySet()) {
			if (rule_.get(rule).get(1).equals("replaceNullWith") && field.length() == 0
					&& rule_.get(rule).get(2).equals("true")) {
				field = rule_.get(rule).get(3);
			} else {

				if (rule_.get(rule).get(1).equals("truncateAfter") && rule_.get(rule).get(2).equals("true")) {
					if (field.length() > Integer.parseInt(rule_.get(rule).get(3))) {
						field = field.substring(0, Integer.parseInt(rule_.get(rule).get(3))) + rule_.get(rule).get(4);
					}
				} else if (rule_.get(rule).get(1).equals("replace") && rule_.get(rule).get(2).equals("true")) {
					field = field.replaceAll(rule_.get(rule).get(3), rule_.get(rule).get(4));
				} else if (rule_.get(rule).get(1).equals("remove") && rule_.get(rule).get(2).equals("true")) {
					field = field.replace(rule_.get(rule).get(3), "");
				} else if (rule_.get(rule).get(1).equals("extractFromEndTo") && rule_.get(rule).get(2).equals("true")
						&& field.contains(rule_.get(rule).get(3))) {
					String[] px = field.split(Pattern.quote(rule_.get(rule).get(3)));
					field = px[px.length - 1];
				} else if (rule_.get(rule).get(1).equals("extractFromFrontTo") && rule_.get(rule).get(2).equals("true")
						&& field.contains(rule_.get(rule).get(3))) {
					String[] px = field.split(Pattern.quote(rule_.get(rule).get(3)));
					field = px[0];
				} else if (rule_.get(rule).get(1).equals("trim") && rule_.get(rule).get(2).equals("true")) {
					field = field.trim();
				} else if (rule_.get(rule).get(1).equals("toUpperCase") && rule_.get(rule).get(2).equals("true")) {
					field = field.toUpperCase();
				} else if (rule_.get(rule).get(1).equals("toLowerCase") && rule_.get(rule).get(2).equals("true")) {
					field = field.toLowerCase();
				} else if (rule_.get(rule).get(1).equals("extractFromTo") && rule_.get(rule).get(2).equals("true")
						&& field.length() > Integer.parseInt(rule_.get(rule).get(4))) {
					field = field.substring(Integer.parseInt(rule_.get(rule).get(3)),
							Integer.parseInt(rule_.get(rule).get(4)));
				}

			}

		}

		return field;
	}

}