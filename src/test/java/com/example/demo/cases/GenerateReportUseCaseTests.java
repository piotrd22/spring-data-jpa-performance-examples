package com.example.demo.cases;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

@UseCaseTest
class GenerateReportUseCaseTests {

    @Autowired
    private GenerateReportUseCase useCase;

    @Test
    void executes() {
        useCase.execute("sender-id");
    }
}
