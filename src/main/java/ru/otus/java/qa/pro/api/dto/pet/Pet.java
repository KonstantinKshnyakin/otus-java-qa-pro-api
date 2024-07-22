package ru.otus.java.qa.pro.api.dto.pet;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

@Data
@Accessors(chain = true)
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Pet {

    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    @UtilityClass
    public static class PetStatus {

        public static final String AVAILABLE = "available";
        public static final String PENDING = "pending";
        public static final String SOLD = "sold";

    }

}