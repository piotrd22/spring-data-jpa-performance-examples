package com.example.demo;

import com.example.demo.domain.Account;
import com.example.demo.domain.Amount;
import com.example.demo.domain.BankTransfer;
import com.example.demo.domain.PhoneNumber;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.BankTransferRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

@TestConfiguration(proxyBeanMethods = false)
public class DemoApplicationTestConfiguration {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest")).withReuse(true);
	}

	@Bean
	ApplicationRunner applicationRunner(AccountRepository accountRepository, BankTransferRepository bankTransferRepository) {
		return args -> {
			Account sender = generateAccount("sender-id");
			sender.addPhoneNumber(new PhoneNumber("111", PhoneNumber.Type.HOME));
			sender = accountRepository.save(sender);

			Account receiver = accountRepository.save(generateAccount("receiver-id"));

			bankTransferRepository.save(generateBankTransfer("bank-transfer-id", sender, receiver));

			for (int i = 0; i < 50; i++) {
				Account account = accountRepository.save(generateAccount(i));
				BankTransfer bankTransfer = generateBankTransfer(i, sender, account);
				bankTransferRepository.save(bankTransfer);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.from(DemoApplication::main)
				.with(DemoApplicationTestConfiguration.class)
				.run(args);
	}

	private Account generateAccount(int i) {
		Account account = new Account();
		account.setId(UUID.randomUUID().toString());
		account.setIban("iban" + i);
		account.setFirstName("John");
		account.setLastName("Doe");
		return account;
	}

	private Account generateAccount(String stringId) {
		Account account = new Account();
		account.setId(stringId);
		account.setIban("iban" + stringId);
		account.setFirstName("John");
		account.setLastName("Doe");
		return account;
	}

	private BankTransfer generateBankTransfer(int i, Account sender, Account receiver) {
		BankTransfer bankTransfer = new BankTransfer();
		bankTransfer.setId(UUID.randomUUID().toString());
		bankTransfer.setReference("reference" + i);
		bankTransfer.setSender(sender);
		bankTransfer.setReceiver(receiver);
		bankTransfer.setAmount(Amount.of(100, "CHF"));
		bankTransfer.setState(BankTransfer.State.CREATED);
		return bankTransfer;
	}

	private BankTransfer generateBankTransfer(String stringId, Account sender, Account receiver) {
		BankTransfer bankTransfer = new BankTransfer();
		bankTransfer.setId(stringId);
		bankTransfer.setReference("reference" + stringId);
		bankTransfer.setSender(sender);
		bankTransfer.setReceiver(receiver);
		bankTransfer.setAmount(Amount.of(100, "CHF"));
		bankTransfer.setState(BankTransfer.State.CREATED);
		return bankTransfer;
	}
}
