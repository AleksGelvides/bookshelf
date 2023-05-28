package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer id;
    @NotEmpty
    private String author;
    @NotEmpty
    private String title;
    @Digits(integer = 4, fraction = 0)
    private Integer size;
}
