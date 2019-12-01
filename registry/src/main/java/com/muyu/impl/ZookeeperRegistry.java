package com.muyu.impl;

import com.muyu.domain.HostAndPort;
import com.muyu.service.Registry;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.Vector;

/**
 * @author 七小鱼
 */
public class ZookeeperRegistry implements Registry {
    private ZkClient client;

    public ZookeeperRegistry(String servers) {
        super();
        this.client = new ZkClient(servers);
    }

    public void registerService(Class targetInterface, HostAndPort hap) {
        String basepath = PREFIX + "/" + targetInterface.getName() + SUFFIX;
        if (!client.exists(basepath)) {
            client.createPersistent(basepath, true);
        }
        //构建临时服务节点
        String enode = basepath + "/" + hap.getHost() + ":" + hap.getPort();
        if (client.exists(enode)) {
            client.delete(enode);
        }
        client.createEphemeral(enode);
    }

    public List<HostAndPort> retriveService(Class targetInterface) {
        String basepath = PREFIX + "/" + targetInterface.getName() + SUFFIX;
        if (!client.exists(basepath)) {
            client.createPersistent(basepath, true);
        }
        List<String> children = client.getChildren(basepath);
        List<HostAndPort> hostAndPorts = new Vector<HostAndPort>();
        for (String child : children) {
            String host = child.split(":")[0];
            int port = Integer.parseInt(child.split(":")[1]);
            hostAndPorts.add(new HostAndPort(host, port));
        }
        return hostAndPorts;
    }

    public void subscribeService(Class targetInterface, final List<HostAndPort> haps) {
        String basepath = PREFIX + "/" + targetInterface.getName() + SUFFIX;
        if (!client.exists(basepath)) {
            client.createPersistent(basepath, true);
        }
        client.subscribeChildChanges(basepath, new IZkChildListener() {

            public void handleChildChange(String path, List<String> children)
                    throws Exception {
                synchronized (haps) {
                    haps.clear();
                    for (String child : children) {
                        String host = child.split(":")[0];
                        int port = Integer.parseInt(child.split(":")[1]);
                        haps.add(new HostAndPort(host, port));
                    }
                }
            }
        });

    }

    public void close() {
        client.close();
    }

}
