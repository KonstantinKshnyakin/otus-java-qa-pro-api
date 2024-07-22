package ru.otus.java.qa.pro.api.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Category {

    private Integer id;
    private String name;

}