package com.muyu.service;

import com.muyu.domain.HostAndPort;

import java.util.List;

/**
 * @author 七小鱼
 */
public interface LoadBalancer {
    public HostAndPort select(List<HostAndPort> hostAndPorts);
}
