package com.viveknarang.nora.main;

import com.google.gson.JsonIOException;
import com.viveknarang.nora.model.ETLJob;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

/**
 * @author Vivek Narang
 */
public class JobsManager {

    final static Logger logger = Logger.getLogger(JobsManager.class);

    public static void schedule() throws JsonIOException, SchedulerException {

        logger.info("JobsManager:execute()::Start");

        List<ETLJob> jobs = Util.conf.jobs;

        for (ETLJob job : jobs) {

            JobDetail xjob = JobBuilder.newJob(ETLJob.class).withIdentity(job.getName(), "Nora").build();

            JobDataMap jobDataMap = xjob.getJobDataMap();
            jobDataMap.put("NAME", job.getName());
            jobDataMap.put("FILES", job.getFiles());
            jobDataMap.put("DELIMITER", job.getCsvDelimiter());
            jobDataMap.put("DATABASE", job.getDatabase());
            jobDataMap.put("COLLECTION", job.getCollection());
            jobDataMap.put("BATCH_SIZE", job.getBatchSize());

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), "Nora").withSchedule(
                    CronScheduleBuilder.cronSchedule(job.getSchedule())).build();

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(xjob, trigger);

            logger.info("Scheduling Job: " + job.getName());

        }

        logger.info("JobsManager:execute()::Complete");
    }
}