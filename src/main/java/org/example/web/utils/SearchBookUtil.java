package org.example.web.utils;

import org.example.web.dto.SearchEntity;
import org.example.web.exception.SearchException;

public interface SearchBookUtil {
    static final String REGEXP_AUTHOR = "author";
    static final String REGEXP_TITLE = "title";
    static final String REGEXP_SIZE = "size";

     static SearchEntity createSearchEntity(String expression) throws SearchException {
         String[] arr = expression.split(": ");
             String field = arr[0];
             String string = arr[1];
        switch (field) {
            case REGEXP_AUTHOR: return new SearchEntity(REGEXP_AUTHOR, string);
            case REGEXP_TITLE: return new SearchEntity(REGEXP_TITLE, string);
            case REGEXP_SIZE: return new SearchEntity(REGEXP_SIZE, string);
            default: throw new SearchException("Поле поиска не указано");
        }
    }
}
