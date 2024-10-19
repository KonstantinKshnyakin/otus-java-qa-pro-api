package ru.otus.java.qa.pro.api;

import com.google.inject.Inject;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import ru.otus.java.qa.pro.api.annotations.FeaturePet;
import ru.otus.java.qa.pro.api.annotations.PetTest;
import ru.otus.java.qa.pro.api.annotations.Create;
import ru.otus.java.qa.pro.api.controllers.PetController;
import ru.otus.java.qa.pro.api.dto.Error;
import ru.otus.java.qa.pro.api.dto.pet.Pet;

import static ru.otus.java.qa.pro.api.utlil.AssertsHelper.*;

@FeaturePet
@Story("GET /pet/{id}")
@DisplayName("GET /pet/{id}")
@PetTest
public class GetByIdPetTest {

    @Inject
    protected PetController petController;

    @Test
    @DisplayName("поиск существующего -> 200")
    public void getByIdExist(@Create Pet pet) {
        Response response = petController.getById(pet.getId());
        assertSuccessResponse(response, pet);
    }

    @Test
    @DisplayName("поиск не существующего -> 404")
    public void getByIdButNotFound(Faker faker) {
        long invalidId = faker.number().negative();
        Response response = petController.getById(invalidId);

        Error expError = Error.of(1, "error", "Pet not found");
        assertErrorResponse(response, expError, 404);
    }

    @Step("Проверка")
    private void assertSuccessResponse(Response response, Pet expBody) {
        Pet actPet = assertSuccessCodeAndGetBody(response, Pet.class);
        assertPet(expBody, actPet);
    }

    @Step("Проверка")
    private void assertErrorResponse(Response response, Error expError, Integer expStatusCode) {
        Error actError = assertErrorCodeAndGetBody(response, expStatusCode);
        assertError(actError, expError);
    }

}
