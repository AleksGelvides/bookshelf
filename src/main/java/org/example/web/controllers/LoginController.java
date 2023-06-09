package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.example.web.exception.BookShelfLoginException;
import org.example.web.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final Logger logger = Logger.getLogger(LoginController.class);
    private final LoginService loginService;

    @Autowired
    private LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public ModelAndView login(Model model) {
        logger.info("GET /login -> login_page.html");
        model.addAttribute("loginForm", new LoginForm());
        return new ModelAndView("login_page");
    }

    @PostMapping("/auth")
    public String authenticate(LoginForm loginForm) throws BookShelfLoginException {
        if (loginService.authenticate(loginForm)) {
            logger.info("Auth: OK");
            return "redirect:/books/shelf";
        }else {
            logger.error("Auth: FAIL");
            throw new BookShelfLoginException("Invalid username or password");
        }
    }
    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, Exception exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error/404";
    }
}
