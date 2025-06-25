package com.shifterwebapp.shifter.account;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account account);
    List<AccountDto> toDto(List<Account> accounts);

    @InheritInverseConfiguration
    Account toEntity(AccountDto accountDto);
    @InheritInverseConfiguration
    List<Account> toEntity(List<AccountDto> accountDtos);
}
