package caojx.learn.transaction;

import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;

/**
 * 发送事务消息
 *
 * @author caojx created on 2022/5/4 8:57 PM
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        // 事务消息生产者
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        // 2.设定发送的命名服务器地址【发给谁？】，因为发送消息就是先问nameserver来要broker的地址的
        producer.setNamesrvAddr("localhost:9876");

        // 设置事务监听器
        producer.setTransactionListener(new TransactionListener() {
            // 正常事务事务过程
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                // 把消息先保存到mysql数据库
                // sql insert (这里省略)

//                本地事务提交，消息就会提交到队列中
//                System.out.println("消息保存到数据库成功了");
//                return LocalTransactionState.COMMIT_MESSAGE;


//                本地事务回滚，ROLLBACK_MESSAGE 回滚，消息就会从broker中删除，消息就不会进入到队列，消费者就不会消费到这笔消息
//                System.out.println("消息保存到数据库失败了");
//                return LocalTransactionState.ROLLBACK_MESSAGE;

                // 如果本地事务状态返回 UNKNOW，或者执行太久没有完成，就会执行事务补充，后边broker就会来询问我们的生产者，然后，执行checkLocalTransaction 再次检查本地事务的状态;
                try {
                    Thread.sleep(1000*60L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return LocalTransactionState.COMMIT_MESSAGE;
//                return LocalTransactionState.UNKNOW;
            }

            // 事务补充过程
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("执行事务补充过程");

                System.out.println("检测消息是否存到数据库中没有？");
                // sql 检查消息是否存到数据库中没有？

                System.out.println("检测到消息已存到数据库中");
                return LocalTransactionState.COMMIT_MESSAGE;

                // 如果补偿还是UNKNOW，这个事务消息补偿还是未知，就应该人工介入排查一下
//                return LocalTransactionState.UNKNOW;

            }

        });
        producer.start();

        // 4.发送消息
        String msg = "hello rocketmq transaction";
        Message message = new Message("topic13", "tag1", msg.getBytes(StandardCharsets.UTF_8));
        SendResult sendResult = producer.sendMessageInTransaction(message, null);
        System.out.println("返回结果:" + sendResult);

        // 别关生产者，因为后边会询问是否提交，还是回滚流程
    }
}
