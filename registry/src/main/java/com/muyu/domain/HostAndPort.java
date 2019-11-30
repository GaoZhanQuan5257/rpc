package com.muyu.domain;

import java.io.Serializable;

/**
 * @author 七小鱼 2019年11月30日12:40
 */
public class HostAndPort implements Serializable {
    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public HostAndPort(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }

    public HostAndPort() {
        super();
    }


}
