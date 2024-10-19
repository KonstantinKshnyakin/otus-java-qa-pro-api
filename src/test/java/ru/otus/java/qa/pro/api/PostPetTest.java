package ru.otus.java.qa.pro.api;

import com.google.inject.Inject;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.java.qa.pro.api.annotations.FeaturePet;
import ru.otus.java.qa.pro.api.annotations.PetTest;
import ru.otus.java.qa.pro.api.annotations.Generate;
import ru.otus.java.qa.pro.api.controllers.PetController;
import ru.otus.java.qa.pro.api.dto.pet.Category;
import ru.otus.java.qa.pro.api.dto.pet.Pet;
import ru.otus.java.qa.pro.api.dto.pet.Tag;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.otus.java.qa.pro.api.utlil.AssertsHelper.*;

@FeaturePet
@Story("POST /pet")
@DisplayName("POST /pet")
@PetTest
public class PostPetTest {

    @Inject
    protected PetController petController;
    protected Set<Long> idsForRemoval = new HashSet<>();

    @AfterAll
    public void cleanup() {
        idsForRemoval
                .forEach(petId -> {
                    Response response = petController.deleteById(petId);
                    assertCode(response, 200);
                });
    }

    @Test
    @DisplayName("сохранение только с обязательными параметрами -> 200")
    public void postPetWithRequiredParam(@Generate Pet pet) {
        Response response = petController.postPet(pet);
        assertSuccessResponse(response, pet);
    }

    @Test
    @DisplayName("сохранение со всеми параметрами -> 200")
    public void postPetWithAllParam(@Generate Pet pet, @Generate Category category, @Generate Tag tag) {
        pet
                .setCategory(category)
                .setTags(List.of(tag));

        Response response = petController.postPet(pet);
        assertSuccessResponse(response, pet);
    }

    @Test
    @DisplayName("сохранение без обязательного параметра -> 400(по факту 200 - bug)")
    public void postPetWithoutRequiredParam(@Generate Pet pet) {
        pet.setName(null);

        Response response = petController.postPet(pet);
        assertSuccessResponse(response, pet);
    }

    @Step("Проверка")
    private void assertSuccessResponse(Response response, Pet expBody) {
        Long createdId = assertSuccessCodeAndGetBody(response, Pet.class).getId();
        idsForRemoval.add(createdId);

        Response getResponse = petController.getById(createdId);
        Pet actPet = assertSuccessCodeAndGetBody(getResponse, Pet.class);
        assertPet(expBody, actPet);
    }

}
