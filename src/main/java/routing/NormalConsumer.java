package routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NormalConsumer {
    private final static String QUEUE_NAME = "normal";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false,null);
        System.out.println("waiting for message, To exit press CTRL+C");

        DeliverCallback deliverCallback = (comsumerTag,delivery) ->{
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] Received " + message );
        };

        channel.basicConsume(QUEUE_NAME,true, deliverCallback ,consumerTag -> {});
    }
}
