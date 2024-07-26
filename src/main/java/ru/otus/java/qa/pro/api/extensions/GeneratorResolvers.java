package ru.otus.java.qa.pro.api.extensions;

import com.google.inject.Inject;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import ru.otus.java.qa.pro.api.annotations.Generate;
import ru.otus.java.qa.pro.api.extensions.util.Generator;
import ru.otus.java.qa.pro.api.utlil.InjectorManager;
import java.lang.reflect.Parameter;

public class GeneratorResolvers implements ParameterResolver {

    @Inject
    protected Generator generator;

    public GeneratorResolvers() {
        InjectorManager.injectMembers(this);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        Class<?> type = parameter.getType();
        return AnnotationSupport.isAnnotated(parameter, Generate.class)
                && generator.contains(type);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        return generator.get(type);
    }

}
