## about
rocketmq使用demo
## 目标
1. 常用的消息队列使用（）
   * 普通消息
   * 顺序消息（注意参数`destination formats: `topicName:tags``）
    ```java
    /**
    * Same to {@link #asyncSendOrderly(String, Message, String, SendCallback)}.
    *
    * @param destination formats: `topicName:tags`
    * @param payload the Object to use as payload
    * @param hashKey use this key to select queue. for example: orderId, productId ...
    * @param sendCallback {@link SendCallback}
    */
    public void asyncSendOrderly(String destination, Object payload, String hashKey, SendCallback sendCallback) {
    asyncSendOrderly(destination, payload, hashKey, sendCallback, producer.getSendMsgTimeout());
    }
    ```
   消费端消费示例:
   ```java
    @Slf4j
    @Component
    // 选取制定的tag
    @RocketMQMessageListener(topic = "order_topic", consumerGroup = "consumer-order",
    consumeMode = ConsumeMode.ORDERLY, selectorType = SelectorType.TAG, selectorExpression = "TagA||TagC")
        public class OrderMsgReceiverListener implements RocketMQListener<MsgDto> {
        @Override
        public void onMessage(MsgDto message) {
        String data = JSONObject.fromObject(message).toString();
            log.info("消费端收到消息：{}", data);
        }
    }
    ```
   * 在rocketmq中，在非广播模式下一条消息只能被某一个消费组所消费，也就是说在消费端是多实例的情况下，一条消息只能被一个实例所消费；
在广播模式下，一条消息可以被多个实例同时消费。
2. 运用事务消息，实现分布式事务的控制
