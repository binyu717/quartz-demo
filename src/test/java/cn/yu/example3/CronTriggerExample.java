package cn.yu.example3;

import cn.yu.SimpleJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author bin.yu
 * @create 2018-07-20 17:06
 **/
public class CronTriggerExample {
    private static Logger log = LoggerFactory.getLogger(CronTriggerExample.class);

    public void run() throws Exception {
        log.info("------- Initializing -------------------");
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete --------");

        log.info("------- Scheduling Jobs ----------------");
        JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();

        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(cronSchedule("0/2 * * * * ?"))
                .build();
        sched.scheduleJob(job, trigger);
        sched.start();
        try {
            // wait one minutes to show jobs
            Thread.sleep(60L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        sched.shutdown();
    }

    public static void main(String[] args) throws Exception{
        CronTriggerExample triggerExample = new CronTriggerExample();
        triggerExample.run();
    }
}
