package caojx.learn.rocketmqspringboot.controller;

import caojx.learn.rocketmqspringboot.domain.User;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送消息测试类
 *
 * @author caojx created on 2022/5/14 11:12 AM
 */
@RestController
@RequestMapping("/demo")
public class SendController {

    /**
     * 引入rocketmq模板类，和第三方软件建连接和断连接
     */
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送消息
     *
     * @return
     */
    @GetMapping("/send")
    public String send() {

//        String msg = "hello rocketmq springboot";
//        // convertAndSend 底层转为字节数组
//        rocketMQTemplate.convertAndSend("topic10", msg);

        // 直接发送对象，对象要实现序列化
        User user = new User("zhangsan", 18);
        // destination = ${topic}:${tag}
        rocketMQTemplate.convertAndSend("topic10:tag1", user);



        // 发送同步消息
        SendResult sendResult = rocketMQTemplate.syncSend("topic10", user);
        System.out.println(sendResult);

        // 发送异步消息
        rocketMQTemplate.asyncSend("topic10", user, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 发送成功
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                // 发送失败
                System.out.println(throwable);
            }
        }, 1000);

        // 发送单向消息
        rocketMQTemplate.sendOneWay("topic10", user);

        // 发送延时消息 3 表示延时等级 10s
        SendResult sendResult1 = rocketMQTemplate.syncSend("topic10", MessageBuilder.withPayload(user).build(), 2000, 3);
        System.out.println(sendResult1);


        // 发送批量消息
        Message message1 = new Message("topic1", "hello rocketmq1".getBytes(StandardCharsets.UTF_8));
        Message message2 = new Message("topic1", "hello rocketmq2".getBytes(StandardCharsets.UTF_8));
        Message message3 = new Message("topic1", "hello rocketmq3".getBytes(StandardCharsets.UTF_8));

        List<Message> messageList = new ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);
        messageList.add(message3);
        SendResult sendResult2 = rocketMQTemplate.syncSend("topic10", messageList, 1000);
        System.out.println(sendResult2);

        return "success";
    }
}
