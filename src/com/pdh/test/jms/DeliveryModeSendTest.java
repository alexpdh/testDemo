package com.pdh.test.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * DeliveryMode 示例
 * 先运行该类，打印 Send Message Sucessfully! 后，停止运行该类，然后运行 DeliveryModeReceiveTest.java 查看结果
 *
 * @auther:pengdh
 * @create:2017-04-10 17:41
 */
public class DeliveryModeSendTest {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(session.createTextMessage("A persistent message"));

        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.send(session.createTextMessage("A nonpersistent message"));
        System.out.println("Send Message Sucessfully!");
    }
}
