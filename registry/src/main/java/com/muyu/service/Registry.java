package com.muyu.service;

import com.muyu.domain.HostAndPort;

import java.util.List;

/**
 * @author 七小鱼 2019年11月30日12:45
 */
public interface Registry {
    String PREFIX = "/RPC";
    String SUFFIX = "/providers";

    /**
     * @param targetInterface
     * @param hap
     */
    void registerService(Class targetInterface, HostAndPort hap);

    /**
     * @param targetInterface
     * @return
     */
    List<HostAndPort> retriveService(Class targetInterface);

    /**
     * @param targetInterface
     * @param haps
     */
    void subscribeService(Class targetInterface, List<HostAndPort> haps);

    void close();
}
