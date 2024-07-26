package ru.otus.java.qa.pro.api.annotations;

import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.java.qa.pro.api.extensions.CreatorResolvers;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(CreatorResolvers.class)
public @interface Create {
}
