package cn.yu.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bin.yu
 * @create 2018-07-10 18:19
 **/
@Component
public class JobTest extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("在"+sdf.format(new Date())+"发送消息");
    }

    public static void main(String args[]) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(JobTest.class)
                .withIdentity("job1", "jgroup1").build();
        SimpleTrigger simpleTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger1")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(100, 2))
                .startNow()
                .build();

        //创建scheduler
        ApplicationContext ac = new ClassPathXmlApplicationContext("c-web-beans.xml");
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        scheduler.start();

    }
}
