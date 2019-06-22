package com.viveknarang.nora.model;

import java.io.File;
import java.util.Arrays;

import com.viveknarang.nora.main.Extractor;
import com.viveknarang.nora.main.Loader;
import com.viveknarang.nora.main.Transformer;
import com.viveknarang.nora.main.Util;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.apache.log4j.Logger;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.JobDataMap;


/**
 * @author Vivek Narang
 */
@PersistJobDataAfterExecution
public class ETLJob implements Job {

    final static Logger logger = Logger.getLogger(ETLJob.class);

    public String name;

    public String[] files;

    public String schedule;

    public String getName() {
        return name;
    }

    public String[] getFiles() {
        return files;
    }

    public String getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return "ETLJob [name=" + name + ", files=" + Arrays.toString(files) + ", schedule=" + schedule + "]";
    }

    public void execute(JobExecutionContext context) {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String name = dataMap.getString("NAME");
        String[] files = (String[]) dataMap.get("FILES");

        logger.info("------------------------------------------------------------------------------------------------------------------------------------------------");
        logger.info("ETLJob:execute():Start ## EXECUTING JOB : " + name);

        try {
            RulesConfiguration rule = Util.loadRules(name);
            for (String fileName : files) {
                Extractor e = new Extractor(new File(fileName));
                e.extract();
                Transformer.transform(this, rule.rules, e.getRows(), fileName);
                Loader l = new Loader();
            }
        } catch (Exception e) {
            logger.error("Exception: " + e);
        }

        logger.info("ETLJob:execute():End ## Completing Execution : " + name);
        logger.info("------------------------------------------------------------------------------------------------------------------------------------------------");
    }

}