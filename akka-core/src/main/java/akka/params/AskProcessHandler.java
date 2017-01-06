package akka.params;

import akka.actor.ActorRef;
import akka.core.Sender;
import akka.msg.Message;

import java.util.Iterator;

/**
 * Created by ruancl@xkeshi.com on 16/11/9.
 * 任务结果统一返回处理  需实现该接口自定义返回内容
 * <p>
 * 任务分割策略  需自己实现  返回对象为消息对象  汇总使用   需要序列化
 */
public interface AskProcessHandler<S, R> {

    /**
     * 任务结果统一返回处理  需实现该接口自定义返回内容
     *
     * @param it
     * @return
     */
    R getReturn(final Iterator<Object> it);

    /**
     * 任务分割策略  需自己实现  返回对象为消息对象  汇总使用   需要序列化
     *
     * @param cutParam
     * @return
     */
    S cut(final CutParam<S> cutParam);

    /**
     * 单个任务成功返回
     *
     * @param sender
     * @param request
     * @param response
     */
    void onSuccess(Sender sender,Message request, Message response);

    /**
     * 任务异常失败
     * @param throwable
     * @param sender
     * @param request
     */
    void onFailure(Throwable throwable, Sender sender, Message request);

    /**
     * 推送完结
     *
     * @param sender
     * @param throwable
     * @param request
     */
    void onComplete(Sender sender, Throwable throwable, Message request);

}
