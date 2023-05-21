package org.example.web.rep;

import org.example.web.dto.Book;
import org.example.web.dto.SearchEntity;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> getAll();
    void save(T t);

    boolean removeById(String id);

    void removeByField(SearchEntity search);
}
