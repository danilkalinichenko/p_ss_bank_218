package com.bank.account.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Класс Audit представляет собой сущность аудита в системе.
 * Он отображается на таблицу "audit" в базе данных.
 * Поля:
 * - id: уникальный идентификатор записи аудита.
 * - entityType: тип сущности, к которой относится событие аудита.
 * - operationType: тип операции (например, создание, обновление, удаление).
 * - createdBy: пользователь, который создал запись аудита.
 * - modifiedBy: пользователь, который изменил запись аудита.
 * - createdAt: дата и время создания записи аудита.
 * - modifiedAt: дата и время последнего изменения записи аудита.
 * - newEntityJson: JSON-представление новой сущности после операции.
 * - entityJson: JSON-представление сущности до операции.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "operation_type")
    private String operationType;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by", nullable = false)
    private String modifiedBy;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private ZonedDateTime modifiedAt;

    @Column(name = "new_entity_json", nullable = false)
    private String newEntityJson;

    @Column(name = "entity_json")
    private String entityJson;
}
