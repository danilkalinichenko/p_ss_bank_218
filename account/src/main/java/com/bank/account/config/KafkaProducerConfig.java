package com.bank.account.config;

import com.bank.account.dto.AccountDto;
import com.bank.account.dto.AuditDto;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация для Kafka Producer, включая настройки для отправки сообщений.
 * Поля:
 * - kafkaServer: адреса серверов Kafka, используемые для отправки сообщений.
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    /**
     * Создает конфигурацию для производителя Kafka.
     *
     * @return карта с настройками для производителя Kafka.
     */
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    /**
     * Создает фабрику производителей для отправки сообщений типа AccountDto.
     *
     * @return ProducerFactory для AccountDto, настроенный с необходимыми параметрами.
     */
    @Bean
    public ProducerFactory<String, AccountDto> producerFactoryAccountDto() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Создает KafkaTemplate для отправки сообщений типа AccountDto.
     *
     * @return KafkaTemplate для AccountDto.
     */
    @Bean
    public KafkaTemplate<String, AccountDto> kafkaTemplateAccountDto() {
        return new KafkaTemplate<>(producerFactoryAccountDto());
    }

    /**
     * Создает фабрику производителей для отправки сообщений типа AuditDto.
     *
     * @return ProducerFactory для AuditDto, настроенный с необходимыми параметрами.
     */
    @Bean
    public ProducerFactory<String, AuditDto> producerFactoryAuditDto() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Создает KafkaTemplate для отправки сообщений типа AuditDto.
     *
     * @return KafkaTemplate для AuditDto.
     */
    @Bean
    public KafkaTemplate<String, AuditDto> kafkaTemplateAuditDto() {
        return new KafkaTemplate<>(producerFactoryAuditDto());
    }
}
