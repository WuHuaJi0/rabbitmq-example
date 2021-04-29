package hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            /*
             * params:
             * queue: queue_name
             * durable: if true, the queue still exist after the rabbitmq restart.
             * exclusive: if true, the queue can only be connected by this connection.
             * autoDelete: server will delete it when no longer in use.
             * arguments: something else you want to attach to the message.
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World, 你好世界";

            /*
             * params:
             * exchanger: the exchanger send our message to a queue by routingKey. In this case, we use the default exchanger so we pass a empty string to it.
             * routingKey: the exchanger send our message to a queue. In this case, we use the queue name.
             * BasicProperties props: Something else you want to attach to the message.
             * byte[] body: the message, in bytes.
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
