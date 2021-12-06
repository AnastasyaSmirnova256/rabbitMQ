import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

//его величество Producer
public class Producer {

    private final static String QUEUE_NAME = "products_queue";

    public static void main(String[]args) throws IOException, TimeoutException {
        //объявление соединения к серверу RabbitMQ и открытие канала сообщений
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        //объявление устойчивой очереди с отключенными свойствами эксклюзивности и автоудаления
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "";
        Scanner sc = new Scanner(System.in);
        
        //чтение ввода с клавиатуры, пока не будет введено слово "exit"
        while(!msg.equals("exit")) {
            if (!msg.isEmpty()) {
                //отправка сообщения
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                System.out.println(" [x] Sent '" + msg + "'");
            }
            msg = sc.nextLine();
        }
        // закрытие канала и соединения
        channel.close();
        connection.close();
    }
}
