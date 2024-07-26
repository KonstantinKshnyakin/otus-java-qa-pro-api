package ru.otus.java.qa.pro.api.controllers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Singleton;
import io.restassured.response.Response;
import ru.otus.java.qa.pro.api.dto.Error;
import ru.otus.java.qa.pro.api.dto.pet.Pet;

@Singleton
public class PetController extends BaseClient {

    private static final String BASE_PATH = "/pet";
    private static final String BY_ID = BASE_PATH + "/%d";


    /**
     * Add a new pet to the store
     * host/v2/pet
     */
    public Response postPet(Pet requestBody) {
        return given()
                .spec(requestSpec)
                .body(requestBody)
                .post(BASE_PATH)
                .thenReturn();
    }

    public Pet postPetSuccess(Pet requestBody) {
        Response response = postPet(requestBody);
        return assertSuccessCodeAndGetBody(response);
    }

    public Error postPetError(Pet requestBody, int expStatusCode) {
        Response response = postPet(requestBody);
        return assertCodeAndGetBody(response, expStatusCode, Error.class);
    }

    /**
     * Find pet by ID
     * host/v2/pet/{petId}
     */
    public Response getById(Long id) {
        return given()
                .spec(requestSpec)
                .get(BY_ID.formatted(id))
                .thenReturn();
    }

    public Pet getByIdSuccess(Long id) {
        Response response = getById(id);
        return assertSuccessCodeAndGetBody(response);
    }

    public Error getByIdError(Long id, int expStatusCode) {
        Response response = getById(id);
        return assertCodeAndGetBody(response, expStatusCode, Error.class);
    }

    /**
     * Deletes a pet
     * host/v2/pet/{petId}
     */
    public Response deleteById(Long id) {
        return given()
                .spec(requestSpec)
                .delete(BY_ID.formatted(id))
                .thenReturn();
    }

    public void deleteByIdSuccess(Long id) {
        Response response = deleteById(id);
        assertCode(response, 200);
    }

    public Error deleteByIdError(Long id, int expStatusCode) {
        Response response = deleteById(id);
        return assertCodeAndGetBody(response, expStatusCode, Error.class);
    }

    private <T> T assertCodeAndGetBody(Response response, Integer expStatusCode, Class<T> expClass) {
        assertCode(response, expStatusCode);
        return response.as(expClass);
    }

    private void assertCode(Response response, Integer expStatusCode) {
        assertThat(response.statusCode()).as("status code").isEqualTo(expStatusCode);
    }

    private Pet assertSuccessCodeAndGetBody(Response response) {
        return assertCodeAndGetBody(response, 200, Pet.class);
    }

}
