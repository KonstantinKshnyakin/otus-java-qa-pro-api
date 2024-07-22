package ru.otus.java.qa.pro.api.extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.extension.*;

public class GuiceExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        Injector injector = Guice.createInjector();
        injector.injectMembers(testInstance);
    }

}
