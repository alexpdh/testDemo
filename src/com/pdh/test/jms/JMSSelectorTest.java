package com.pdh.test.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;
import javax.xml.soap.Text;

/**
 * JMSSelector示例
 *
 * @auther:pengdh
 * @create:2017-04-11 10:31
 */
public class JMSSelectorTest {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 注册一个消息生产者,并发送多条消息
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 10; i++) {
            String receiver = (i%3 == 0 ? "A" : "B");
            TextMessage message = session.createTextMessage("Message" + i + ", receiver:" + receiver);
            message.setStringProperty("receiver", receiver);
            producer.send(message);
        }

        // 注册消息消费者 consumerA 用于接收 receiver = 'A' 的消息
        MessageConsumer consumerA = session.createConsumer(queue, "receiver = 'A'");
        consumerA.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("consumerA get:" + ((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // // 注册消息消费者 consumerA 用于接收 receiver = 'B' 的消息
        MessageConsumer consumerB = session.createConsumer(queue, "receiver = 'B'");
        consumerB.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("consumerB get:" + ((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
