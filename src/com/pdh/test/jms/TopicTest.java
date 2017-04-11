package com.pdh.test.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;


/**
 * Topic实现发布/订阅模型
 *
 * 与Queue不同的是，Topic实现的是发布/订阅模型，
 * 在下面的例子中，启动2个消费者共同监听一个Topic，然后循环给这个Topic中发送多个消息。
 *
 * @auther:pengdh
 * @create:2017-04-10 17:08
 */
public class TopicTest {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = factory.createConnection();
        connection.start();

        // 创建一个 topic
        Topic topic = new ActiveMQTopic("testTopic");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 注册一个消费者1
        MessageConsumer consumer1 = session.createConsumer(topic);
        consumer1.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("consumer1 get:" + ((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // 注册一个消费者2
        MessageConsumer consumer2 = session.createConsumer(topic);
        consumer1.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("consumer1 get:" + ((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 创建一个生产者，然后发送多天消息
        MessageProducer producer = session.createProducer(topic);
        for (int i = 0; i < 10 ; i++) {
            producer.send(session.createTextMessage("message:" + i));
        }
    }
}
