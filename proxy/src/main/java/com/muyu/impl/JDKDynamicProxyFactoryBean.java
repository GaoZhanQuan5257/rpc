package com.muyu.impl;

import com.muyu.domain.HostAndPort;
import com.muyu.meta.MethodInvokeMeta;
import com.muyu.meta.MethodInvokeMetaWrap;
import com.muyu.meta.Result;
import com.muyu.meta.ResultWrap;
import com.muyu.service.BeanProxy;
import com.muyu.service.Cluster;
import com.muyu.service.Registry;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author 七小鱼
 */
public class JDKDynamicProxyFactoryBean implements BeanProxy {
    private Class targetInterface;

    private List<HostAndPort> hostAndPorts;
    private Registry registry;

    private Cluster cluster;//集群容错

    public JDKDynamicProxyFactoryBean() {
    }

    public JDKDynamicProxyFactoryBean(Class targetInterface, Registry registry) {
        this.targetInterface = targetInterface;
        //获取服务列表
        this.registry = registry;
        //根据目标接口获取服务列表
        hostAndPorts = registry.retriveService(targetInterface);
        registry.subscribeService(targetInterface, hostAndPorts);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        //封装远程参数
        MethodInvokeMeta mim = new MethodInvokeMeta(targetInterface, method.getName(),
                method.getParameterTypes(), args);
        MethodInvokeMetaWrap mimw = new MethodInvokeMetaWrap(mim);
//        mimw.setAttchments(null);


        //网络RPC实现获取远程目标对象的返回值
        ResultWrap resultWrap = cluster.invoke(mimw, hostAndPorts);

        Result result = resultWrap.getResult();
        if (result.getException() != null) {
            throw result.getException();
        }
        return result.getReturnValue();
    }

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(JDKDynamicProxyFactoryBean.class.getClassLoader(),
                new Class[]{targetInterface}, this);
    }

    @Override
    public Class getObjectType() {
        return targetInterface;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
