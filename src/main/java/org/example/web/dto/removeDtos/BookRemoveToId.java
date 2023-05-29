package org.example.web.dto.removeDtos;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookRemoveToId {
    @NotNull
    private Integer id;
}
