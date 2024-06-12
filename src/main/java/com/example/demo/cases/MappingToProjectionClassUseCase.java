package com.example.demo.cases;

import com.example.demo.domain.Account;
import com.example.demo.dto.AccountDto;
import com.example.demo.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MappingToProjectionClassUseCase {

    private final AccountRepository accountRepository;


    public MappingToProjectionClassUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void execute(String id) {
        Optional<AccountDto> optionalAccountDto = accountRepository.findById(id, AccountDto.class);
        optionalAccountDto.ifPresent(System.out::println);
    }
}
