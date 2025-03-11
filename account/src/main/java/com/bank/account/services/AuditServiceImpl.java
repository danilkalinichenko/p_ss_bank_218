package com.bank.account.services;

import com.bank.account.dto.AuditDto;
import com.bank.account.mappers.AuditMapper;
import com.bank.account.model.Audit;
import com.bank.account.repositories.AuditRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * Класс AuditServiceImpl реализует интерфейс AuditService и предоставляет
 * методы для логирования изменений и получения записей аудита.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    /**
     * Логирует изменения, сохраняя запись аудита в базе данных.
     *
     * @param auditDto DTO записи аудита, которую нужно сохранить.
     */
    @Override
    @Transactional
    public void logChange(AuditDto auditDto) {
        final Audit audit = auditMapper.toEntity(auditDto);
        try {
            log.debug("Сохранение аудита: {}", audit);
            auditRepository.save(audit);
            log.info("Запись аудита сохранена для сущности типа '{}', операция '{}'", audit.getEntityType(), audit.getOperationType());
        } catch (DataAccessException e) {
            log.error("Ошибка при сохранении аудита: {}", e.getMessage(), e);
        }
    }

    /**
     * Получает запись аудита по идентификатору.
     *
     * @param id идентификатор записи аудита, которую нужно получить.
     * @return запись аудита.
     * @throws EntityNotFoundException если запись аудита не найдена.
     */
    @Override
    @Transactional
    public Audit getAudit(Long id) {
        log.info("Запрос аудита с ID: {}", id);
        return auditRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Аудит не найден с ID: " + id));
    }
}
