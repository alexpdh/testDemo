## JMS
### JMS基本概念 
JMS(Java Message Service) 即Java消息服务。它提供标准的产生、发送、接收消息的接口简化企业应用的开发。它支持两种消息通信模型：点到点（point-to-point）（P2P）模型和发布/订阅（Pub/Sub）模型。P2P 模型规定了一个消息只能有一个接收者;Pub/Sub 模型允许一个消息可以有多个接收者。

对于点到点模型，消息生产者产生一个消息后，把这个消息发送到一个Queue（队列）中，然后消息接收者再从这个Queue中读取，一旦这个消息被一个接收者读取之后，它就在这个Queue中消失了，所以一个消息只能被一个接收者消费。

与点到点模型不同，发布/订阅模型中，消息生产者产生一个消息后，把这个消息发送到一个Topic中，这个Topic可以同时有多个接收者在监听，当一个消息到达这个Topic之后，所有消息接收者都会收到这个消息。
      
简单的讲，点到点模型和发布/订阅模型的区别就是前者是一对一，后者是一对多。
### 几个重要概念
* Destination：消息发送的目的地，也就是前面说的Queue和Topic。创建好一个消息之后，只需要把这个消息发送到目的地，消息的发送者就可以继续做自己的事情，而不用等待消息被处理完成。至于这个消息什么时候，会被哪个消费者消费，完全取决于消息的接受者。

* Message：从字面上就可以看出是被发送的消息。它有下面几种类型：
> StreamMessage：Java 数据流消息，用标准流操作来顺序的填充和读取。
MapMessage：一个Map类型的消息；名称为 string 类型，而值为 Java 的基本类型。
TextMessage：普通字符串消息，包含一个String。
ObjectMessage：对象消息，包含一个可序列化的Java 对象
BytesMessage：二进制数组消息，包含一个byte[]。
XMLMessage:  一个XML类型的消息。
最常用的是TextMessage和ObjectMessage。
Session：与JMS提供者所建立的会话，通过Session我们才可以创建一个Message。
Connection：与JMS提供者建立的一个连接。可以从这个连接创建一个会话，即Session。
ConnectionFactory: 那如何创建一个Connection呢？这就需要下面讲到的ConnectionFactory了。通过这个工厂类就可以得到一个与JMS提供者的连接，即Conection。
Producer：消息的生产者，要发送一个消息，必须通过这个生产者来发送。
MessageConsumer：与生产者相对应，这是消息的消费者或接收者，通过它来接收一个消息。
前面多次提到JMS提供者，因为JMS给我们提供的只是一系列接口，当我们使用一个JMS的时候，还是需要一个第三方的提供者，它的作用就是真正管理这些Connection，Session，Topic和Queue等。

* 通过下面这个简图可以看出上面这些概念的关系。 JMS 发送接收一条消息的流程是：
ConnectionFactory---->Connection--->Session--->Message  
Destination + Session------------------------------------>Producer  
Destination + Session------------------------------------>MessageConsumer   

1. 首先需要得到ConnectionFactoy和Destination，这里创建一个一对一的Queue作为Destination。  
ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");  
Queue queue = new ActiveMQQueue("testQueue");
  
2. 然后又ConnectionFactory创建一个Connection, 再启动这个Connection:   
Connection connection = factory.createConnection();  
connection.start();
  
3. 接下来需要由Connection创建一个Session:  
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)  
  
4. 下面就可以创建Message了,这里创建一个TextMessage。   
Message message = session.createTextMessage("Hello JMS!"); 
 
5. 要想把刚才创建的消息发送出去，需要由Session和Destination创建一个消息生产者：  
MessageProducer producer = session.createProducer(queue);
  
6. 下面就可以发送刚才创建的消息了：  
producer.send(message);
  
7. 消息发送完成之后，我们需要创建一个消息消费者来接收这个消息：  
MessageConsumer comsumer = session.createConsumer(queue);  
Message recvMessage = comsumer.receive(); 
 
8. 消息消费者接收到这个消息之后，就可以得到它的内容：  
System.out.println(((TextMessage)recvMessage).getText());   

> 一个消息对象分为三部分：消息头(Headers)，属性（Properties）和消息体（Payload）。对于StreamMessage和MapMessage，消息本身就有特定的结构，而对于TextMessage，ObjectMessage和BytesMessage是无结构的。一个消息可以包含一些重要的数据或者仅仅是一个事件的通知。
消息的Headers部分通常包含一些消息的描述信息，它们都是标准的描述信息。包含下面一些值：

* JMSDestination
> 消息的目的地，Topic或者是Queue。

   
* JMSDeliveryMode
> 消息的发送模式：persistent或nonpersistent。前者表示消息在被消费之前，如果JMS提供者DOWN了，重新启动后消息仍然存在。后者在这种情况下表示消息会被丢失。可以通过下面的方式设置：
Producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
 
* JMSTimestamp
>当调用send()方法的时候，JMSTimestamp会被自动设置为当前事件。可以通过下面方式得到这个值：
   long timestamp = message.getJMSTimestamp();
　　JMSExpiration 
   表示一个消息的有效期。只有在这个有效期内，消息消费者才可以消费这个消息。默认值为0，表示消息永不过期。可以通过下面的方式设置：
   producer.setTimeToLive(3600000); //有效期1小时 （1000毫秒 * 60秒 * 60分）

* JMSPriority 
> 消息的优先级。0-4为正常的优先级，5-9为高优先级。可以通过下面方式设置：
  producer.setPriority(9);

* JMSMessageID 
> 一个字符串用来唯一标示一个消息。

* JMSReplyTo 
> 有时消息生产者希望消费者回复一个消息，JMSReplyTo为一个Destination，表示需要回复的目的地。当然消费者可以不理会它。

* JMSCorrelationID 
> 通常用来关联多个Message。例如需要回复一个消息，可以把JMSCorrelationID设置为所收到的消息的JMSMessageID。

* JMSType 
> 表示消息体的结构，和JMS提供者有关。

* JMSRedelivered 
> 如果这个值为true，表示消息是被重新发送了。因为有时消费者没有确认他已经收到消息或者JMS提供者不确定消费者是否已经收到。

> 除了Header，消息发送者可以添加一些属性(Properties)。这些属性可以是应用自定义的属性，JMS定义的属性和JMS提供者定义的属性。我们通常只适用自定义的属性。
