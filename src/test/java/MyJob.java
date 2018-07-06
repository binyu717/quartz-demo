import org.quartz.*;

/**
 * @author bin.yu
 * @create 2017-11-14 18:07
 **/
public class MyJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDetail jobDetail = context.getJobDetail();
        JobDataMap map = jobDetail.getJobDataMap();
        Trigger trigger = context.getTrigger();
        System.out.println("测试"+trigger);
    }
}
