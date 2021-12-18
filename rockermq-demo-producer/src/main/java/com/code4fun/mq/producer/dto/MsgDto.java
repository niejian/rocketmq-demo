package com.code4fun.mq.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: MsgDto
 * @desc:
 * @time: 2021/12/18 16:17
 * @version: 0.0.1
 */
public class MsgDto<T> {
    private Long msgCreatTime;
    private T data;

    public MsgDto(Long msgCreatTime, T data) {
        this.msgCreatTime = msgCreatTime;
        this.data = data;
    }

    public MsgDto() {
    }

    public Long getMsgCreatTime() {
        return msgCreatTime;
    }

    public void setMsgCreatTime(Long msgCreatTime) {
        this.msgCreatTime = msgCreatTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MsgDto{" +
                "msgCreatTime=" + msgCreatTime +
                ", data=" + data +
                '}';
    }
}
