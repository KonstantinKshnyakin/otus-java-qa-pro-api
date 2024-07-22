package ru.otus.java.qa.pro.api.extensions;

import net.datafaker.Faker;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.otus.java.qa.pro.api.dto.pet.Category;
import ru.otus.java.qa.pro.api.dto.pet.Pet;
import ru.otus.java.qa.pro.api.dto.pet.Tag;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

public class ParameterResolvers implements ParameterResolver {

    private final Faker faker = new Faker();
    private HashMap<Class<?>, Supplier<?>> resolvers = new HashMap<>();

    {
        resolvers.put(Pet.class, this::genPet);
        resolvers.put(Category.class, this::genCategory);
        resolvers.put(Tag.class, this::genTag);
        resolvers.put(Faker.class, () -> faker);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        return resolvers.containsKey(type);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        return resolvers.get(type).get();
    }

    private Pet genPet() {
        return new Pet()
                .setName(faker.dog().name())
                .setPhotoUrls(
                        Arrays.asList(faker.text().text(5), faker.text().text(5), faker.text().text(5))
                );
    }

    private Category genCategory() {
        return Category.of(faker.number().positive(), faker.animal().name());
    }

    private Tag genTag() {
        return Tag.of(faker.number().positive(), faker.text().text());
    }

}
