package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchEntity {
    private final String fieldName;
    private final String fieldValue;
}
