package orderservice.messageque;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@EnableKafka //카프카에서 사용할수있도록 어노테이션 추가
@Configuration //설정!
public class KafkaProducerConfig {

    //접속할수있는 카프카 정보가 있는 객체 생성 <프로듀셔>
    @Bean
    public ProducerFactory<String,String> producerFactory(){
        Map<String,Object> properties=new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"172.18.0.101:9092");
        // 이거떄문에 계속 rg.apache.kafka.common.config.ConfigException: Invalid value class org.apache.kafka.common.serialization.StringDeserializer for configuration key.serializer:
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);//키의직렬화 , 데이터 타입
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); //VALUE값 직렬화

        return new DefaultKafkaProducerFactory<>(properties);
    }

    //데이터를 전달하는 인스턴스
    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
