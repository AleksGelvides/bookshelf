package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookRemoveDto;
import org.example.web.exception.ValidationException;
import org.example.web.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;


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
        model.addAttribute("bookRemoveDto", new BookRemoveDto());
        model.addAttribute("bookList", bookService.getAll());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) throws ValidationException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookRemoveDto", new BookRemoveDto());
            model.addAttribute("bookList", bookService.getAll());
            return "book_shelf";
        }
        bookService.saveBook(book);
        logger.info("Current repository size: " + bookService.getAll().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(Files.newOutputStream(serverFile.toPath()));
        stream.write(bytes);
        stream.close();

        logger.info("new file saved at: " + serverFile.getAbsolutePath());

        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookRemoveDto bookRemoveDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAll());
            return "book_shelf";
        }
        bookService.removeBook(bookRemoveDto.getId());
        return "redirect:/books/shelf";
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
