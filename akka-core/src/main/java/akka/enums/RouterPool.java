package akka.enums;

import akka.cluster.metrics.AdaptiveLoadBalancingGroup;
import akka.cluster.metrics.HeapMetricsSelector;
import akka.routing.*;

import java.util.Collections;

/**
 * Created by ruancl@xkeshi.com on 16/10/20.
 * 路由模式的路由策略
 * Pool表示消息接收方 执行方的actor(线程)策略
 * Group表示消息发送时候  选择路由的模式
 */
public enum RouterPool {


    ROBIN, RANDOM, BALANCE, CONSISTENTHASH;


    public Pool getPool(int num) {
        switch (this) {
            case ROBIN:
                return new RoundRobinPool(num);
            case RANDOM:
                return new RandomPool(num);
            case BALANCE:
                return new BalancingPool(num);
            case CONSISTENTHASH:
                return new ConsistentHashingPool(num);
        }
        return new RoundRobinPool(1);
    }


}
