package org.example.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class BookRemoveDto {

    @NotNull
    private Integer id;
    private String regexp;
}
