package com.github.scotts.avro.v1;

import com.github.scotts.avro.ClimbingGym;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class AvroJavaConsumer {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.put("group.id", "climbinggym-consumer-group");
        properties.put("auto.commit.enable", "false");
        properties.put("auto.offset.reset", "earliest");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
        properties.setProperty("schema.registry.url", "http://localhost:8081");
        properties.setProperty("specific.avro.reader", "true");

        KafkaConsumer<String, ClimbingGym> kafkaConsumer = new KafkaConsumer<>(properties);
        String topic = "climbinggym";
        kafkaConsumer.subscribe(Collections.singleton(topic));

        while (true){
            System.out.println("Checking in topic " + topic);
            ConsumerRecords<String, ClimbingGym> records = kafkaConsumer.poll(Duration.ofMillis(5000));

            for (ConsumerRecord<String, ClimbingGym> record : records){
                ClimbingGym climbingGym = record.value();
                System.out.println(climbingGym);
            }

            kafkaConsumer.commitSync();
        }

    }

}
