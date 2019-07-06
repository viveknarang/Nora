package com.viveknarang.nora.model;

import com.viveknarang.nora.main.Extractor;
import com.viveknarang.nora.main.Loader;
import com.viveknarang.nora.main.Transformer;
import com.viveknarang.nora.main.Util;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;

import java.io.File;
import java.util.Arrays;


/**
 * @author Vivek Narang
 */
@PersistJobDataAfterExecution
public class ETLJob implements Job {

    final static Logger logger = Logger.getLogger(ETLJob.class);

    public String name;

    public String[] files;

    public String csvDelimiter;

    public String schedule;

    public String database;

    public String collection;

    public String batchSize;

    public String getName() {
        return name;
    }

    public String[] getFiles() {
        return files;
    }

    public String getCsvDelimiter() {
        return csvDelimiter;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getDatabase() {
        return database;
    }

    public String getCollection() {
        return collection;
    }

    public String getBatchSize() {
        return batchSize;
    }

    @Override
    public String toString() {
        return "ETLJob [name=" + name + ", files=" + Arrays.toString(files) + ", schedule=" + schedule + "]";
    }

    public void execute(JobExecutionContext context) {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String name = dataMap.getString("NAME");
        String[] files = (String[]) dataMap.get("FILES");
        String csvDelimiter = dataMap.getString("DELIMITER");
        String database = dataMap.getString("DATABASE");
        String collection = dataMap.getString("COLLECTION");
        int batchSize = Integer.parseInt(dataMap.getString("BATCH_SIZE"));

        logger.info("------------------------------------------------------------------------------------------------------------------------------------------------");
        logger.info("ETLJob:execute():Start ## EXECUTING JOB : " + name);

        try {
            RulesConfiguration rule = Util.loadRules(name);
            for (String fileName : files) {
                Extractor e = new Extractor(new File(fileName), csvDelimiter);
                e.extract();
                Transformer.transform(this, rule.rules, e.getRows(), fileName, e.getHeaders());
                Loader loader = new Loader(database, collection, batchSize);
                loader.load();
            }
        } catch (Exception e) {
            logger.error("Exception: " + e);
        }

        logger.info("ETLJob:execute():End ## Completing Execution : " + name);
        logger.info("------------------------------------------------------------------------------------------------------------------------------------------------");
    }

}