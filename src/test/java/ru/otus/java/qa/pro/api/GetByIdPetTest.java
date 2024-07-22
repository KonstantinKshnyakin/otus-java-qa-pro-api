package ru.otus.java.qa.pro.api;

import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import ru.otus.java.qa.pro.api.dto.Error;
import ru.otus.java.qa.pro.api.dto.pet.Pet;

import static org.assertj.core.api.Assertions.assertThat;

public class GetByIdPetTest extends PetBaseTest {

    private Pet createdPet;

    @BeforeEach
    public void createPet(Pet pet) {
        Response response = petController.postPet(pet);
        assertThat(response.statusCode()).as("status code").isEqualTo(200);
        createdPet = response.as(Pet.class);
        idsForRemoval.add(createdPet.getId());
    }

    @Test
    @DisplayName("поиск существуюещего -> 200")
    void getByIdExist() {
        Response response = petController.getById(createdPet.getId());
        assertSuccessResponse(response);
    }

    @Test
    @DisplayName("поиск существуюещего -> 404")
    void getByIdButNotFound(Faker faker) {
        Response response = petController.getById((long) faker.number().negative());

        Error expError = Error.of(1, "error", "Pet not found");
        assertError(response, expError, 404);
    }

    private void assertError(Response response, Error expError, Integer statusCode) {
        assertThat(response.statusCode()).as("status code").isEqualTo(statusCode);
        Error actError = response.as(Error.class);
        assertThat(actError)
                .usingRecursiveComparison()
                .isEqualTo(expError);
    }

    private void assertSuccessResponse(Response response) {
        assertThat(response.statusCode()).as("status code").isEqualTo(200);
        Pet act = response.as(Pet.class);
        assertPet(createdPet, act);
    }

}
