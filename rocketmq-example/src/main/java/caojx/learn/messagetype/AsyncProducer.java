package caojx.learn.messagetype;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * 发送异步消息
 *
 * @author caojx created on 2022/5/4 8:57 PM
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        // 消息发送与接收开发流程
        //1. 谁来发?
        //2. 发给谁?
        //3. 怎么发?
        //4. 发什么?
        //5. 发的结果是什么?
        //6. 打扫战场

        // 1.创建一个默认的发送消息对象Producer【谁来发？】
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 2.设定发送的命名服务器地址【发给谁？】，因为发送消息就是先问nameserver来要broker的地址的
        producer.setNamesrvAddr("localhost:9876");
        // 3.启动发送的服务器
        producer.start();

        for (int i = 0; i < 10; i++) {
            // 4.发送消息，创建要发送的消息对象，指定topic，指定内容body【发什么？】
            String content = "hello rocketmq async" + i;
            Message message = new Message("topic4", "tag1", content.getBytes(StandardCharsets.UTF_8));
            // 发送异步消息
            producer.send(message, new SendCallback() {
                // 发送成功的回调
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                // 发送失败的回调
                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable);
                }
            });
            System.out.println("异步发送完成");
        }

    }
}
