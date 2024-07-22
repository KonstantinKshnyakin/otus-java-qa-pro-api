package ru.otus.java.qa.pro.api.controllers;

import static io.restassured.RestAssured.given;

import com.google.inject.Singleton;
import io.restassured.response.Response;
import ru.otus.java.qa.pro.api.dto.pet.Pet;

@Singleton
public class PetController extends BaseClient{

    public static final String BASE_PATH = "/pet";
    public static final String BY_ID = BASE_PATH + "/%d";


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

    /**
     * Deletes a pet
     * host/v2/pet/{petId}
     */
    public Response delete(Long id) {
        return given()
                .spec(requestSpec)
                .delete(BY_ID.formatted(id))
                .thenReturn();
    }

}
