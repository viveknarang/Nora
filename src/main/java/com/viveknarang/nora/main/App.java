package com.viveknarang.nora.main;

import java.io.File;
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

		logger.info("................... Massive ETL Welcome! ...................");

		Util.loadConf();
		new Extractor(new File("C:\\1500000_Sales_Records.csv")).run();
		
		logger.info("..................... Massive ETL Bye! .....................");

	}

}
