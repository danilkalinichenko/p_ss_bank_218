package com.bank.account.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс Account представляет собой сущность учетной записи в системе.
 * Он отображается на таблицу "account_details" в базе данных.
 * Поля:
 * - id: уникальный идентификатор учетной записи.
 * - passwordId: идентификатор пароля, связанного с учетной записью.
 * - accountNumber: номер учетной записи (должен быть уникальным).
 * - bankDetailsId: идентификатор банковских реквизитов (должен быть уникальным).
 * - money: сумма денег на счету.
 * - negativeBalance: флаг, указывающий, может ли счет иметь отрицательный баланс.
 * - profileId: идентификатор профиля, связанного с учетной записью.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_details")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "password_id")
    private int passwordId;

    @Column(name = "account_number", unique = true, nullable = false)
    private int accountNumber;

    @Column(name = "bank_details_id", unique = true, nullable = false)
    private int bankDetailsId;

    @Column(name = "money")
    private double money;

    @Column(name = "negative_balance")
    private boolean negativeBalance;

    @Column(name = "profile_id")
    private int profileId;
}
