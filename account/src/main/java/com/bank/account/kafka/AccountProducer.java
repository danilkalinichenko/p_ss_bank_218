package com.bank.account.kafka;


import com.bank.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Класс AccountProducer отвечает за отправку событий, связанных с учетными записями,
 * в Kafka. Он использует KafkaTemplate для отправки сообщений в определенные топики.
 * Поля:
 * - accountkafkaTemplate: шаблон Kafka для отправки сообщений типа AccountDto.
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountProducer {

    private final KafkaTemplate<String, AccountDto> accountkafkaTemplate;

    /**
     * Отправляет событие создания учетной записи в Kafka.
     *
     * @param accountDto объект учетной записи, который будет отправлен.
     */
    public void sendCreateAccountEvent(AccountDto accountDto) {
        sendMessage("account.create", accountDto);
    }

    /**
     * Отправляет событие обновления учетной записи в Kafka.
     *
     * @param accountDto объект учетной записи, который будет отправлен.
     */
    public void sendUpdateAccountEvent(AccountDto accountDto) {
        sendMessage("account.update", accountDto);
    }

    /**
     * Отправляет событие удаления учетной записи в Kafka.
     *
     * @param accountDto объект учетной записи, который будет отправлен.
     */
    public void sendDeleteAccountEvent(AccountDto accountDto) {
        sendMessage("account.delete", accountDto);
    }

    /**
     * Отправляет сообщение в указанный топик Kafka.
     *
     * @param topic название топика, в который будет отправлено сообщение.
     * @param message объект учетной записи, который будет отправлен.
     */
    private void sendMessage(String topic, AccountDto message) {
        try {
            accountkafkaTemplate.send(topic, message).get(); // Блокирует до завершения отправки
            log.info("Сообщение отправлено в топик {}: {}", topic, message);
        } catch (Exception ex) {
            log.error("Ошибка при отправке сообщения в Kafka: {}", ex.getMessage(), ex);
            throw new RuntimeException("Ошибка отправки события", ex);
        }
    }
}