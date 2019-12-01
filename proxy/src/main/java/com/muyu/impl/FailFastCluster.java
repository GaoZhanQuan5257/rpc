package com.muyu.impl;

import com.muyu.domain.HostAndPort;
import com.muyu.meta.MethodInvokeMetaWrap;
import com.muyu.meta.ResultWrap;
import com.muyu.service.Cluster;
import com.muyu.service.LoadBalancer;
import com.muyu.service.RPCClient;

import java.util.List;

/**
 * @author 七小鱼
 */
public class FailFastCluster implements Cluster {
    private LoadBalancer loadBalancer;
    private RPCClient client;

    public void setClient(RPCClient client) {
        this.client = client;
    }

    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    public ResultWrap invoke(MethodInvokeMetaWrap mimw,
                             List<HostAndPort> hostAndPorts) {
        HostAndPort hostAndPort = loadBalancer.select(hostAndPorts);
        ResultWrap invoke = client.invoke(mimw, hostAndPort);
        return invoke;
    }

}
