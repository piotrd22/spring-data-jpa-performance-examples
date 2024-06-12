package com.example.demo.cases;

import com.example.demo.domain.Account;
import com.example.demo.domain.Amount;
import com.example.demo.domain.BankTransfer;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.BankTransferRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterBankTransferUseCase {

    private final AccountRepository accountRepository;
    private final BankTransferRepository bankTransferRepository;

    public RegisterBankTransferUseCase(AccountRepository accountRepository, BankTransferRepository bankTransferRepository) {
        this.accountRepository = accountRepository;
        this.bankTransferRepository = bankTransferRepository;
    }

    // Using @Transactional ensures that all database operations within this method
    // are executed within a single transaction, reducing the number of queries and
    // improving performance.
    @Transactional
    public void execute(String bankTransferId,
                        String reference,
                        String senderId,
                        String receiverId,
                        Amount amount) {
//        If the account is only needed for the relationship and not for any other processing,
//        you can use getReferenceById which is more efficient as it doesn't hit the database unless necessary.
//        https://naveen-metta.medium.com/mastering-the-use-of-getreferencebyid-and-findbyid-methods-in-spring-data-jpa-deac16b46456#:~:text=getReferenceById()%20Method%3A&text=While%20it%20serves%20the%20same,its%20approach%20is%20notably%20different.&text=Usage%3A%20getReferenceById()%20shines%20when,reference%20proxy%20to%20the%20entity.

//        Account sender = accountRepository.findByIdOrThrow(senderId);
//        Account receiver = accountRepository.findByIdOrThrow(receiverId);

        Account sender = accountRepository.getReferenceById(senderId);
        Account receiver = accountRepository.getReferenceById(receiverId);

        BankTransfer bankTransfer = new BankTransfer();
        bankTransfer.setId(bankTransferId);
        bankTransfer.setReference(reference);
        bankTransfer.setSender(sender);
        bankTransfer.setReceiver(receiver);
        bankTransfer.setAmount(amount);
        bankTransfer.setState(BankTransfer.State.CREATED);

        bankTransferRepository.save(bankTransfer);
    }
}
