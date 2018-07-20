package cn.yu.example4;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author bin.yu
 * @create 2018-07-20 17:25
 **/
public class ColorJob implements Job {

    private static Logger log = LoggerFactory.getLogger(ColorJob.class);

    public static final String FAVORITE_COLOR = "favorite color";
    public static final String EXECUTION_COUNT = "count";
    private int _counter = 1;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String color = jobDataMap.getString(FAVORITE_COLOR);
        int count = jobDataMap.getInt(EXECUTION_COUNT);
        log.info("ColorJob: " + jobKey + " executing at " + new Date() + "\n" +
                "  favorite color is " + color + "\n" +
                "  execution count (from job map) is " + count + "\n" +
                "  execution count (from job member variable) is " + _counter);

        count++;
        jobDataMap.put(EXECUTION_COUNT, count);

        _counter++;

    }
}
