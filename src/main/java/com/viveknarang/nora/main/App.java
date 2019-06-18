package com.viveknarang.nora.main;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.gson.JsonIOException;
import org.quartz.SchedulerException;

/**
 * @author Vivek Narang
 *
 */
public class App {

	private final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws JsonIOException, IOException, SchedulerException {

		logger.info("................... Nora ETL Welcome! ...................");
		Util.loadConf();
		JobsManager.execute();

	}

}
