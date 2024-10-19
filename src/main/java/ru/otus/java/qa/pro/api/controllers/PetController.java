package ru.otus.java.qa.pro.api.controllers;

import static io.restassured.RestAssured.given;

import com.google.inject.Singleton;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.otus.java.qa.pro.api.dto.pet.Pet;

@Singleton
public class PetController extends BaseClient {

    private static final String BASE_PATH = "/pet";
    private static final String BY_ID = BASE_PATH + "/%d";

    /**
     * Add a new pet to the store
     * host/v2/pet
     */
    @Step("Запрос POST /pet")
    public Response postPet(Pet requestBody) {
        return given()
                .spec(requestSpec)
                .body(requestBody)
                .post(BASE_PATH)
                .thenReturn();
    }

    /**
     * Find pet by ID
     * host/v2/pet/{id}
     */
    @Step("Запрос GET /pet/{id}")
    public Response getById(Long id) {
        return given()
                .spec(requestSpec)
                .get(BY_ID.formatted(id))
                .thenReturn();
    }

    /**
     * Deletes a pet
     * host/v2/pet/{petId}
     */
    @Step("Запрос DELETE /pet/{id}")
    public Response deleteById(Long id) {
        return given()
                .spec(requestSpec)
                .delete(BY_ID.formatted(id))
                .thenReturn();
    }

}
