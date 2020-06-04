package rain.kafka;


import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component @Slf4j
public class FcKafkaConsumerInit implements ApplicationListener<ContextRefreshedEvent> {

    @Override public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new FcKafkaConsumer(Lists.newArrayList("topic1", "topic2")).start();
    }

}
