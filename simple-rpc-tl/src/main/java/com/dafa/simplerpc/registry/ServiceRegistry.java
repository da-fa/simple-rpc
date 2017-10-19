package com.dafa.simplerpc.registry;

import com.dafa.simplerpc.conf.Constant;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Mtime on 2017/10/10 0010.
 */
public class ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private String registryAddress;

    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void register(String data) {
        logger.info("service register");
        if (null != data) {
            ZooKeeper zk = this.connectServer();
            if (null != zk) {
                this.createNode(zk, data);
            }
        }
    }

    private ZooKeeper connectServer() {
        logger.info("==>connect to zookeeper");
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            logger.error("IO异常", e);
        } catch (InterruptedException e) {
            logger.error("中断异常", e);
        }
        return zk;
    }

    private void createNode(ZooKeeper zk, String data) {
        logger.info("==>create node");
        try {
            byte[] bytes = data.getBytes();
            String path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.info("create zookeeper node ({} => {})", path, data);
        } catch (KeeperException e) {
            logger.error("Keeper异常", e);
        } catch (InterruptedException e) {
            logger.error("中断异常", e);
        }

    }

}
