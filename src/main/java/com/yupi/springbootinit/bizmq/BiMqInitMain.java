package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 用于BI项目,创建测试测序用到的交换机和队列 (仅执行一次)
 */

public class BiMqInitMain {
    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            // 设置 rabbitmq 对应的信息
            factory.setHost("localhost");

            Connection connection = factory.newConnection();

            Channel channel = connection.createChannel();

            String biExchange = BiMqConstant.BI_EXCHANGE_NAME;

            channel.exchangeDeclare(biExchange, "direct");

            // 创建队列，分配一个队列名称：demo_queue
            String queueName = BiMqConstant.BI_QUEUE_NAME;
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, biExchange, BiMqConstant.BI_ROUTING_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
