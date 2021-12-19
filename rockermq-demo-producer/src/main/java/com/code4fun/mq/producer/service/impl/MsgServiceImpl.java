package com.code4fun.mq.producer.service.impl;

import com.code4fun.mq.producer.service.MsgService;
import com.code4fun.mq.utils.dto.MsgDto;
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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.StringJoiner;

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
    private static final String ORDER_TOPIC = "order_topic";

    /**
     * 发送消息
     * @throws Exception
     */
    @Override
    public MsgDto sendMsg() throws Exception{
        LocalDateTime now = LocalDateTime.now();
        long time = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String msgData = "北京时间：" + now.toString();
        MsgDto msgDto = new MsgDto<>(TOPIC, null, msgData);
        msgDto = msgDto.convertMsgPlayload(msgDto);
        Message message = new Message(TOPIC, JSONObject.fromObject(msgDto).toString().getBytes(StandardCharsets.UTF_8));

        this.rocketMQTemplate.asyncSend(msgDto.getDestination(), msgDto, new SendCallback() {
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

    String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};


    /**
     * 发送有序消息类型信息
     *
     * @throws Exception
     */
    @Override
    public void sendOrderMsg() throws Exception {
        // 这里需要注意的是消息的有序性保证的是在同一个tag下面的消息的有序性
        int len = tags.length;
        for (int i = 0; i < 10; i++) {
            int orderId = i % 10;
            LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
            String tag = tags[i % len];
            String data = "tag: " + tag + ", 北京时间：" + now.toString();
            MsgDto msgDto = new MsgDto(ORDER_TOPIC, tag, data);
            msgDto = msgDto.convertMsgPlayload(msgDto);

            String msg = JSONObject.fromObject(msgDto).toString();

            Message message = new Message(ORDER_TOPIC, tag,
                    msg.getBytes(StandardCharsets.UTF_8));
            String destination = formatDestination(ORDER_TOPIC, tag);
            this.rocketMQTemplate.asyncSendOrderly(msgDto.getDestination(), msgDto, tag, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info("消息发送成功，发送结果：{}", sendResult.toString());
                }

                @Override
                public void onException(Throwable throwable) {
                    LOGGER.error("消息发送失败，异常信息：{}", throwable);
                }
            });
        }

    }

    /**
     * 格式化 destination – formats: `topicName:tags`
     * @param topic
     * @param tag
     * @return
     */
    private String formatDestination(String topic, String tag) {
        StringBuffer stringBuffer = new StringBuffer(topic);


        return stringBuffer.append(":").append(tag).toString();
    }

}
