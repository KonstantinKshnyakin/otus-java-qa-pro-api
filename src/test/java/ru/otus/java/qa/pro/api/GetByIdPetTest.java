package ru.otus.java.qa.pro.api;

import com.google.inject.Inject;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import ru.otus.java.qa.pro.api.annotations.PetTest;
import ru.otus.java.qa.pro.api.annotations.Create;
import ru.otus.java.qa.pro.api.controllers.PetController;
import ru.otus.java.qa.pro.api.dto.Error;
import ru.otus.java.qa.pro.api.dto.pet.Pet;
import ru.otus.java.qa.pro.api.utlil.AssertsHelper;

@PetTest
public class GetByIdPetTest {

    @Inject
    protected PetController petController;

    @Test
    @DisplayName("поиск существуюещего -> 200")
    public void getByIdExist(@Create Pet pet) {
        Pet actBody = petController.getByIdSuccess(pet.getId());
        AssertsHelper.assertPet(pet, actBody);
    }

    @Test
    @DisplayName("поиск не существуюещего -> 404")
    public void getByIdButNotFound(Faker faker) {
        long invalidId = faker.number().negative();
        Error actError = petController.getByIdError(invalidId, 404);

        Error expError = Error.of(1, "error", "Pet not found");
        AssertsHelper.assertError(actError, expError);
    }

}
