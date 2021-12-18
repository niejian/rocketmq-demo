package com.code4fun.mq.producer.controller;

import com.code4fun.mq.producer.dto.MsgDto;
import com.code4fun.mq.producer.service.MsgService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @className: MsgController
 * @desc: 产生消息
 * @time: 2021/12/18 16:16
 * @version: 0.0.1
 */
@RestController
@RequestMapping(value = "/msg")
public class MsgController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgController.class);
    @Autowired
    private MsgService msgService;

    @RequestMapping("/create")
    public MsgDto<String> createMsg() {

        try {
            return msgService.sendMsg();
        } catch (Exception e) {
            LOGGER.error("消息发送异常", e);
        }
        return null;
    }
}
