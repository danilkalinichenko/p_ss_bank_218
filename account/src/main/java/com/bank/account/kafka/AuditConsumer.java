package com.bank.account.kafka;

import com.bank.account.dto.AuditDto;
import com.bank.account.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * Класс AuditConsumer отвечает за обработку событий аудита, получаемых из Kafka.
 * Он слушает определенный топик и вызывает сервис аудита для сохранения событий.
 * Поля:
 * - auditService: сервис для управления логированием аудита.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuditConsumer {

    private final AuditService auditService;

    /**
     * Обрабатывает события аудита, полученные из Kafka.
     *
     * @param record объект ConsumerRecord, содержащий данные события аудита.
     * @param ack объект Acknowledgment для подтверждения обработки сообщения.
     */
    @KafkaListener(topics = "${kafka.topic.audit.log:audit.log}",
            groupId = "${kafka.group.id:audit-service-group}",
            containerFactory = "auditKafkaListenerContainerFactory")
    public void listenAuditLog(ConsumerRecord<String, AuditDto> record, Acknowledgment ack) {
        AuditDto auditDto = record.value();
        String key = record.key();
        log.debug("Получено событие аудита. Ключ: {}, Сообщение: {}", key, auditDto);
        try {
            auditService.logChange(auditDto);
            ack.acknowledge(); // Подтверждаем сохранение в БД
            log.debug("Событие аудита для ключа {} успешно обработано и сохранено.", key);
        } catch (Exception e) {
            log.error("Критическая ошибка при сохранении события аудита для ключа {}: {}", key, e.getMessage(), e);
        }
    }
}
