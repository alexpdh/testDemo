package com.pdh.test.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;
import javax.xml.soap.Text;

/**
 * JMSSelector示例
 *
 * 前面的例子中创建一个消息消费者使用的是:
 * sesssion.createConsumer(destination)
 * 另外，还提供了另一种方式：
 * sesssion.createConsumer(destination, selector)
 * 这里selector是一个字符串，用来过滤消息。也就是说，这种方式可以创建一个可以只接收特定消息的一个消费者。
 * Selector的格式是类似于SQL-92的一种语法。可以用来比较消息头信息和属性。
 * 下面的例子中，创建两个消费者，共同监听同一个Queue，但是它们的Selector不同，
 * 然后创建一个消息生产者，来发送多个消息。
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
