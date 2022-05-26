package caojx.learn.messagetype;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送批量消息消息
 *
 * @author caojx created on 2022/5/10 9:04 AM
 */
public class SendBatchMessage {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        // 4.发送批量消息
        Message message1 = new Message("topic1", "hello rocketmq1".getBytes(StandardCharsets.UTF_8));
        Message message2 = new Message("topic1", "hello rocketmq2".getBytes(StandardCharsets.UTF_8));
        Message message3 = new Message("topic1", "hello rocketmq3".getBytes(StandardCharsets.UTF_8));

        List<Message> messageList = new ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);
        messageList.add(message3);

        SendResult sendResult = producer.send(messageList);
        System.out.println("返回结果:" + sendResult);


        producer.shutdown();
    }
}
