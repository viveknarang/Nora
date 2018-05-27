package com.viveknarang.nora.main;

import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.viveknarang.nora.model.JobConfiguration;
import com.viveknarang.nora.model.RulesConfiguration;

/**
 * 
 * @author Vivek Narang
 *
 */
public class Util {

	final static Logger logger = Logger.getLogger(Util.class);
	static JobConfiguration conf;

	public static void loadConf() throws JsonIOException, IOException {

		logger.info("Util:loadConf():Start");
		Gson gson = new Gson();
		conf = gson.fromJson(new FileReader("jobs.conf"), JobConfiguration.class);
		logger.info("Util:loadConf():End");
	}

	public static RulesConfiguration loadRules(String jobName) throws JsonIOException, IOException {

		logger.info("Util:loadRules(" + jobName + "):Start");
		Gson gson = new Gson();
		RulesConfiguration rulesConf = gson.fromJson(new FileReader(jobName + ".rules.conf"), RulesConfiguration.class);
		logger.info("Util:loadRules(" + jobName + "):Complete");
		return rulesConf;
	}

}