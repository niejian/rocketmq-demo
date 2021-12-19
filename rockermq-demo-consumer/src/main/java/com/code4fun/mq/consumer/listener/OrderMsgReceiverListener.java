package com.code4fun.mq.consumer.listener;

import com.code4fun.mq.utils.dto.MsgDto;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

/**
 * @className: OrderMsgReceiverListener
 * @desc:
 * @time: 2021/12/19 9:16
 * @version: 0.0.1
 */
@Slf4j
@Component
// 选取制定的tag
@RocketMQMessageListener(topic = "order_topic", consumerGroup = "consumer-order",
        consumeMode = ConsumeMode.ORDERLY, selectorType = SelectorType.TAG, selectorExpression = "TagA||TagC")
public class OrderMsgReceiverListener implements RocketMQListener<MsgDto> {
    @Override
    public void onMessage(MsgDto message) {
        String data = JSONObject.fromObject(message).toString();
        log.info("消费端收到消息：{}", data);
    }
}
