package com.example.demo.cases;

import com.example.demo.domain.BankTransfer;
import com.example.demo.repositories.BankTransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenerateReportUseCase {
    private final BankTransferRepository bankTransferRepository;

    public GenerateReportUseCase(BankTransferRepository bankTransferRepository) {
        this.bankTransferRepository = bankTransferRepository;
    }

    @Transactional
    public void execute(String senderId) {
        // Fetching the list of bank transfers by sender ID
        List<BankTransfer> entries = bankTransferRepository.findBySenderId(senderId);

        // Looping through each bank transfer to print the receiver's IBAN
        // Without using lazy loading, this would cause the N + 1 problem,
        // where N additional queries are executed to fetch each receiver's details.
        // By leveraging query graphs, we can optimize this and avoid the N + 1 issue.
        for (BankTransfer sentTransfer : entries) {
            System.out.println(sentTransfer.getReceiver().getIban());
        }
    }
}
