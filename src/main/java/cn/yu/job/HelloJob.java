package cn.yu.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bin.yu
 * @create 2018-07-19 16:29
 **/
@Component
public class HelloJob {

    public HelloJob(){
        System.out.println("对象初始化");
    }

    @Scheduled(cron = "0/1 * * * * ? ")
    public void say(){
        System.out.println("Hello MyJob  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()));
    }
}
