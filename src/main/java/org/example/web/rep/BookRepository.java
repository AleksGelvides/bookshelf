package org.example.web.rep;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.SearchEntity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final String FIELD_ID = "id";
    private final String FIELD_TITLE = "title";
    private final String FIELD_AUTHOR = "author";
    private final String FIELD_SIZE = "size";
    private final Logger logger = Logger.getLogger(BookRepository.class);
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> getAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM BOOKS", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt(FIELD_ID));
            book.setAuthor(rs.getString(FIELD_AUTHOR));
            book.setTitle(rs.getString(FIELD_TITLE));
            book.setSize(rs.getInt(FIELD_SIZE));
            return book;
        });
        return books;
    }

    public void save(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(FIELD_AUTHOR, book.getAuthor());
        parameterSource.addValue(FIELD_TITLE, book.getTitle());
        parameterSource.addValue(FIELD_SIZE, book.getSize());
        jdbcTemplate.update("INSERT INTO BOOKS(AUTHOR, TITLE, SIZE) VALUES(:author, :title, :size)",
                parameterSource);
        logger.info("store new book with id: " + book.getId());
    }

    @Override
    public boolean removeById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(FIELD_ID, id);
        int result = jdbcTemplate.update("DELETE FROM BOOKS WHERE ID = :id", parameterSource);
        logger.info("remove book completed");
        return result > 0;
    }

    @Override
    public boolean removeByField(SearchEntity entity) {
        String field = entity.getFieldName();
        String sql = String.format("DELETE FROM BOOKS WHERE %s = :%s", field, field);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(entity.getFieldName(), entity.getFieldValue());
        int result = jdbcTemplate.update(sql, parameterSource);
        return result > 0;
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
