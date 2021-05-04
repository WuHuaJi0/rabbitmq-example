package routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static final String PUBLISH_SUBSCRIBE = "routing";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(PUBLISH_SUBSCRIBE, "direct");

            channel.queueDeclare("error", false, false, false, null);
            channel.queueDeclare("normal", false, false, false, null);

            channel.queueBind("error", PUBLISH_SUBSCRIBE, "error");
            channel.queueBind("normal", PUBLISH_SUBSCRIBE, "warning");
            channel.queueBind("normal", PUBLISH_SUBSCRIBE, "info");
            channel.queueBind("normal", PUBLISH_SUBSCRIBE, "debug");

            channel.basicPublish(PUBLISH_SUBSCRIBE, "error", null, "A error message".getBytes());
            channel.basicPublish(PUBLISH_SUBSCRIBE, "warning", null, "A warning message".getBytes());
            channel.basicPublish(PUBLISH_SUBSCRIBE, "debug", null, "A debug message".getBytes());
            channel.basicPublish(PUBLISH_SUBSCRIBE, "info", null, "A info message".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
