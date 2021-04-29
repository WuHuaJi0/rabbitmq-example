package hello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //初始化队列，见 send.java
        channel.queueDeclare(QUEUE_NAME, false, false, false,null);
        System.out.println("waiting for message, To exit press CTRL+C");

        DeliverCallback deliverCallback = (comsumerTag,delivery) ->{
//            Envelope envelope = delivery.getEnvelope(); //这里包含了很多消息的信息，
//            delivery.getProperties(); // 如果消息有属性的话，可以从这里获取
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] Received " + message );
        };

        //主要是第二个参数：autoAck: 自动应答收到；如果是false,就需要手动应答。
        channel.basicConsume(QUEUE_NAME,true, deliverCallback ,consumerTag -> {});
    }
}
