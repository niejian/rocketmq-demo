package com.code4fun.mq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: RocketmqProducerApplication
 * @desc:
 * @time: 2021/12/18 15:36
 * @version: 0.0.1
 */
@SpringBootApplication
public class RocketmqProducerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketmqProducerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(RocketmqProducerApplication.class, args);
        LOGGER.info("RocketmqProducerApplication 启动成功。。。。");
    }

}
