package com.bank.account.mappers;

import com.bank.account.dto.AuditDto;
import com.bank.account.model.Audit;
import org.mapstruct.Mapper;


/**
 * Интерфейс AuditMapper отвечает за преобразование между сущностями Audit и их DTO (Data Transfer Object) представлениями.
 * Использует MapStruct для автоматической генерации кода преобразования.
 */
@Mapper(componentModel = "spring")
public interface AuditMapper {

    /**
     * Преобразует сущность Audit в объект AuditDto.
     *
     * @param audit сущность, которая будет преобразована в DTO.
     * @return преобразованный объект AuditDto.
     */
    AuditDto toDto(Audit audit);

    /**
     * Преобразует объект AuditDto в сущность Audit.
     *
     * @param auditDto объект DTO, который будет преобразован в сущность.
     * @return преобразованная сущность Audit.
     */
    Audit toEntity(AuditDto auditDto);
}
