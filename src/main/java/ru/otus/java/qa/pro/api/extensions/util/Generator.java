package ru.otus.java.qa.pro.api.extensions.util;

import com.google.inject.Inject;
import net.datafaker.Faker;
import ru.otus.java.qa.pro.api.dto.pet.Category;
import ru.otus.java.qa.pro.api.dto.pet.Pet;
import ru.otus.java.qa.pro.api.dto.pet.Tag;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

public class Generator {

    @Inject
    private Faker faker;
    private final HashMap<Class<?>, Supplier<?>> generatorByClass = new HashMap<>();

    {
        generatorByClass.put(Pet.class, this::genPet);
        generatorByClass.put(Category.class, this::genCategory);
        generatorByClass.put(Tag.class, this::genTag);
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

    public boolean contains(Class<?> cl) {
        return generatorByClass.containsKey(cl);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<?> cl) {
        return (T) generatorByClass.get(cl).get();
    }

}
