package org.example.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class BookRemoveDto {

    @NotEmpty
    @NotNull
    private String id;
    private String regexp;
}
