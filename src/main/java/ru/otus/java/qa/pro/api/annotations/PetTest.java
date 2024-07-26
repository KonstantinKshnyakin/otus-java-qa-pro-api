package ru.otus.java.qa.pro.api.annotations;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.java.qa.pro.api.extensions.GuiceExtension;
import ru.otus.java.qa.pro.api.extensions.ParamResolver;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({GuiceExtension.class, ParamResolver.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public @interface PetTest {
}
