package hello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {

        //initialize the connection
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        // initialize the channel, use channel to communicate with rmq;
        Channel channel = connection.createChannel();

        //declare a queue. rabbitmq will create the queue if the queue isn't exist.
        channel.queueDeclare(QUEUE_NAME, false, false, false,null);
        System.out.println("waiting for message, To exit press CTRL+C");

        // create the callback function to deal with the message.
        DeliverCallback deliverCallback = (comsumerTag,delivery) ->{
//            Envelope envelope = delivery.getEnvelope(); // many detail can fetch from the envelop object.
//            delivery.getProperties(); // if the message have props
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received " + message );
        };

        // define how to deal with message, let's say when a message arrived, it will be send to the deliverCallback function.
        channel.basicConsume(QUEUE_NAME,true, deliverCallback ,consumerTag -> {});
    }
}
