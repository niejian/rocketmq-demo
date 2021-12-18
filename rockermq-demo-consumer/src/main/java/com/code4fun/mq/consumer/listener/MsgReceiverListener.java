package com.code4fun.mq.consumer.listener;

import com.code4fun.mq.consumer.dto.MsgDto;
import net.sf.json.JSONObject;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;

/**
 * @className: MsgReceiverListener
 * @desc:
 * @time: 2021/12/18 16:46
 * @version: 0.0.1
 */
@Component
@RocketMQMessageListener(topic = "rocketmq-demo",consumerGroup = "rocketmq-consumer-1")
public class MsgReceiverListener implements RocketMQListener<MsgDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgReceiverListener.class);
    @Override
    public void onMessage(MsgDto message) {
        LOGGER.info("=====================================");
        try {
            // 消息转换为Message实体
//            String data = new String(message.getBody(), "UTF-8");
            String data = JSONObject.fromObject(message).toString();
            LOGGER.info("消费端收到消息：{}", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("=====================================");
    }
}
