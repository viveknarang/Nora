package MassiveETL.MassiveETL;

import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class Util {
	
	final static Logger logger = Logger.getLogger(Util.class);	
	static JobConfiguration conf;

	public static void loadConf() throws JsonIOException, IOException {
		
		logger.info("Util:loadConf():Start");
		Gson gson = new Gson();
		conf = gson.fromJson(new FileReader("jobs.conf"), JobConfiguration.class);
		logger.info("Util:loadConf():End");
	}
	
}
