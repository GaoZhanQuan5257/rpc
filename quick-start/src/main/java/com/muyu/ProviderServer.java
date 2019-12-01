package com.muyu;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 七小鱼
 */
public class ProviderServer {
    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        context.start();
        System.out.println("rpc service is start");
        System.in.read(); // press any key to exit\

    }
}
