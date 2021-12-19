package com.code4fun.mq.producer.service;


import com.code4fun.mq.utils.dto.MsgDto;

public interface MsgService {
    /**
     * 发送消息
     */
    MsgDto sendMsg() throws Exception;

    /**
     * 发送有序消息类型信息
     * @throws Exception
     */
    void sendOrderMsg() throws Exception;
}
