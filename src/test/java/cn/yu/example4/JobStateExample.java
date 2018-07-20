package cn.yu.example4;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author bin.yu
 * @create 2018-07-20 17:56
 **/
public class JobStateExample {

    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(JobStateExample.class);

        log.info("------- Initializing -------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete --------");

        log.info("------- Scheduling Jobs ----------------");

        // get a "nice round" time a few seconds in the future....
        Date startTime = nextGivenSecondDate(null, 10);

        JobDetail job1 = newJob(ColorJob.class).withIdentity("job1", "group1").build();
        SimpleTrigger trigger1 = newTrigger().withIdentity("trigger1", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4)).build();

        job1.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "green");
        job1.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);
        Date scheduleTime1 = sched.scheduleJob(job1, trigger1);
        log.info(job1.getKey() + " will run at: " + scheduleTime1 + " and repeat: " + trigger1.getRepeatCount()
                + " times, every " + trigger1.getRepeatInterval() / 1000 + " seconds");


        JobDetail job2 = newJob(ColorJob.class).withIdentity("job2", "group1").build();

        SimpleTrigger trigger2 = newTrigger().withIdentity("trigger2", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4)).build();

        // pass initialization parameters into the job
        // this job has a different favorite color!
        job2.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "red");
        job2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

        // schedule the job to run
        Date scheduleTime2 = sched.scheduleJob(job2, trigger2);
        log.info(job2.getKey().toString() + " will run at: " + scheduleTime2 + " and repeat: " + trigger2.getRepeatCount()
                + " times, every " + trigger2.getRepeatInterval() / 1000 + " seconds");

        log.info("------- Starting Scheduler ----------------");
        sched.start();

        log.info("------- Started Scheduler -----------------");

        log.info("------- Waiting 60 seconds... -------------");
        try {
            // wait five minutes to show jobs
            Thread.sleep(60L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        log.info("------- Shutting Down ---------------------");

        sched.shutdown(true);

        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws Exception{
        JobStateExample example = new JobStateExample();
        example.run();
    }
}
