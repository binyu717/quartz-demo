package cn.yu.job;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ming.jin
 * @Date 2017/11/20
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("c-web-beans.xml");
    }
}
