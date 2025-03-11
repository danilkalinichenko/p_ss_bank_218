package com.bank.account.services;


import com.bank.account.dto.AccountDto;
import com.bank.account.mappers.AccountMapper;
import com.bank.account.model.Account;
import com.bank.account.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Класс AccountServiceImpl реализует интерфейс AccountService и предоставляет
 * методы для управления учетными записями, включая создание, обновление, удаление
 * и получение учетных записей.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    /**
     * Создает новую учетную запись.
     *
     * @param accountDto DTO учетной записи, которую нужно создать.
     * @return созданная учетная запись в виде DTO.
     * @throws RuntimeException если возникает ошибка доступа к данным.
     */
    @Override
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        try {
            Objects.requireNonNull(accountDto, "DTO учетных данных не может быть null");
            Account account = accountMapper.toEntity(accountDto);
            Account savedAccount = accountRepository.save(account);
            return accountMapper.toDto(savedAccount);
        } catch (DataAccessException e) {
            log.error("Ошибка базы данных при создании учетной записи: {}", e.getMessage());
            throw new RuntimeException("Ошибка при создании учетной записи", e);
        }
    }

    /**
     * Обновляет существующую учетную запись.
     *
     * @param id идентификатор учетной записи, которую нужно обновить.
     * @param accountDto DTO учетной записи с новыми данными.
     * @return обновленная учетная запись в виде DTO.
     * @throws EntityNotFoundException если учетная запись не найдена.
     */
    @Override
    @Transactional
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Учетная запись не найдена"));
        existingAccount.setPasswordId(accountDto.getPasswordId());
        existingAccount.setAccountNumber(accountDto.getAccountNumber());
        existingAccount.setBankDetailsId(accountDto.getBankDetailsId());
        existingAccount.setMoney(accountDto.getMoney());
        existingAccount.setNegativeBalance(accountDto.isNegativeBalance());
        existingAccount.setProfileId(accountDto.getProfileId());
        Account updatedAccount = accountRepository.save(existingAccount);
        return accountMapper.toDto(updatedAccount);
    }

    /**
     * Удаляет учетную запись по идентификатору.
     *
     * @param id идентификатор учетной записи, которую нужно удалить.
     * @throws EntityNotFoundException если учетная запись не найдена.
     */
    @Override
    @Transactional
    public void deleteAccount(Long id) {
        try {
            if (!accountRepository.existsById(id)) {
                throw new EntityNotFoundException("Учетная запись с ID " + id + " не найдена");
            }
            accountRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            log.error("Ошибка при удалении учетной записи: {}", e.getMessage());
            throw new RuntimeException("Не удалось удалить учетную запись", e);
        }
    }

    /**
     * Получает список всех учетных записей.
     */
    @Override
    @Transactional(readOnly = true)
    public void getAccounts() {
        List<Account> account = accountRepository.findAll();
        if (account.isEmpty()) {
            log.warn("Не найдено ни одной учетной записи.");
        }
        accountMapper.toDtoList(account);
    }

    /**
     * Получает учетную запись по идентификатору.
     *
     * @param id идентификатор учетной записи, которую нужно получить.
     * @return учетная запись в виде DTO.
     * @throws EntityNotFoundException если учетная запись не найдена.
     */
    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Учетная запись с ID " + id + " не найдена"));
        return accountMapper.toDto(account);
    }
}
