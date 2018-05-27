package com.viveknarang.nora.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonIOException;
import com.viveknarang.nora.model.Job;
import com.viveknarang.nora.model.RulesConfiguration;

public class JobsManager {

	final static Logger logger = Logger.getLogger(JobsManager.class);

	public static void execute() throws JsonIOException, IOException {

		logger.info("JobsManager:execute()::Start");

		List<Job> jobs = Util.conf.jobs;
		for (Job job : jobs) {

			logger.info(" Executing Job: " + job.getName());

			RulesConfiguration rule = Util.loadRules(job.getName());
			for (String fileName : job.getFiles()) {
				Extractor e = new Extractor(new File(fileName));
				e.run();
				Transformer.transform(job, rule.rules, e.getRows(), fileName);
			}

		}

		logger.info("JobsManager:execute()::Complete");
	}
}
