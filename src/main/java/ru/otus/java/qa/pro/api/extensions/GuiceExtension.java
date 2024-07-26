package ru.otus.java.qa.pro.api.extensions;

import org.junit.jupiter.api.extension.*;
import ru.otus.java.qa.pro.api.utlil.InjectorManager;

public class GuiceExtension implements TestInstancePostProcessor, TestInstancePreDestroyCallback {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        InjectorManager.injectMembers(testInstance);
    }

    @Override
    public void preDestroyTestInstance(ExtensionContext context) throws Exception {
        InjectorManager.remove();
    }

}
