package com.muyu.service;

import com.muyu.domain.HostAndPort;
import com.muyu.meta.MethodInvokeMetaWrap;
import com.muyu.meta.ResultWrap;

import java.util.List;

/**
 * @author 七小鱼
 */
public interface Cluster {

    ResultWrap invoke(MethodInvokeMetaWrap mimw, List<HostAndPort> hostAndPorts);

}
