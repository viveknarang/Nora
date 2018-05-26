package com.viveknarang.nora.main;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.gson.JsonIOException;

/**
 * @author Vivek Narang
 *
 */
public class App {

	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws JsonIOException, IOException {

		logger.info("................... Nora ETL Welcome! ...................");
		Util.loadConf();
		JobsManager.execute();
		logger.info("..................... Nora ETL Bye! .....................");

	}

}
