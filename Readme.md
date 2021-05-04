## Rabbitmq-example 
Use simple code to demonstrate how to use rabbitmq.

### Run rabbitmq by Docker.
To keep it simple, we can quickly run rabbitmq by docker use the following command:
```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```
Then the rabbitmq will start and listen on the 5672 port, and it also provider a web dashboard
host on [localhost:15672](http://localhost:15672)


### Require the rabbitmq client 
In java, in order to communicate with rabbitmq, you need to require the rabbitmq client to you code, which is already included in the pom.xml file.

```
<dependency>
    <groupId>com.rabbitmq</groupId>
    <artifactId>amqp-client</artifactId>
    <version>5.12.0</version>
</dependency>
```

### The code structure
There are serial sub package in src/main/java, each of these represented a use case in rabbitmq. I will list below

#### hello
This package shows how the `Producer` and `Consumer` connect to the rabbitmq server, send or receive message from queue.

#### Workqueue

![](https://www.rabbitmq.com/img/tutorials/python-two.png)
The work queue model means there is one Producer send message to a queue, and have two or more `Consumer` listen to the queue.
Each message could have been received only once.

It is useful in the distributed system. We may deploy many `Consumer` in servers.

The code in this package have one producer and two consumer, the message from producer will send to only one consumer.


#### Publish/Subscribe 

![](https://www.rabbitmq.com/img/tutorials/python-three.png)

In this model, a message from `Producer` will send to the exchanger which the type is `fanout`, then the exchanger will deliver the message to queues which had been bind.

In this example, we first declare a `fanout` exchange and two queue, then bind them together. Now we just need specify the exchanger and no long need specify the queue name when we send a message from `Producer`.

#### Routing

![](https://www.rabbitmq.com/img/tutorials/direct-exchange.png)

In the Publish/Subscribe model, we are able to broadcast a message to many receivers. In the Routing model, we are able to send a message to subset of queues by specify the RoutingKey.

For example, we may want to send the error message a queue, and the warning/debug/info message to another queue.

The different with Publish/Subscribe model is that we need create a `direct` type of `Exchanger`, then bind the queue and exchanger with a RoutingKey.

When a message has been sent from producer, we not only need specify the Exchanger but specify the RoutingKey as well.
