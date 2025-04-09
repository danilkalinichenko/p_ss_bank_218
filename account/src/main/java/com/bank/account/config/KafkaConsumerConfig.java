package com.bank.account.config;

import com.bank.account.dto.AccountDto;
import com.bank.account.dto.AuditDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.core.KafkaTemplate;


import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация для Kafka, включая настройки для потребителей и производителей.
 * Поля:
 * - accountBootstrapServers: адреса серверов Kafka для потребителей учетных записей.
 * - auditBootstrapServers: адреса серверов Kafka для производителей аудита.
 * - TRUSTED_PACKAGES: пакеты, которые доверяются для десериализации.
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String auditBootstrapServers;

    @Value("${spring.kafka.bootstrap-servers}")
    private String accountBootstrapServers;


    private static final String TRUSTED_PACKAGES = "com.bank.account.dto";

    /**
     * Создает фабрику потребителей для обработки сообщений типа AccountDto.
     *
     * @return ConsumerFactory для AccountDto, настроенный с необходимыми параметрами.
     */
    @Bean
    public ConsumerFactory<String, AccountDto> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, accountBootstrapServers);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, TRUSTED_PACKAGES);
        configProps.put(JsonDeserializer.TYPE_MAPPINGS, TRUSTED_PACKAGES);

        try (JsonDeserializer<AccountDto> jsonDeserializer = new JsonDeserializer<>(AccountDto.class)) {
            return new DefaultKafkaConsumerFactory<>(
                    configProps,
                    new StringDeserializer(),
                    jsonDeserializer.trustedPackages(TRUSTED_PACKAGES));
        }
    }

    /**
     * Создает фабрику контейнеров слушателей Kafka для обработки сообщений типа AccountDto.
     *
     * @return ConcurrentKafkaListenerContainerFactory для AccountDto.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AccountDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    /**
     * Создает фабрику производителей для отправки сообщений типа AuditDto.
     *
     * @return ProducerFactory для AuditDto, настроенный с необходимыми параметрами.
     */
    @Bean
    public ProducerFactory<String, AuditDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, auditBootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Создает KafkaTemplate для отправки сообщений типа AuditDto.
     *
     * @return KafkaTemplate для AuditDto.
     */
    @Bean
    public KafkaTemplate<String, AuditDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
