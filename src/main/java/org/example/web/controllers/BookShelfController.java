package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.exception.ValidationException;
import org.example.web.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("books")
@Scope("singleton")
public class BookShelfController {
    private Logger logger = Logger.getLogger(LoginController.class);
    private final BookService bookService;

    @Autowired
    private BookShelfController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAll());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book, Model model) {
        try {
            bookService.saveBook(book);
        } catch (ValidationException e) {
            return books(model);
        }
        logger.info("Current repository size: " + bookService.getAll().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam("bookId") String bookId, Model model) {
        boolean removed = bookService.removeBook(bookId);
        if (removed) {
            return "redirect:/books/shelf";
        } else {
            return books(model);
        }
    }

    @PostMapping("removeByRegex")
    public String removeBuRegexp(@RequestParam("queryRegex") String regexp, Model model) {
        try {
            bookService.removeByRegexp(regexp);
        } catch (Exception e) {
            return books(model);
        }
        return "redirect:/books/shelf";
    }
}
