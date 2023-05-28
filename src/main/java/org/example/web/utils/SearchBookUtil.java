package org.example.web.utils;

import org.example.web.dto.SearchEntity;
import org.example.web.exception.RegexpException;
import org.example.web.exception.SearchException;
import org.thymeleaf.util.StringUtils;

public interface SearchBookUtil {
    String REGEXP_AUTHOR = "author";
    String REGEXP_TITLE = "title";
    String REGEXP_SIZE = "size";

     static SearchEntity createSearchEntity(String expression) throws RegexpException {
         if (StringUtils.isEmpty(expression)) {
             throw new RegexpException("regex cannot be null");
         }
         String[] arr = expression.split(": ");
             String field = arr[0];
             String string = arr[1];
        switch (field) {
            case REGEXP_AUTHOR: return new SearchEntity(REGEXP_AUTHOR, string);
            case REGEXP_TITLE: return new SearchEntity(REGEXP_TITLE, string);
            case REGEXP_SIZE: return new SearchEntity(REGEXP_SIZE, string);
            default: throw new RegexpException(arr[0] + " regexp incorrect");
        }
    }
}
