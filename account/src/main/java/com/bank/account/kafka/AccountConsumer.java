package com.bank.account.kafka;


import com.bank.account.dto.AccountDto;
import com.bank.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * Класс AccountConsumer отвечает за обработку событий, связанных с учетными записями,
 * получаемых из Kafka. Он слушает определенные топики и вызывает соответствующие методы
 * сервиса учетных записей для выполнения операций.
 * Поля:
 * - accountService: сервис для управления учетными записями.
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountConsumer {

    private final AccountService accountService;

    /**
     * Обрабатывает события создания учетной записи.
     *
     * @param accountDto объект ConsumerRecord, содержащий данные учетной записи.
     */
    @KafkaListener(topics = "account.create")
    public void consumeCreateAccountEvent(ConsumerRecord<String, AccountDto> accountDto) {
        log.info("Получен запрос на создание учетной записи");
        accountService.createAccount(accountDto.value());
    }

    /**
     * Обрабатывает события обновления учетной записи.
     *
     * @param accountDto объект ConsumerRecord, содержащий данные учетной записи.
     */
    @KafkaListener(topics = "account.update")
    public void consumeUpdateAccountEvent(ConsumerRecord<String, AccountDto> accountDto) {
        log.info("Получен запрос на обновление учетной записи");
        accountService.updateAccount(accountDto.value().getId(), accountDto.value());
    }

    /**
     * Обрабатывает события удаления учетной записи.
     *
     * @param accountDto объект ConsumerRecord, содержащий данные учетной записи.
     */
    @KafkaListener(topics = "account.delete")
    public void consumeDeleteAccountEvent(ConsumerRecord<String, AccountDto> accountDto) {
        log.info("Получен запрос на удаление учетной записи");
        accountService.deleteAccount(accountDto.value().getId());
    }

    /**
     * Обрабатывает запросы на получение учетных записей.
     */
    @KafkaListener(topics = "accounts.get")
    public void consumeGetAccountsRequest() {
        log.info("Получен запрос на получение учетных записей");
        accountService.getAccounts();
    }
}
