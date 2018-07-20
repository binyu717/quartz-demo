package cn.yu;

import cn.yu.example2.SimpleTriggerExample;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author bin.yu
 * @create 2018-07-20 15:26
 **/
public class SimpleJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(SimpleTriggerExample.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        logger.info("SimpleJob say: "+jobKey+" executing at "+new Date());
    }
}
