package com.bank.account.kafka;


import com.bank.account.dto.AuditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Класс AuditProducer отвечает за отправку событий аудита в Kafka.
 * Он использует KafkaTemplate для отправки сообщений в указанный топик.
 * Поля:
 * - kafkaTemplateAuditDto: шаблон Kafka для отправки событий аудита.
 * - auditTopic: название топика для логов аудита.
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuditProducer {

    // KafkaTemplate для событий аудита
    private final KafkaTemplate<String, AuditDto> kafkaTemplateAuditDto;

    @Value("${kafka.topic.audit.log:audit.log}") // Топик для логов аудита
    private String auditTopic;

    /**
     * Отправляет событие аудита.
     *
     * @param auditDto DTO события аудита, которое будет отправлено.
     */
    public void sendAuditEvent(AuditDto auditDto) {
        // Ключ может быть ID сущности или другим идентификатором, если нужен порядок
        String key = auditDto.getEntityType() + "_" + System.currentTimeMillis(); // Пример ключа
        log.info("Отправка события аудита типа '{}', операция '{}' в топик {}",
                auditDto.getEntityType(), auditDto.getOperationType(), auditTopic);

        CompletableFuture<SendResult<String, AuditDto>> future = kafkaTemplateAuditDto.send(auditTopic, key, auditDto);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Событие аудита успешно отправлено в топик [{}], ключ [{}], смещение [{}]",
                        auditTopic, key, result.getRecordMetadata().offset());
            } else {
                log.error("Ошибка отправки события аудита в топик [{}], ключ [{}]: {}",
                        auditTopic, key, ex.getMessage(), ex);
                // Обработка ошибок отправки аудита
            }
        });
    }
}
