package com.viveknarang.nora.main;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.viveknarang.nora.model.JobConfiguration;
import com.viveknarang.nora.model.Rules;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author Vivek Narang
 */
public class Util {

    private final static Logger logger = Logger.getLogger(Util.class);
    public static JobConfiguration conf;

    public static void loadConf() throws JsonIOException, IOException {

        logger.info("Util:loadConf():Start");
        Gson gson = new Gson();
        conf = gson.fromJson(new FileReader("jobs.conf"), JobConfiguration.class);
        System.out.println(conf.jobs);
        logger.info("Util:loadConf():End");
    }

    public static Rules loadRules(String jobName) throws JsonIOException, IOException {

        logger.info("Util:loadRules(" + jobName + "):Start");
        Gson gson = new Gson();
        Rules rulesConf = gson.fromJson(new FileReader(jobName + ".rules.conf"), Rules.class);
        logger.info("Util:loadRules(" + jobName + "):Complete");
        return rulesConf;
    }

}