package org.example.web.dto.removeDtos;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BookRemoveToRegexp {
    @NotEmpty
    private String regexp;
}
