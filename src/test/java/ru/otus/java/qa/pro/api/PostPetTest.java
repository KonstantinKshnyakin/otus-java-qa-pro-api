package ru.otus.java.qa.pro.api;

import com.google.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.java.qa.pro.api.annotations.PetTest;
import ru.otus.java.qa.pro.api.annotations.Generate;
import ru.otus.java.qa.pro.api.controllers.PetController;
import ru.otus.java.qa.pro.api.dto.pet.Category;
import ru.otus.java.qa.pro.api.dto.pet.Pet;
import ru.otus.java.qa.pro.api.dto.pet.Tag;
import ru.otus.java.qa.pro.api.utlil.AssertsHelper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@PetTest
public class PostPetTest {

    @Inject
    protected PetController petController;
    protected Set<Long> idsForRemoval = new HashSet<>();

    @AfterAll
    public void cleanup() {
        idsForRemoval
                .forEach(petId -> {
                    petController.deleteByIdSuccess(petId);
                });
    }

    @Test
    @DisplayName("сохранение только с обязательными параметрами -> 200")
    public void postPetWithRequiredParam(@Generate Pet pet) {
        Pet createdPet = petController.postPetSuccess(pet);
        assertSuccessResponse(createdPet, pet);
    }

    @Test
    @DisplayName("сохранение со всеми параметрами -> 200")
    public void postPetWithAllParam(@Generate Pet pet, @Generate Category category, @Generate Tag tag) {
        pet
                .setCategory(category)
                .setTags(List.of(tag));

        Pet createdPet = petController.postPetSuccess(pet);
        assertSuccessResponse(createdPet, pet);
    }

    @Test
    @DisplayName("сохранение без обязательного параметра -> 400(по факту 200 - bug)")
    public void postPetWithoutRequiredParam(@Generate Pet pet) {
        pet.setName(null);

        Pet createdPet = petController.postPetSuccess(pet);
        assertSuccessResponse(createdPet, pet);
    }

    private void assertSuccessResponse(Pet createdPet, Pet expBody) {
        Long createdId = createdPet.getId();
        idsForRemoval.add(createdId);

        Pet actBody = petController.getByIdSuccess(createdId);

        AssertsHelper.assertPet(expBody, actBody);
    }

}
