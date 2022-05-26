package caojx.learn.messagefilter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消费者，sql过滤
 *
 * rocketmq默认没有开启sql过滤功能，如果要开启，则
 *
 * 在broker.conf配置文件中添加对应的功能项，并开启对应功能
 * enablePropertyFilter=true
 * 重启broker
 * ./mqbroker -n localhost:9876 autoCreateTopicEnable=true
 *
 * 或者直接cmd中输入
 * ./mqadmin updateBrokerConfig -blocalhost:10911 -kenablePropertyFilter -vtrue
 *
 * @author caojx created on 2022/5/4 9:28 PM
 */
public class SqlFilterConsumer {

    public static void main(String[] args) throws Exception {
        // 1.谁来收消息
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 2.从哪里收？收消息先问nameserver要broker地址
        consumer.setNamesrvAddr("localhost:9876");
        // 消费者使用属性过滤，想要成年的消息
        consumer.subscribe("topic8", MessageSelector.bySql("age >= 15 and name='zhangsan'"));

        // 4.处理业务流程
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 业务逻辑
                for (MessageExt messageExt : list) {
                    System.out.println("收到消息：" + messageExt);
                    System.out.println("消息是：" + new String(messageExt.getBody()));
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
