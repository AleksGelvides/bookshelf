package org.example.web.utils;

import org.example.web.dto.SearchEntity;
import org.example.web.exception.RegexpException;

public interface SearchBookUtil {
    String REGEXP_AUTHOR = "author: ";
    String REGEXP_TITLE = "title: ";
    String REGEXP_SIZE = "size: ";

    static SearchEntity createSearchEntity(String expression) throws RegexpException {
        if (!expression.contains(REGEXP_AUTHOR) &&
                !expression.contains(REGEXP_TITLE) &&
                !expression.contains(REGEXP_SIZE))
            throw new RegexpException("Regexp not found");
        String[] arr = expression.split(":");
        String expSearch = arr[0];
        String expField = arr[1].replaceFirst(" ","");
        return new SearchEntity(expSearch, expField);
    }
}
