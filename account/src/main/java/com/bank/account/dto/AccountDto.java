package com.bank.account.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDto {

    long id;
    @NotNull
    int passwordId;
    @NotNull
    int accountNumber;
    @NotNull
    int bankDetailsId;
    @NotNull
    double money;
    @NotNull
    boolean negativeBalance;
    @NotNull
    int profileId;
}
