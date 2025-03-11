package com.bank.account.repositories;

import com.bank.account.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс AuditRepository предоставляет методы для работы с сущностью Audit в базе данных.
 * Он наследует функциональность от JpaRepository, что позволяет выполнять стандартные операции
 * CRUD (создание, чтение, обновление, удаление) без необходимости реализации этих методов.
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
}
