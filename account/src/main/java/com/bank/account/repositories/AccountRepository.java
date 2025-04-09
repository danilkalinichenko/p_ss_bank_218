package com.bank.account.repositories;

import com.bank.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс AccountRepository предоставляет методы для работы с сущностью Account в базе данных.
 * Он наследует функциональность от JpaRepository, что позволяет выполнять стандартные операции
 * CRUD (создание, чтение, обновление, удаление) без необходимости реализации этих методов.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
