package com.pdh.test.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

/**
 * jms学习，消息发送与接收
 *
 * @auther:pengdh
 * @create:2017-04-10 16:36
 */
public class MessageSendAndReceive {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testqueue");
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Message message = session.createTextMessage("Hello JMS!");

        MessageProducer producer = session.createProducer(queue);
        producer.send(message);
        System.out.println("Send Message Completed!");

        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {
                TextMessage textMsg = (TextMessage) message;
                try {
                    System.out.println(textMsg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();

                }
            }
        });
//		Message recvMessage = consumer.receive(200);
//		System.out.println(((TextMessage) recvMessage).getText());
    }
}
