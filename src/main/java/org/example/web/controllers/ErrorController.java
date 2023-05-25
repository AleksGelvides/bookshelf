package org.example.web.controllers;

import org.example.web.exception.BookShelfLoginException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class ErrorController {

    @GetMapping("/404")
    public String notFoundError() {
        return "error/404";
    }

    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, Exception exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error/404";
    }
}
