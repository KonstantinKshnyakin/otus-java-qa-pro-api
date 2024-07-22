package ru.otus.java.qa.pro.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.java.qa.pro.api.dto.pet.Category;
import ru.otus.java.qa.pro.api.dto.pet.Pet;
import ru.otus.java.qa.pro.api.dto.pet.Tag;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostPetTest extends PetBaseTest {

    @Test
    @DisplayName("сохранение только с обязательными параметрами -> 200")
    public void postPetWithRequiredParam(Pet pet) {
        Response response = petController.postPet(pet);
        assertSuccessResponse(response, pet);
    }

    @Test
    @DisplayName("сохранение со всеми параметрами -> 200")
    public void postPetWithAllParam(Pet pet, Category category, Tag tag) {
        pet
                .setCategory(category)
                .setTags(List.of(tag));

        Response response = petController.postPet(pet);
        assertSuccessResponse(response, pet);
    }

    @Test
    @DisplayName("сохранение без обязательного параметра -> 400(по факту 200 - bug)")
    public void postPetWithoutRequiredParam(Pet pet) {
        pet
                .setName(null);

        Response response = petController.postPet(pet);
        assertSuccessResponse(response, pet);
    }

    private void assertSuccessResponse(Response response, Pet expBody) {
        assertThat(response.statusCode()).as("status code").isEqualTo(200);
        Long createdId = response.as(Pet.class).getId();
        idsForRemoval.add(createdId);

        Response getByIdResponse = petController.getById(createdId);
        assertThat(getByIdResponse.statusCode()).as("status code").isEqualTo(200);
        Pet actBody = getByIdResponse.as(Pet.class);

        assertPet(expBody, actBody);
    }

}
