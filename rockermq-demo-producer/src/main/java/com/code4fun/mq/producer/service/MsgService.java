package com.code4fun.mq.producer.service;

import com.code4fun.mq.producer.dto.MsgDto;

public interface MsgService {
    /**
     * 发送消息
     * @param msgDto
     */
    MsgDto sendMsg() throws Exception;
}
