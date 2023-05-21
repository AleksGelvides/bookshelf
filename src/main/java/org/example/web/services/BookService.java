package org.example.web.services;

import org.example.web.dto.Book;
import org.example.web.dto.SearchEntity;
import org.example.web.exception.SearchException;
import org.example.web.exception.ValidationException;
import org.example.web.rep.BookRepository;
import org.example.web.rep.ProjectRepository;
import org.example.web.utils.SearchBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class);
    private final ProjectRepository<Book> repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAll() {
        return repository.getAll();
    }

    public void saveBook(Book book) throws ValidationException {
        if (StringUtils.isEmpty(book.getAuthor()) &&
                StringUtils.isEmpty(book.getTitle()) &&
                Objects.isNull(book.getSize())) {
            throw new ValidationException("При сохранении книги необходимо заполнить хотя бы одно из полей");
        }
        repository.save(book);
    }

    public boolean removeBook(Integer id) {
        return repository.removeById(id);
    }

    public void removeByRegexp(String regexp) throws SearchException {
        SearchEntity search = SearchBookUtil.createSearchEntity(regexp);
        repository.removeByField(search);
    }


}
