package ru.otus.java.qa.pro.api.utlil;

import static org.assertj.core.api.Assertions.assertThat;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.experimental.UtilityClass;
import ru.otus.java.qa.pro.api.dto.Error;
import ru.otus.java.qa.pro.api.dto.pet.Pet;

@UtilityClass
public class AssertsHelper {

    @Step("Проверка тела ответа")
    public static void assertPet(Pet expPet, Pet actPet) {
        assertThat(actPet)
                .usingRecursiveComparison()
                .withEqualsForFields((act, exp) -> act != null && (Long) act > 0, "id")
                .isEqualTo(expPet);
    }

    @Step("Проверка тела ответа")
    public static void assertError(Error actError, Error expError) {
        assertThat(actError)
                .usingRecursiveComparison()
                .isEqualTo(expError);
    }

    @Step("Проверка кода ответа '{expStatusCode}'")
    public static void assertCode(Response response, Integer expStatusCode) {
        assertThat(response.statusCode()).as("status code").isEqualTo(expStatusCode);
    }

    public static <T> T assertCodeAndGetBody(Response response, Integer expStatusCode, Class<T> expClass) {
        assertCode(response, expStatusCode);
        return response.as(expClass);
    }

    public static <T> T assertSuccessCodeAndGetBody(Response response, Class<T> expClass) {
        return assertCodeAndGetBody(response, 200, expClass);
    }

    public static Error assertErrorCodeAndGetBody(Response response, int expStatusCode) {
        return assertCodeAndGetBody(response, expStatusCode, Error.class);
    }

}
