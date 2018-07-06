import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author bin.yu
 * @create 2017-11-14 17:53
 **/
public class JobTest {
    public static void main(String[] args) {


        SchedulerFactory schedulerfactory=new StdSchedulerFactory();
        Scheduler scheduler=null;
        try{

            scheduler=schedulerfactory.getScheduler();
            JobDetail job1= JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();
//            JobDetail job2= JobBuilder.newJob(MyJob.class).withIdentity("job2", "group2").build();
            job1.getJobDataMap().put("name","map");
//            job2.getJobDataMap().put("desc", "dddd");
        Trigger trigger1=TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "triggerGroup")
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2).withMisfireHandlingInstructionNextWithExistingCount().withRepeatCount(5))
                        .startNow().build();

//        Trigger trigger2=TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "triggerGroup")
//                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withRepeatCount(5))
//                .startNow().build();

            scheduler.scheduleJob(job1, trigger1);
//            scheduler.scheduleJob(job2, trigger2);
            scheduler.start();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
