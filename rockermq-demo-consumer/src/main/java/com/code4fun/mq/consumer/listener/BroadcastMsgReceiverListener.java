package com.code4fun.mq.consumer.listener;

import com.code4fun.mq.utils.dto.MsgDto;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @className: OrderMsgReceiverListener
 * @desc: 广播模式：在rocketmq中，在非广播模式下一条消息只能被某一个消费组所消费，也就是说在消费端是多实例的情况下，一条消息只能被一个实例所消费；
 * 在广播模式下，一条消息可以被多个实例同时消费
 * @time: 2021/12/19 9:16
 * @version: 0.0.1
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "order_topic", consumerGroup = "broadcast-consumer-order", messageModel = MessageModel.BROADCASTING,
        consumeMode = ConsumeMode.CONCURRENTLY, selectorType = SelectorType.TAG, selectorExpression = "TagA||TagC")
public class BroadcastMsgReceiverListener implements RocketMQListener<MsgDto> {
    @Override
    public void onMessage(MsgDto message) {
        String data = JSONObject.fromObject(message).toString();
        log.info("广播模式--->消费端收到消息：{}", data);
    }
}
