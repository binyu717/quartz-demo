package cn.yu.example2;

import cn.yu.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author bin.yu
 * @create 2018-07-20 15:26
 **/
public class SimpleTriggerExample{

    private static Logger logger = LoggerFactory.getLogger(SimpleTriggerExample.class);

    public void run() throws Exception{
        logger.info("--------------init--------------");
        StdSchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        logger.info("------------init completed------------");
        // 调度开始前，要安排任务
        logger.info("------------Scheduling jobs------------");

        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);
        // job1 startTime 执行一次
        JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();
        SimpleTrigger trigger = (SimpleTrigger)newTrigger().withIdentity("trigger1", "group1").startAt(startTime).build();
        Date ft = scheduler.scheduleJob(job, trigger);
        logger.info(job.getKey()+" will run at: "+ft+" and repeat: "+trigger.getRepeatCount()+" times, every "
                + trigger.getRepeatInterval()/1000 +" second");


        // job2 同job1
        job = newJob(SimpleJob.class).withIdentity("job2","group1").build();
        trigger = (SimpleTrigger)newTrigger().withIdentity("trigger2","group1").startAt(startTime).build();
        ft = scheduler.scheduleJob(job, trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // job3 将执行11次，运行一次，重复十次，间隔10s
        job = newJob(SimpleJob.class).withIdentity("job3","group1").build();
        trigger = newTrigger().withIdentity("trigger3", "group1").startAt(startTime).withSchedule(
                simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10)
        ).build();
        ft = scheduler.scheduleJob(job, trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times,every " +
                trigger.getRepeatInterval() / 1000 + " seconds");

        // 新建一个定时器装配job3，重复2次间隔10s
        // job直接挂载到trigger上，上面例子是挂载在scheduler
        trigger = newTrigger().withIdentity("trigger3","group2").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(2)).forJob(job).build();
        scheduler.scheduleJob(trigger);
        logger.info(job.getKey() + " will [also] run at: " + ft + " and repeat: " + trigger.getRepeatCount()
                + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // job4 执行6次，间隔10s
        job = newJob(SimpleJob.class).withIdentity("job4", "group1").build();
        trigger = newTrigger().withIdentity("trigger4", "group1").startAt(startTime).withSchedule(
                simpleSchedule().withIntervalInSeconds(10).withRepeatCount(5)).build();
        ft = scheduler.scheduleJob(job, trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // job5 5min后执行一次
        job = newJob(SimpleJob.class).withIdentity("job5", "group1").build();

        trigger = (SimpleTrigger) newTrigger().withIdentity("trigger5", "group1")
                .startAt(futureDate(5, DateBuilder.IntervalUnit.MINUTE)).build();

        ft = scheduler.scheduleJob(job, trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // job6  每 40s 执行一次，
        job = newJob(SimpleJob.class).withIdentity("job6", "group1").build();

        trigger = newTrigger().withIdentity("trigger6", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();

        ft = scheduler.scheduleJob(job, trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        logger.info("---------- Starting Scheduler------------");
        scheduler.start();
        logger.info("--------- Started Scheduler--------------");

        // job 在调度开始之后仍可以重新安排
        // job7  执行21次间隔5min
        job = newJob(SimpleJob.class).withIdentity("job7", "group1").build();

        trigger = newTrigger().withIdentity("trigger7", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInMinutes(5).withRepeatCount(20)).build();

        ft = scheduler.scheduleJob(job, trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // job 可以直接触发，而不一定需要一个定时器
        job = newJob(SimpleJob.class).withIdentity("job8", "group1").storeDurably().build();
        scheduler.addJob(job,true);
        logger.info("'Manually' triggering job8 ...");
        // 手动触发job8,执行一次
        scheduler.triggerJob(jobKey("job8", "group1"));

        logger.info("------- Waiting 30 seconds... --------------");

        try {
            // wait 33 seconds to show jobs
            Thread.sleep(30L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        // job 可以重新调度(修改job的定时器，将按新的定时规则执行)
        // job7 将重新立即执行11次，间隔1s
        logger.info("------- Rescheduling... --------------------");
        trigger = newTrigger().withIdentity("trigger7", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(5).withRepeatCount(20)).build();

        ft = scheduler.rescheduleJob(trigger.getKey(), trigger);
        logger.info("job7 rescheduled to run at: " + ft);

        logger.info("------- Waiting one minutes... ------------");
        try {
            // wait one minutes to show jobs
            Thread.sleep(60L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        logger.info("------- Shutting Down ---------------------");

        scheduler.shutdown(true);

        logger.info("------- Shutdown Complete -----------------");

        // 统计调度器信息
        SchedulerMetaData metaData = scheduler.getMetaData();
        logger.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws Exception{
        SimpleTriggerExample triggerExample = new SimpleTriggerExample();
        triggerExample.run();
//        StdSchedulerFactory sf = new StdSchedulerFactory();
//        Scheduler scheduler = sf.getScheduler();
//        JobDetail job = newJob(SimpleJob.class).withIdentity("job8", "group1").storeDurably().build();
//        scheduler.addJob(job,true);
//        logger.info("'Manually' triggering job8 ...");
//        // 手动触发job8
//        scheduler.triggerJob(jobKey("job8", "group1"));
//        scheduler.start();
//        try {
//            // wait one minutes to show jobs
//            Thread.sleep(60L * 1000L);
//            // executing...
//        } catch (Exception e) {
//            //
//        }
//        scheduler.shutdown();
    }
}
