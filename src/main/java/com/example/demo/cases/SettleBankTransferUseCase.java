package com.example.demo.cases;

import com.example.demo.domain.BankTransfer;
import com.example.demo.repositories.BankTransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettleBankTransferUseCase {
    private final BankTransferRepository bankTransferRepository;

    public SettleBankTransferUseCase(BankTransferRepository bankTransferRepository) {
        this.bankTransferRepository = bankTransferRepository;
    }

    // This method executes within a transactional context.
    // Thanks to the @DynamicUpdate annotation on the BankTransfer entity,
    // the database update query will only update the necessary fields,
    // such as the 'state', without affecting other columns.
    // Additionally, lazy loading ensures that associated entities,
    // such as 'sender' and 'receiver', are not fetched unnecessarily,
    // minimizing database queries and improving performance.
    @Transactional
    public void execute(String bankTransferId) {
        BankTransfer bankTransfer = bankTransferRepository.findByIdOrThrow(bankTransferId);
        bankTransfer.settle();
        bankTransferRepository.save(bankTransfer);
    }
}
