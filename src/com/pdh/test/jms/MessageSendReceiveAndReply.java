package com.pdh.test.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * JMSReplyTo示例，有时消息生产者希望消费者回复一个消息，JMSReplyTo为一个Destination，
 * 表示需要回复的目的地。当然消费者可以不理会它。
 *
 * 首先消息生产者发送一个消息，内容为“Andy”， 然后消费者收到这个消息之后根据消息的JMSReplyTo，
 * 回复一个消息，内容为“Hello Andy‘。 最后在回复的Queue上创建一个接收回复消息的消费者，它输出所回复的内容。
 * @auther:pengdh
 * @create:2017-04-11 9:38
 */
public class MessageSendReceiveAndReply {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = factory.createConnection();
        connection.start();

        // 创建消息发送 queue
        Queue sendQueue = new ActiveMQQueue("sendQueue");
        // 创建消息回复 queue
        Queue replyQueue = new ActiveMQQueue("replyQueue");
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 创建一条消息并设置 JMSReplyTo 为 replyQueue
        Message message = session.createTextMessage("World!");
        message.setJMSReplyTo(replyQueue);

        // 创建一个消息生产者
        MessageProducer producer = session.createProducer(sendQueue);
        producer.send(message);

        // 创建一个消息消费者，并设置回复 queue
        MessageConsumer consumer = session.createConsumer(sendQueue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                // 创建一个新的MessageProducer来发送一个回复消息
                MessageProducer producer = null;
                try {
                    producer = session.createProducer(message.getJMSReplyTo());
                    producer.send(session.createTextMessage("Hello " + ((TextMessage)message).getText()));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 再创建一个接收者来接收回复的消息
        MessageConsumer replyConsumer = session.createConsumer(replyQueue);
        replyConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println(((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
