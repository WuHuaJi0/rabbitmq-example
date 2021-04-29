package hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            /**
             * 参数：
             * queue: 队列名
             * durable: 是否持久化； true 的话，重启rabbitmq队列还存在；
             * exclusive: 是否这个链接独占(restricted to this connection)
             * autoDelete: server will delete it when no longer in use. 怎么判断不在使用呢？ 应该是exclusive是true,然后链接关闭了吧。
             * arguments: 其他参数，通过一个map来传入
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World, 你好世界";

            /**
             * 参数：
             * exchange: 交换机,填一个空字符串，就是默认的
             * routingKey: 路由key, 交换机根据routingkey 转发到对应的队列，没有交换机就填队列名称
             * BasicProperties props: 消息的附加属性
             * byte[] body: 消息体
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
