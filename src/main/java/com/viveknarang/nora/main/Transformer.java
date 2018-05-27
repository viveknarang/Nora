package com.viveknarang.nora.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.viveknarang.nora.model.Job;
import com.viveknarang.nora.model.Rules;

public class Transformer {

	private static Map<Integer, Rules> rulesMap = new LinkedHashMap<Integer, Rules>();

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
		System.out.println(Arrays.toString(rows.get(1)));
		System.out.println(Arrays.toString(rows.get(2)));
		System.out.println(Arrays.toString(rows.get(3)));
		System.out.println(Arrays.toString(rows.get(4)));
		System.out.println(Arrays.toString(rows.get(5)));
		System.out.println(Arrays.toString(rows.get(6)));
		System.out.println(Arrays.toString(rows.get(7)));
		System.out.println(Arrays.toString(rows.get(8)));
		System.out.println(Arrays.toString(rows.get(9)));
		
		long e = System.currentTimeMillis();

		logger.info("Transformer:transform()::Complete >> Transformation completed in: " + ((e - s) / 1000)
				+ " seconds for a total of: " + rows.size() + " records");
	}

	public static String transform(String field, Rules rule) {

		if (field.length() == 0 && rule.getReplaceNullWith()[1].equals("true")) {
			field = rule.getReplaceNullWith()[2];
		} else {

			if (rule.getTruncateAfter()[1].equals("true")) {
				if (field.length() > Integer.parseInt(rule.getTruncateAfter()[2])) {
					field = field.substring(0, Integer.parseInt(rule.getTruncateAfter()[2])) + rule.getTruncateAfter()[3];
				}
			}
			
			if (rule.getReplace()[1].equals("true")) {
				field = field.replaceAll(rule.getReplace()[2], rule.getReplace()[3]);
			}
			
			if (rule.getRemove()[1].equals("true")) {
				field = field.replace(rule.getRemove()[2], "");
			}
			
			if (rule.getExtractFromEndTo()[1].equals("true") && field.contains(rule.getExtractFromEndTo()[2])) {
				String[] px = field.split(Pattern.quote(rule.getExtractFromEndTo()[2]));
				field = px[px.length-1];
			}

			if (rule.getExtractFromFrontTo()[1].equals("true") && field.contains(rule.getExtractFromFrontTo()[2])) {
				String[] px = field.split(Pattern.quote(rule.getExtractFromFrontTo()[2]));
				field = px[0];
			}

			if (rule.getTrim()[1].equals("true")) {
				field = field.trim();
			}
			
			if (rule.getToUpperCase()[1].equals("true")) {
				field = field.toUpperCase();
			}

			if (rule.getToLowerCase()[1].equals("true")) {
				field = field.toLowerCase();
			}

			if (rule.getExtractFromTo()[1].equals("true") && field.length() > Integer.parseInt(rule.getExtractFromTo()[3])) {
				field = field.substring(Integer.parseInt(rule.getExtractFromTo()[2]), Integer.parseInt(rule.getExtractFromTo()[3]));
			}
			
		}

		return field;
	}

}