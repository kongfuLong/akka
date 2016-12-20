package akka.core;

import akka.enums.RouterStrategy;
import akka.enums.TransferType;
import akka.msg.Message;

/**
 * Created by ruancl@xkeshi.com on 16/12/1.
 *
 *  ask和tell模式的消息发送抽象接口
 */
public interface Sender {

    /**
     * @param message
     * @param transferType
     * @return
     */
    Object sendMsg(Message message, RouterStrategy transferType);

}
