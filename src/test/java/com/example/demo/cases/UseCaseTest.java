package com.example.demo.cases;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.demo.DemoApplicationTestConfiguration;
import org.quickperf.junit5.QuickPerfTest;
import org.quickperf.spring.sql.QuickPerfSqlConfig;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest
@QuickPerfTest
@Import({DemoApplicationTestConfiguration.class, QuickPerfSqlConfig.class})
public @interface UseCaseTest {
}
