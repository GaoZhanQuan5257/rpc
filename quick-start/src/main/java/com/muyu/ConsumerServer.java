package com.muyu;

import com.muyu.entity.User;
import com.muyu.impl.FailFastCluster;
import com.muyu.impl.JDKDynamicProxyFactoryBean;
import com.muyu.impl.RandomLoadBalancer;
import com.muyu.impl.ZookeeperRegistry;
import com.muyu.server.NettyRPCClient;
import com.muyu.service.UserService;

/**
 * @author 七小鱼
 */
public class ConsumerServer {
    public static void main(String[] args) throws Throwable {
        JDKDynamicProxyFactoryBean jdkDynamicProxyFactoryBean = new JDKDynamicProxyFactoryBean(UserService.class, new ZookeeperRegistry("127.0.0.1:2181"));
        FailFastCluster failFastCluster = new FailFastCluster();
        failFastCluster.setClient(new NettyRPCClient());
        failFastCluster.setLoadBalancer(new RandomLoadBalancer());
        jdkDynamicProxyFactoryBean.setCluster(failFastCluster);
//
        User user = (User) jdkDynamicProxyFactoryBean.invoke(jdkDynamicProxyFactoryBean.getObject(), UserService.class.getMethod("queryUserById", Integer.class), new Object[]{1});
        System.out.println(user);


//        xml的方式暂时有点问题，先以上边的方式来做
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-consumer-context.xml");
//        context.start();
//        JDKDynamicProxyFactoryBean proxyFactoryBean = (JDKDynamicProxyFactoryBean) context.getBean("jDKDynamicProxyFactoryBean");
//
//        User user = (User) proxyFactoryBean.invoke(proxyFactoryBean.getObject(), UserService.class.getMethod("queryUserById", Integer.class),new Object[]{1});

        int a = 0;

    }
}
