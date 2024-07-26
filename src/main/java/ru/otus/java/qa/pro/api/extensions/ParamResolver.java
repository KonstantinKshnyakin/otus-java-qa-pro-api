package ru.otus.java.qa.pro.api.extensions;

import com.google.inject.Inject;
import net.datafaker.Faker;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.otus.java.qa.pro.api.utlil.InjectorManager;

public class ParamResolver implements ParameterResolver {

    @Inject
    private Faker faker;

    public ParamResolver() {
        InjectorManager.injectMembers(this);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Faker.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return faker;
    }

}
