package caojx.learn.sort;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单步骤
 *
 * @author caojx created on 2022/5/14 6:04 PM
 */
@Data
public class OrderStep implements Serializable {

    /**
     * 订单标号
     */
    private long orderId;

    /**
     * 描述
     */
    private String desc;
}
