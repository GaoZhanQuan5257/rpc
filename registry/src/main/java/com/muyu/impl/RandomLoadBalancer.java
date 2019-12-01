package com.muyu.impl;

import com.muyu.domain.HostAndPort;
import com.muyu.service.LoadBalancer;

import java.util.List;
import java.util.Random;

/**
 * @author 七小鱼
 */
public class RandomLoadBalancer implements LoadBalancer {

    public HostAndPort select(List<HostAndPort> hostAndPorts) {
        // TODO Auto-generated method stub
        int index = new Random().nextInt(hostAndPorts.size());
        return hostAndPorts.get(index);
    }

}
