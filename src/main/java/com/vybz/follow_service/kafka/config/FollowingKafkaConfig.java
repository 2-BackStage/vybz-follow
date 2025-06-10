package com.vybz.follow_service.kafka.config;

import com.vybz.follow_service.kafka.event.FollowingEvent;
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
public class FollowingKafkaConfig {

    private final CommonKafkaConfig commonKafkaConfig;

    @Bean
    public ConsumerFactory<String, FollowingEvent> followingEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(FollowingEvent.class, false))
        );
    }

    @Bean
    public ConsumerFactory<String, String> stringFollowingEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(String.class, false))
        );
    }

    @Bean(name = "followingKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, FollowingEvent> followingKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FollowingEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(followingEventConsumerFactory());
        return factory;
    }

    @Bean(name = "stringFollowingKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> stringFollowingKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringFollowingEventConsumerFactory());
        return factory;
    }

}
