package com.example.demo.connections;

import com.example.demo.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class AnotherService {

    private final PersonRepository personRepository;
    private final TransactionTemplate transactionTemplate;


    public AnotherService(PersonRepository personRepository, TransactionTemplate transactionTemplate) {
        this.personRepository = personRepository;
        this.transactionTemplate = transactionTemplate;
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Transactional
    public void runsInNewTransaction() {
        transactionTemplate.executeWithoutResult(transactionStatus -> System.out.println(personRepository.findAll()));
        Sleep.sleep(400);
    }
}
