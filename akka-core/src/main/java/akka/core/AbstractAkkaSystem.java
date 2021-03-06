package akka.core;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actors.ClusterListener;
import akka.cluster.Cluster;
import akka.cluster.ClusterInterface;
import akka.cluster.addrs.ClusterAddress;
import akka.msg.Constant;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ruancl@xkeshi.com on 2016/12/22.
 */
public abstract class AbstractAkkaSystem implements Akka {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAkkaSystem.class);

    private final ActorSystem system;

    private ClusterInterface clusterInterface;

    private String systemName;

    /**
     */
    public AbstractAkkaSystem(String systemName) {
        this.systemName = systemName;
        Config config = ConfigFactory.load();
        this.system = ActorSystem.create(systemName, config);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //在节点监听还未成功建立前阻塞消息
        Cluster.get(system).registerOnMemberUp(() -> countDownLatch.countDown());
        if (countDownLatch.getCount() == 1) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("actor system创建完毕");
        try {
            Constructor constructor = Constant.CLUSTER_STRATEGY.getConstructor(ActorSystem.class);
            clusterInterface = (ClusterInterface) constructor.newInstance(system);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (Constant.CLUSTER_STRATEGY == ClusterAddress.class) {
            system.actorOf(Props.create(ClusterListener.class, (ClusterAddress) clusterInterface));
        }
        logger.info("actor system 扩展功能启动完毕");
    }

    protected ClusterInterface getClusterInterface() {
        return clusterInterface;
    }

    protected String getSystemName() {
        return systemName;
    }

    protected ActorSystem getSystem() {
        return system;
    }


}
