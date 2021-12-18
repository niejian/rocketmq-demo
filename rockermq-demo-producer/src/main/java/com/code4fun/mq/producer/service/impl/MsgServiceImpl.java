package com.code4fun.mq.producer.service.impl;

import com.code4fun.mq.producer.dto.MsgDto;
import com.code4fun.mq.producer.service.MsgService;
import net.sf.json.JSONObject;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @className: MsgServiceImpl
 * @desc:
 * @time: 2021/12/18 16:36
 * @version: 0.0.1
 */
@Service
public class MsgServiceImpl implements MsgService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgServiceImpl.class);
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    private static final String TOPIC = "rocketmq-demo";

    /**
     * 发送消息
     * @throws Exception
     */
    @Override
    public MsgDto sendMsg() throws Exception{
        LocalDateTime now = LocalDateTime.now();
        long time = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        MsgDto msgDto = new MsgDto<>(time, "北京时间：" + now.toString());
        Message message = new Message(TOPIC, JSONObject.fromObject(msgDto).toString().getBytes(StandardCharsets.UTF_8));

        this.rocketMQTemplate.asyncSend(TOPIC, msgDto, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOGGER.info("消息发送成功，发送结果：{}", sendResult.toString());
            }

            @Override
            public void onException(Throwable throwable) {
                LOGGER.error("消息发送失败，异常信息：{}", throwable);
            }
        });
        return msgDto;
    }
}
