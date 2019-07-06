package com.viveknarang.nora.main;

import com.google.gson.JsonIOException;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import java.io.IOException;

/**
 * @author Vivek Narang
 */
public class App {

    private final static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) throws JsonIOException, IOException, SchedulerException {

        logger.info("................... Nora ETL Welcome! ...................");
        Util.loadConf();
        JobsManager.schedule();

    }

}
