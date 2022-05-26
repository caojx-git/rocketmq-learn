package caojx.learn.one2many;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 消费者
 *
 * @author caojx created on 2022/5/4 9:28 PM
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        // 1.谁来收消息
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group2");
        // 2.从哪里收？收消息先问nameserver要broker地址
        consumer.setNamesrvAddr("localhost:9876");

        // 消息的消费模式
        // 默认使用的是集群模式 MessageModel.CLUSTERING
//        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 设置为广播模式 MessageModel.BROADCASTING 广播模式，消费者可以收到该topic的所有的消息
        consumer.setMessageModel(MessageModel.BROADCASTING);

        // 3.监听那个消息队列
        consumer.subscribe("topic2", "*");
        // 4.处理业务流程
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 业务逻辑
                for (MessageExt messageExt : list) {
                    System.out.println("收到消息：" + messageExt);
                    System.out.println("消息是：" + new String(messageExt.getBody()));
                    System.out.println("==========");
                }

                // 消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动消费者
        consumer.start();

        System.out.println("消费者启动成功了");
    }
}
