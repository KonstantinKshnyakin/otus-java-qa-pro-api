package ru.otus.java.qa.pro.api.extensions.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.otus.java.qa.pro.api.controllers.PetController;
import ru.otus.java.qa.pro.api.dto.pet.Pet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class Creator {

    @Inject
    private Generator generator;
    @Inject
    private PetController petController;
    private Set<Long> idsForRemoval = new HashSet<>();

    private final HashMap<Class<?>, Supplier<?>> creatorByClass = new HashMap<>();

    {
        creatorByClass.put(Pet.class, this::createPet);
    }

    @Step("Создание Pet")
    private Pet createPet() {
        Pet pet = generator.get(Pet.class);
        Response response = petController.postPet(pet);
        assertThat(response.statusCode()).as("status code").isEqualTo(200);
        Pet createdPet = response.as(Pet.class);
        idsForRemoval.add(createdPet.getId());
        return createdPet;
    }

    public boolean contains(Class<?> cl) {
        return creatorByClass.containsKey(cl);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<?> cl) {
        return (T) creatorByClass.get(cl).get();
    }

    public void deleteAll() {
        idsForRemoval
                .forEach(petId -> {
                    Response response = petController.deleteById(petId);
                    assertThat(response.statusCode()).as("status code").isEqualTo(200);
                });
    }

}
