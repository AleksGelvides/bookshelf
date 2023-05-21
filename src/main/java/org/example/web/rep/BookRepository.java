package org.example.web.rep;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.SearchEntity;
import org.example.web.services.IdProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final String FIELD_TITLE = "title";
    private final String FIELD_AUTHOR = "author";
    private final String FIELD_SIZE = "size";
    private final Logger logger = Logger.getLogger(BookRepository.class);

    private ApplicationContext context;
    private List<Book> repo = new ArrayList<Book>();

    public List<Book> getAll() {
        return new ArrayList<Book>(repo);
    }

    public void save(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        logger.info("store new book with id: " + book.getId());
        repo.add(book);
    }

    @Override
    public boolean removeById(String id) {
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


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void defaultInit(){
        logger.info("default INIT in book repo bean");
    }

    public void defaultDestroy(){
        logger.info("default DESTROY in book repo bean");
    }
}
