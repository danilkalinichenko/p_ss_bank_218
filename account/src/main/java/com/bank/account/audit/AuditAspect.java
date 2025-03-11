package com.bank.account.audit;

import com.bank.account.dto.AuditDto;
import com.bank.account.services.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;


/**
 * Класс AuditAspect отвечает за логирование изменений в учетных записях.
 * Он использует аспектно-ориентированное программирование (AOP) для перехвата
 * методов создания и обновления учетных записей в AccountServiceImpl.
 * Поля:
 * - ENTITY_TYPE: тип сущности, который фиксируется в аудите (в данном случае "Account").
 * - auditService: сервис для логирования изменений.
 * - objectMapper: объект для преобразования объектов в JSON.
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuditAspect {

    private final static String ENTITY_TYPE = "Account";
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    /**
     * Логирует создание учетной записи.
     *
     * @param joinPoint информация о соединении, позволяющая получить доступ к методу, который был вызван.
     * @param result    результат выполнения метода createAccount.
     */
    @AfterReturning(pointcut = "execution(* com.bank.account.services.AccountServiceImpl.createAccount(..))",
            returning = "result")
    public void logCreateAccount(JoinPoint joinPoint, Object result) {
        logAudit(joinPoint, result);
    }

    /**
     * Логирует обновление учетной записи.
     *
     * @param joinPoint информация о соединении, позволяющая получить доступ к методу, который был вызван.
     * @param result    результат выполнения метода updateAccount.
     */
    @AfterReturning(pointcut = "execution(* com.bank.account.services.AccountServiceImpl.updateAccount(..))",
            returning = "result")
    public void logUpdateAccount(JoinPoint joinPoint, Object result) {
        logAudit(joinPoint, result);
    }

    /**
     * Логирует изменения в учетной записи.
     *
     * @param joinPoint информация о соединении, позволяющая получить доступ к методу, который был вызван.
     * @param result    результат выполнения метода, который был вызван.
     */
    private void logAudit(JoinPoint joinPoint, Object result) {
        try {
            final String username = getCurrentUser();
            final String operationType = joinPoint.getSignature().getName();
            final String entityJson = objectMapper.writeValueAsString(result);
            final AuditDto auditDto = new AuditDto();
            auditDto.setEntityType(ENTITY_TYPE);
            auditDto.setOperationType(operationType);
            auditDto.setCreatedBy(username);
            auditDto.setCreatedAt(ZonedDateTime.now());
            auditDto.setEntityJson(entityJson);
            auditDto.setNewEntityJson(entityJson);
            auditService.logChange(auditDto);

        } catch (Exception e) {
            log.error("Ошибка при логирование аудита : {}", e.getMessage(), e);
            throw new RuntimeException("Не удалось создать аудит", e);
        }
    }

    /**
     * Получает имя текущего пользователя из контекста безопасности.
     *
     * @return имя текущего пользователя.
     */
    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
