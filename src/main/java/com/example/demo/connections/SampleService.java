package com.example.demo.connections;

import com.example.demo.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class SampleService {

    private final AnotherService anotherService;
    private final PersonRepository personRepository;
    private final ExternalService externalService;
    private final TransactionTemplate transactionTemplate;

    public SampleService(AnotherService anotherService, PersonRepository personRepository, ExternalService externalService, TransactionTemplate transactionTemplate) {
        this.anotherService = anotherService;
        this.personRepository = personRepository;
        this.externalService = externalService;
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    public void hello() {
        System.out.println(personRepository.findAll());
    }

    // This method executes with an external service call within a transactional context.
    // The @Transactional annotation ensures that the method runs within a transaction,
    // allowing for database operations to be managed atomically.
    // By setting 'spring.datasource.hikari.auto-commit=false' in the application properties,
    // the Hikari connection pool is configured to disable auto-commit mode. This means that
    // transactions won't be automatically started for each method that interacts with the
    // database. Instead, transactions will only be initiated when explicitly annotated
    // with @Transactional, as demonstrated in this method.
    // Disabling auto-commit mode optimizes the utilization of database connections, as
    // transactions are only initiated when necessary, leading to faster execution times
    // and more efficient resource management.
    @Transactional
    public void withExternalServiceCall() {
        externalService.externalCall();

        System.out.println(personRepository.findAll());
    }

    // In this method, when the external service call is made after accessing the repository,
    // it is advisable to use the 'transactionTemplate' to manage the transaction, especially when we
    // no longer need the database connection after the external service call.
    // Placing the external service call after the database operation allows the transaction to be
    // completed sooner, potentially reducing the time the database connection is held open and
    // improving overall performance.

    //    @Transactional
    public void withExternalServiceCallAfter() {
        transactionTemplate.executeWithoutResult(transactionStatus -> System.out.println(personRepository.findAll()));

        externalService.externalCall();
    }

    // In this method, we use 'transactionTemplate.executeWithoutResult()' to manage the initial transaction
    // and execute the database operation. This ensures that the database transaction is completed and the
    // connection is released promptly after executing the operation, before calling the external service.
    // The 'anotherService.runsInNewTransaction()' method is intended to run in a new transaction. By structuring
    // the code this way, we ensure that the initial transaction is closed before starting a new transaction
    // in the 'anotherService', rather than keeping both transactions open concurrently.
    // This approach helps to optimize resource usage by not holding multiple database connections open
    // simultaneously. However, it is important to carefully manage transaction boundaries and ensure
    // that the logic remains consistent and reliable when splitting transactions this way.

    //    @Transactional
    public void withNestedTransaction() {
        transactionTemplate.executeWithoutResult(transactionStatus -> System.out.println(personRepository.findAll()));

        anotherService.runsInNewTransaction();
    }
}