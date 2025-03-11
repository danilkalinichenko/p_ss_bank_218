package com.bank.account.services;


import com.bank.account.dto.AccountDto;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    AccountDto updateAccount(Long id, AccountDto accountDto);

    void deleteAccount(Long id);

    void getAccounts();

    AccountDto getAccount(Long id);
}
