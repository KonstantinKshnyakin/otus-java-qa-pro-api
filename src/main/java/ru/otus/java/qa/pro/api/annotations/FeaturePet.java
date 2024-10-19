package ru.otus.java.qa.pro.api.annotations;

import io.qameta.allure.Feature;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Feature("Pet")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FeaturePet {
}