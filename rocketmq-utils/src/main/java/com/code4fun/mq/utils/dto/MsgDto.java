package com.code4fun.mq.utils.dto;

import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @className: MsgDto
 * @desc:
 * @time: 2021/12/18 16:48
 * @version: 0.0.1
 */
public class MsgDto<T> {
    private Long msgCreatTime;
    private T data;
    private String topicName;
    private String tag;
    private String destination;

    private MsgDto(Long msgCreatTime, T data) {
        this.msgCreatTime = msgCreatTime;
        this.data = data;
    }

    public MsgDto() {
    }

    public MsgDto(String topicName, String tag, T data) {
        this.topicName = topicName;
        this.data = data;
        this.tag = tag;
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

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public MsgDto<T> convertMsgPlayload(MsgDto msgDto) {
        if (null == msgDto.getData()) {
            throw new RuntimeException("消息内容为空");
        }
        long milli = LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        msgDto.setTag(tag);
        msgDto.setMsgCreatTime(milli);
        return convertMqDestination(msgDto);

    }

    private MsgDto convertMqDestination(MsgDto msgDto) {
        if (null == msgDto.tag || "".equals(msgDto.tag)) {
            msgDto.setDestination(msgDto.getTopicName());
            return msgDto;
        }
        StringBuffer stringBuffer = new StringBuffer(this.topicName);

        String destination = stringBuffer.append(":").append(this.tag).toString();
        msgDto.setDestination(destination);
        return msgDto;
    }

    @Override
    public String toString() {
        return "MsgDto{" +
                "msgCreatTime=" + msgCreatTime +
                ", data=" + data +
                ", topicName='" + topicName + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
