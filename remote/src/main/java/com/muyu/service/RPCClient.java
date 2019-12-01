package com.muyu.service;

import com.muyu.domain.HostAndPort;
import com.muyu.meta.MethodInvokeMetaWrap;
import com.muyu.meta.ResultWrap;

/**
 * @author 七小鱼
 */
public interface RPCClient {
    public ResultWrap invoke(MethodInvokeMetaWrap mimw, HostAndPort hap);

    public void close();
}
