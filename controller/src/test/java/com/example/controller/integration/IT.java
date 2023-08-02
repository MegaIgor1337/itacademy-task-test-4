package com.example.controller.integration;

import com.example.controller.TestApplicationRunner;
import com.example.controller.application.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = {Application.class, TestApplicationRunner.class})
@Transactional
public @interface IT {
}

