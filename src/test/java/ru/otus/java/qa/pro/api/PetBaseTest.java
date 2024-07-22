package ru.otus.java.qa.pro.api;

import com.google.inject.Inject;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.java.qa.pro.api.controllers.PetController;
import ru.otus.java.qa.pro.api.dto.pet.Pet;
import ru.otus.java.qa.pro.api.extensions.GuiceExtension;
import ru.otus.java.qa.pro.api.extensions.ParameterResolvers;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({ParameterResolvers.class, GuiceExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetBaseTest {

    @Inject
    protected PetController petController;
    protected static Set<Long> idsForRemoval = new HashSet<>();

    @AfterAll
    public void cleanup() {
        idsForRemoval
                .forEach(petId -> {
                    Response response = petController.delete(petId);
                    assertThat(response.statusCode()).as("status code").isEqualTo(200);
                });
    }

    protected void assertPet(Pet expBody, Pet actBody) {
        assertThat(actBody)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expBody);
    }

}
