package ru.otus.java.qa.pro.api.extensions;

import com.google.inject.Inject;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import ru.otus.java.qa.pro.api.annotations.Create;
import ru.otus.java.qa.pro.api.extensions.util.Creator;
import ru.otus.java.qa.pro.api.utlil.InjectorManager;
import java.lang.reflect.Parameter;

public class CreatorResolvers implements ParameterResolver, AfterAllCallback {

    @Inject
    protected Creator creator;

    public CreatorResolvers() {
        InjectorManager.injectMembers(this);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        Class<?> type = parameter.getType();
        return AnnotationSupport.isAnnotated(parameter, Create.class)
                && creator.contains(type);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        return creator.get(type);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        creator.deleteAll();
    }

}
