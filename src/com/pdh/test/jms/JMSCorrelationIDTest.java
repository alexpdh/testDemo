package com.pdh.test.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * JMSCorrelationID示例
 *
 * JMSCorrelationID主要是用来关联多个Message，例如需要回复一个消息的时候，
 * 通常把回复的消息的JMSCorrelationID设置为原来消息的ID。在下面这个例子中，
 * 创建了三个消息生产者A，B，C和三个消息消费者A，B，C。生产者A给消费者A发送一个消息，
 * 同时需要消费者A给它回复一个消息。B、C与A类似。
 *
 * 简图如下：
 * 生产者A-----发送----〉消费者A-----回复------〉生产者A
 * 生产者B-----发送----〉消费者B-----回复------〉生产者B
 * 生产者C-----发送----〉消费者C-----回复------〉生产者C
 *
 * 需要注意的是，所有的发送和回复都使用同一个Queue，通过Selector区分。
 *
 * @auther:pengdh
 * @create:2017-04-11 11:10
 */
public class JMSCorrelationIDTest {
    private Queue queue;
    private Session session;

    public JMSCorrelationIDTest() throws JMSException {
        ActiveMQConnectionFactory factory  = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection =  factory.createConnection();
        connection.start();

        queue = new ActiveMQQueue("testQueue");
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        setupConsumer("ConsumerA");
        setupConsumer("ConsumerB");
        setupConsumer("ConsumerC");

        setupProducer("ProducerA", "ConsumerA");
        setupProducer("ProducerB", "ConsumerB");
        setupProducer("ProducerC", "ConsumerC");
    }

    private void setupConsumer(final String name) throws JMSException {
        // 注册一个消费者，它只接收属于它自己的消息
        MessageConsumer consumer = session.createConsumer(queue, "receiver = '"+ name +"'");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    MessageProducer producer = session.createProducer(queue);
                    System.out.println(name + " get:" + ((TextMessage)message).getText());
                    // 回复一个消息
                    TextMessage replyMessage = session.createTextMessage("Reply from " + name);
                    replyMessage.setJMSCorrelationID(message.getJMSMessageID());
                    producer.send(replyMessage);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setupProducer(final String name, String consumerName) throws JMSException {
        MessageProducer producer = session.createProducer(queue);
        // 设置消息非持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        // 创建一个消息，并设置一个属性receiver，为消费者的名字。
        Message message = session.createTextMessage("Message from " + name);
        message.setStringProperty("receiver", consumerName);
        producer.send(message);

        MessageConsumer replyConsumer = session.createConsumer(queue,"JMSCorrelationID='" + message.getJMSMessageID() + "'");
        replyConsumer.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println(name + " get reply:" + ((TextMessage)m).getText());
                } catch (JMSException e) { }
            }
        });
    }

    public static void main(String[] args) throws JMSException {
        new JMSCorrelationIDTest();
    }
}
