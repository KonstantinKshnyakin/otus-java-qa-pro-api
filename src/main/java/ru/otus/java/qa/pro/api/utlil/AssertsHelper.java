package ru.otus.java.qa.pro.api.utlil;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.experimental.UtilityClass;
import ru.otus.java.qa.pro.api.dto.Error;
import ru.otus.java.qa.pro.api.dto.pet.Pet;

@UtilityClass
public class AssertsHelper {

    public static void assertPet(Pet expPet, Pet actPet) {
        assertThat(actPet)
                .usingRecursiveComparison()
                .withEqualsForFields((act, exp) -> act != null && (Long) act > 0, "id")
                .isEqualTo(expPet);
    }

    public static void assertError(Error actError, Error expError) {
        assertThat(actError)
                .usingRecursiveComparison()
                .isEqualTo(expError);
    }

}
