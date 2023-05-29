package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.removeDtos.BookRemoveToId;
import org.example.web.dto.removeDtos.BookRemoveToRegexp;
import org.example.web.exception.RegexpException;
import org.example.web.exception.ValidationException;
import org.example.web.services.BookService;
import org.example.web.services.IoService;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;


@Controller
@RequestMapping("books")
@Scope("singleton")
public class BookShelfController {
    private Logger logger = Logger.getLogger(LoginController.class);
    private final BookService bookService;
    private final IoService ioService;

    @Autowired
    private BookShelfController(BookService bookService,
                                IoService ioService){
        this.bookService = bookService;
        this.ioService = ioService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookRemoveToId", new BookRemoveToId());
        model.addAttribute("bookRemoveToRegexp", new BookRemoveToRegexp());
        model.addAttribute("bookList", bookService.getAll());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) throws ValidationException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookRemoveToId", new BookRemoveToId());
            model.addAttribute("bookRemoveToRegexp", new BookRemoveToRegexp());
            model.addAttribute("bookList", bookService.getAll());
            return "book_shelf";
        }
        bookService.saveBook(book);
        logger.info("Current repository size: " + bookService.getAll().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
            ioService.saveFile(file);
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookRemoveToId bookRemoveToId, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookRemoveToId", bookRemoveToId);
            model.addAttribute("bookRemoveToRegexp", new BookRemoveToRegexp());
            model.addAttribute("bookList", bookService.getAll());
            return "book_shelf";
        }
        bookService.removeBook(bookRemoveToId.getId());
        return "redirect:/books/shelf";
    }

    @PostMapping("removeByRegex")
    public String removeBuRegexp(@Valid BookRemoveToRegexp bookRemoveToRegexp,
                                 BindingResult bindingResult,
                                 Model model) {
        try {
            if (StringUtils.isNullOrEmpty(bookRemoveToRegexp.getRegexp()))
                throw new RegexpException("regexp cannot be null");
            bookService.removeByRegexp(bookRemoveToRegexp.getRegexp());
        } catch (RegexpException e) {
            bindingResult.addError(new ObjectError("regexp", e.getMessage()));
            model.addAttribute("bookRemoveToId", new BookRemoveToId());
            model.addAttribute("bookRemoveToRegexp", bookRemoveToRegexp);
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAll());
            return "book_shelf";
        }
        return "redirect:/books/shelf";
    }

    @ExceptionHandler(IOException.class)
    public String handleAccessDeniedException(Model model, IOException e) {
        model.addAttribute("errorMessage", "File not found or cannot be processed");
        return "error/common_error";
    }
}
