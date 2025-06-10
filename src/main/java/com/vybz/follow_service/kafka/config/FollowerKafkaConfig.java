package com.vybz.follow_service.kafka.config;

import com.vybz.follow_service.kafka.event.FollowerEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class FollowerKafkaConfig {

    private final CommonKafkaConfig commonKafkaConfig;

    @Bean
    public ConsumerFactory<String, FollowerEvent> followerEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(FollowerEvent.class, false))
        );
    }

    @Bean
    public ConsumerFactory<String, String> stringFollowerEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(String.class, false))
        );
    }

    @Bean(name = "followerKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, FollowerEvent> followerKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FollowerEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(followerEventConsumerFactory());
        return factory;
    }

    @Bean(name = "stringFollowerKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> stringFollowerKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringFollowerEventConsumerFactory());
        return factory;
    }

}
