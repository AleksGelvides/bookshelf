package org.example.web.rep;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.SearchEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final String FIELD_TITLE = "title";
    private final String FIELD_AUTHOR = "author";
    private final String FIELD_SIZE = "size";
    private final Logger logger = Logger.getLogger(BookRepository.class);
    private List<Book> repo = new ArrayList<Book>();

    private BookRepository() {
        this.repo.add(new Book(1, "Author1", "Author1_Book", 1000));
        this.repo.add(new Book(2, "Author2", "Author2_Book", 1000));
        this.repo.add(new Book(3, "Author3", "Author3_Book", 1000));
        this.repo.add(new Book(4, "Author4", "Author4_Book", 1000));
        this.repo.add(new Book(5, "Author5", "Author5_Book", 1000));
        this.repo.add(new Book(6, "Author1", "Author1_Book2", 1000));
        this.repo.add(new Book(7, "Author1", "Author1_Book3", 1000));
        this.repo.add(new Book(8, "Author1", "Author1_Book4", 1000));
        this.repo.add(new Book(9, "Author4", "Author4_Book2", 1000));
        this.repo.add(new Book(10, "Author4", "Author4_Book3", 1000));
        this.repo.add(new Book(11, "Author3", "Author3_Book2", 1000));
        this.repo.add(new Book(12, "Author4", "Author4_Book4", 1000));
        this.repo.add(new Book(13, "Author6", "Author6_Book1", 1000));
    }

    public List<Book> getAll() {
        return new ArrayList<Book>(repo);
    }

    public void save(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book with id: " + book.getId());
        repo.add(book);
    }

    @Override
    public boolean removeById(Integer id) {
        ListIterator<Book> iter = this.repo.listIterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            if (book.getId().equals(id)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeByField(SearchEntity entity) {
        List<Book> removeList = null;
        switch (entity.getFieldName()) {
            case FIELD_TITLE:
                removeList = this.repo.stream()
                        .filter(book -> book.getTitle().equals(entity.getFieldValue()))
                        .collect(Collectors.toList());
                break;
            case FIELD_AUTHOR:
                removeList = this.repo.stream()
                        .filter(book -> book.getAuthor().equals(entity.getFieldValue()))
                        .collect(Collectors.toList());
                break;
            case FIELD_SIZE:
                removeList = this.repo.stream()
                        .filter(book -> book.getSize().equals(new Integer(entity.getFieldValue())))
                        .collect(Collectors.toList());
                break;
        }

        if (CollectionUtils.isEmpty(removeList))
            return;
        this.repo.removeAll(removeList);
    }


}
