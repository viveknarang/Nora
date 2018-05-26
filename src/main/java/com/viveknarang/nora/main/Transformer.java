package com.viveknarang.nora.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.viveknarang.nora.model.Job;
import com.viveknarang.nora.model.Rules;

public class Transformer {
	
	private static Map<String, Rules> rulesMap = new HashMap<String, Rules>();
	
	final static Logger logger = Logger.getLogger(Transformer.class);
	
	public Transformer() {
		super();
	}
	
	public static void transform(Job job, List<Rules> rules, List<String[]> rows, String fileName) {
		logger.info("Transformer:transform()::Start");
		rulesMap = new HashMap<String, Rules>();
		
		for (Rules rule : rules) {
			if (rule.getFileName().equals(fileName)) {
				rulesMap.put(rule.getMapFromField(), rule);
			}
		}
		
		for (String field : rulesMap.keySet()) {
			
			
		}
	
		System.out.println(rulesMap);
		logger.info("Transformer:transform()::End");
	}

}