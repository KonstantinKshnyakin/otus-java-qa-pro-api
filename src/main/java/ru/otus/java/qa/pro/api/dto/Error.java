package ru.otus.java.qa.pro.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Error {

    private Integer code;
    private String type;
    private String message;

}