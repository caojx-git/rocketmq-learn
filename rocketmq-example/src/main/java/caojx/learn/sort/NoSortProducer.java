package caojx.learn.sort;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 模拟订单创建，消息消费会无序
 *
 * @author caojx created on 2022/5/4 8:57 PM
 */
public class NoSortProducer {

    public static void main(String[] args) throws Exception {
        // 1.创建一个默认的发送消息对象Producer【谁来发？】
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 2.设定发送的命名服务器地址【发给谁？】，因为发送消息就是先问nameserver来要broker的地址的
        producer.setNamesrvAddr("localhost:9876");
        // 3.启动发送的服务器
        producer.start();

        // 模拟订单数据
        List<OrderStep> orders = buildOrders();

        // 发送订单消息
        for (OrderStep order : orders) {
            Message message = new Message("topic11", "tag1", order.toString().getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }

        // 6.打扫战场
        producer.shutdown();
    }

    /**
     * 生成模拟3笔订单数据
     * 每个单独的订单都是有如下顺序的
     * <p>
     * 创建、付款、推送、完成
     */
    private static List<OrderStep> buildOrders() {
        List<OrderStep> orderList = new ArrayList<OrderStep>();

        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        return orderList;
    }
}
