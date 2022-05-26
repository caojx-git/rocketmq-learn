package caojx.learn.sort;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消费者
 *
 * @author caojx created on 2022/5/4 9:28 PM
 */
public class MessageListenerOrderlyConcurrentlyConsumer {

    public static void main(String[] args) throws Exception {
        // 1.谁来收消息
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 2.从哪里收？收消息先问nameserver要broker地址
        consumer.setNamesrvAddr("localhost:9876");
        // 3.监听那个消息队列
        consumer.subscribe("topic12", "*");
        // 4.处理业务流程

        // 消费者，一个线程，只监听一个queue
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                // 业务逻辑
                for (MessageExt messageExt : list) {
                    System.out.println("消息是：" + new String(messageExt.getBody()));
                }

                // 消费成功
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        // 启动消费者
        consumer.start();

        System.out.println("消费者启动成功了");
    }
}
