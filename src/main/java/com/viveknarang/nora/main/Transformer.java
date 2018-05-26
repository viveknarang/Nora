package com.viveknarang.nora.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.viveknarang.nora.model.Job;
import com.viveknarang.nora.model.Rules;

public class Transformer {

	private static Map<Integer, Rules> rulesMap = new HashMap<Integer, Rules>();

	final static Logger logger = Logger.getLogger(Transformer.class);

	public Transformer() {
		super();
	}

	public static void transform(Job job, List<Rules> rules, List<String[]> rows, String fileName) {
		logger.info("Transformer:transform()::Start");
		long s = System.currentTimeMillis();
		rulesMap = new HashMap<Integer, Rules>();
		int i = 0;

		for (Rules rule : rules) {
			if (rule.getFileName().equals(fileName)) {
				rulesMap.put(rule.getMapIndex(), rule);
			}
		}

		for (String[] row : rows) {

			for (i = 0; i < row.length; i++) {
				if (rulesMap.containsKey(i)) {
					row[i] = transform(row[i], rulesMap.get(i));
				}
			}
		}

		System.out.println(Arrays.toString(rows.get(0)));
		long e = System.currentTimeMillis();

		logger.info("Transformer:transform()::End >> Transformation completed in: " + ((e - s) / 1000)
				+ " seconds for a total of: " + rows.size() + " records");
	}

	public static String transform(String field, Rules rule) {

		if (field.length() == 0) {
			field = rule.getReplaceNullWith();
		} else {

			if (rule.isTruncateEnabled()) {
				if (field.length() > rule.getTruncateAfter()) {
					field = field.substring(0, field.length()) + rule.getTruncateEndChars();
				}
			}
			
			if (rule.getReplace() != null) {
				field = field.replaceAll(rule.getReplace()[0], rule.getReplace()[1]);
			}
			
			if (rule.getRemove() != null) {
				field = field.replace(rule.getRemove(), "");
			}
			
			if (rule.getExtractFromEndTo() != null && field.contains(rule.getExtractFromEndTo())) {
				field = field.split(rule.getExtractFromEndTo())[field.split(rule.getExtractFromEndTo()).length-1];
			}

			if (rule.getExtractFromFrontTo() != null && field.contains(rule.getExtractFromFrontTo())) {
				field = field.split(rule.getExtractFromFrontTo())[0];
			}

			if (rule.getTrim()) {
				field = field.trim();
			}
			
			if (rule.isToUpperCase()) {
				field = field.toUpperCase();
			}

			if (rule.isToLowerCase()) {
				field = field.toLowerCase();
			}

			if (rule.getExtractFromTo() != null && field.length() > Integer.parseInt(rule.getExtractFromTo()[1])) {
				field = field.substring(Integer.parseInt(rule.getExtractFromTo()[0]), Integer.parseInt(rule.getExtractFromTo()[1]));
			}
			
		}

		return field;
	}

}