package com.pdh.test.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * queue示例，基于点对点模型
 *
 * @auther:pengdh
 * @create:2017-04-10 16:50
 */
public class QueueTest {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = factory.createConnection();
        connection.start();

        // 创建一个 Queue
        Queue queue = new ActiveMQQueue("testQueue");
        // 创建一个 Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 注册消费者1
        MessageConsumer consumer1 = session.createConsumer(queue);
        consumer1.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMsg = (TextMessage) message;
                System.out.println("consumer1 get:" + textMsg);
            }
        });

        MessageConsumer consumer2 = session.createConsumer(queue);
        consumer2.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMsg = (TextMessage) message;
                System.out.println("consumer2 get:" + textMsg);
            }
        });

        // 创建一个生产者，然后发送多天消息
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 10 ; i++) {
            producer.send(session.createTextMessage("message:" + i));
        }
    }
}
