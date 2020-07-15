package rain.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

@Slf4j
public class FcKafkaConsumer extends Thread {
    private final static String servers = "servers地址";
    private static KafkaConsumer<String, String> consumer;

    public FcKafkaConsumer(List<String> topics) {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        props.put("group.id", "grp_out_" + ip);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(topics);
    }

    public FcKafkaConsumer(String ip, List<String> topics) {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        log.info(ip);
        props.put("group.id", "grp_out_" + ip);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(topics);
    }

    @Override public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String message = record.value();
                String topic = record.topic();
                System.out.println("接收到: " + topic + message);

                switch (topic) {
                    case "topic1":
                        //处理收到的消息
                        System.out.println(topic);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
