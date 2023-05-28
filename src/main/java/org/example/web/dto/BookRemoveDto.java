package org.example.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class BookRemoveDto {

    @NotNull
    private Integer id;
    @NotEmpty
    private String regexp;
}
