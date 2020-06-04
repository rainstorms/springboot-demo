package rain.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class MyKafkaProducer {
    private final static String TOPIC = "push_webSocket";
    private final static String servers = "servers地址";

    public MyKafkaProducer() {
    }

    public MyKafkaProducer(String message) {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        // 这段应该可以提取出来  要发消息的时候调用一下
        try {
            producer.send(new ProducerRecord<>(TOPIC, message)).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }

        producer.close();
    }


    // 以下为猜想代码 start
    public static Producer<String, String> producer;

    static {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    public static void sendMessage(String message) {
        try {
            producer.send(new ProducerRecord<>(TOPIC, message)).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        producer.close();
    }

    public static void main(String[] args) {
        MyKafkaProducer.sendMessage("xx");
    }

    // 以上为猜想代码  end
}
