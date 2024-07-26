package ru.otus.java.qa.pro.api.utlil;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InjectorManager {

    private static final ThreadLocal<Injector> INJECTOR = ThreadLocal.withInitial(Guice::createInjector);

    public static Injector getInjector() {
        return INJECTOR.get();
    }

    public static void injectMembers(Object instance) {
        INJECTOR.get().injectMembers(instance);
    }

    public static void remove() {
        INJECTOR.remove();
    }

}
