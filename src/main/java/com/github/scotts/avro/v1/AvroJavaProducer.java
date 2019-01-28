package com.github.scotts.avro.v1;

import com.github.scotts.avro.ClimbingGym;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class AvroJavaProducer {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "10");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://localhost:8081");

        Producer<String, ClimbingGym> producer = new KafkaProducer<String, ClimbingGym>(properties);
        String topic = "climbinggym";

        ClimbingGym.Builder climbingGymBuilder = ClimbingGym.newBuilder();
        climbingGymBuilder.setGymName("Inner Peaks");
        climbingGymBuilder.setGymNickname("New Gym or South End");
        climbingGymBuilder.setLocation("Charlotte, NC");
        climbingGymBuilder.setHastopropeclimbing(true);
        climbingGymBuilder.setHasleadclimbing(true);
        climbingGymBuilder.setHasbouldering(true);
        climbingGymBuilder.setHasspeedclimbing(true);
        ClimbingGym climbingGym = climbingGymBuilder.build();

        ProducerRecord<String, ClimbingGym> producerRecord = new ProducerRecord<String, ClimbingGym>(
                topic, climbingGym
        );

        System.out.println(climbingGym);
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    System.out.println("metadata offset: " + metadata.offset());
                    System.out.println("metadata partition: " + metadata.partition());
                    System.out.println("metadata topic: " + metadata.topic());
                } else {
                    exception.printStackTrace();
                }
            }
        });

        producer.flush();
        producer.close();

    }

}
