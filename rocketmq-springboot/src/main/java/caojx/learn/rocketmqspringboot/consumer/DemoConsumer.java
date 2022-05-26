package caojx.learn.rocketmqspringboot.consumer;

import caojx.learn.rocketmqspringboot.domain.User;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * 消费者，实现RocketMQListener
 *
 * @author caojx created on 2022/5/14 11:29 AM
 */
@Service
// 监听的topic、tag, 消费者group

// 按照tag过滤
@RocketMQMessageListener(topic = "topic10", selectorExpression = "*", consumerGroup = "consumer_group1")

//按照SQL过滤
//@RocketMQMessageListener(topic = "topic10", selectorType = SelectorType.SQL92, selectorExpression = "age >= 18", consumerGroup = "consumer_group1")
// 广播消息模式
//@RocketMQMessageListener(topic = "topic10", selectorExpression = "*", consumerGroup = "consumer_group1",messageModel = MessageModel.BROADCASTING)
public class DemoConsumer implements RocketMQListener<User> {

    @Override
    public void onMessage(User user) {
        System.out.println("DemoConsumer接收消息:" + user.toString());
    }
}
