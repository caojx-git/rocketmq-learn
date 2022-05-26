package caojx.learn.messagetype;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * 延时消息
 *
 * @author caojx created on 2022/5/10 9:04 AM
 */
public class SendDelayMessage {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        // 4.延时消息
        Message message = new Message("topic3", "延时消息 hello rocketmq".getBytes(StandardCharsets.UTF_8));
        /**
         * delayTimeLevel就是延迟时间的一个等级，只能填int值，只能填写固定的支持的等级
         * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h;
         * 第一等级代表延迟一秒发送，第二等级是五秒，第三等级是十秒，我设置DelayTimeLevel=3的话，那么这条消息就是延迟十秒才会被收到。
         */
        message.setDelayTimeLevel(3);
        SendResult sendResult = producer.send(message);
        System.out.println("返回结果:" + sendResult);


        producer.shutdown();
    }
}
