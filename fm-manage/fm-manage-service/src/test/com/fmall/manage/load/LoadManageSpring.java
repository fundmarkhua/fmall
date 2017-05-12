package com.fmall.manage.load;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/11 21:49
 */
public class LoadManageSpring {
    public static void main(String[] args) {
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
        System.out.println("Manage服务加载完毕");
        try {
            int read = System.in.read();
            System.out.println("结束工作");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
