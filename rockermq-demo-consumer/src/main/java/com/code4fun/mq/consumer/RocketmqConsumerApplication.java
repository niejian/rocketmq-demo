package com.code4fun.mq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: RockermqConsumerApplication
 * @desc:
 * @time: 2021/12/18 16:03
 * @version: 0.0.1
 */
@SpringBootApplication
public class RocketmqConsumerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(RocketmqConsumerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(RocketmqConsumerApplication.class, args);
        LOGGER.info("RocketmqConsumerApplication 启动成功。。。。");
    }
}
