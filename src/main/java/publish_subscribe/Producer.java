package publish_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static final String PUBLISH_SUBSCRIBE = "publish_subscribe";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(PUBLISH_SUBSCRIBE, "fanout");
            channel.queueDeclare("queue1", false, false, false, null);
            channel.queueDeclare("queue2", false, false, false, null);

            channel.queueBind("queue1", PUBLISH_SUBSCRIBE, "");
            channel.queueBind("queue2", PUBLISH_SUBSCRIBE, "");

            String message = "Hello World";

            channel.basicPublish(PUBLISH_SUBSCRIBE, "", null, message.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
