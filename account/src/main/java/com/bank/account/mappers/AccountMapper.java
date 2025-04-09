package com.bank.account.mappers;

import com.bank.account.dto.AccountDto;
import com.bank.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Интерфейс AccountMapper отвечает за преобразование между сущностями Account и их DTO (Data Transfer Object) представлениями.
 * Использует MapStruct для автоматической генерации кода преобразования.
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    /**
     * Преобразует объект AccountDto в сущность Account.
     *
     * @param accountDto объект DTO, который будет преобразован в сущность.
     * @return преобразованная сущность Account.
     */
    @Mapping(target = "id", ignore = true) // Игнорируем поле id при преобразовании
    Account toEntity(AccountDto accountDto);

    /**
     * Преобразует сущность Account в объект AccountDto.
     *
     * @param account сущность, которая будет преобразована в DTO.
     * @return преобразованный объект AccountDto.
     */
    AccountDto toDto(Account account);

    /**
     * Преобразует список сущностей Account в список объектов AccountDto.
     *
     * @param accountList список сущностей, который будет преобразован в список DTO.
     * @return список преобразованных объектов AccountDto.
     */
    List<AccountDto> toDtoList(List<Account> accountList);
}
