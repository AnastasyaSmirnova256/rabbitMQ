import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {

    private final static String QUEUE_NAME = "products_queue";

    /**
     * Какой-то мейн
     * @param args какие-то парамы
     * @throws IOException ошибка 1
     * @throws TimeoutException ошибка 2
     */
    public static void main(String[]args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "";
        Scanner sc = new Scanner(System.in);
        while(!msg.equals("exit")) {
            if (!msg.isEmpty()) {
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                System.out.println(" [x] Sent '" + msg + "'");
            }
            msg = sc.nextLine();
        }

        channel.close();
        connection.close();
    }
}