package com.example.demo.cases;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@UseCaseTest
class MappingToProjectionClassUseCaseTests {

    @Autowired
    private MappingToProjectionClassUseCase useCase;

    @Test
    void executes() {
        useCase.execute("sender-id");
    }
}
