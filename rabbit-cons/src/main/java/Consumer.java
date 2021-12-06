import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer {

    private static final String QUEUE_NAME = "products_queue";

    public static void main (String[] args) throws IOException, TimeoutException {
        //объявление соединения к серверу RabbitMQ и открытие канала сообщений
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        //объявление устойчивой очереди с отключенными свойствами эксклюзивности и автоудаления
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
        //его величество consumer
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //метод обработки сообщения
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        // подключение consumer к очереди
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
