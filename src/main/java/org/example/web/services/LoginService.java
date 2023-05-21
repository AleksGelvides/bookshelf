package org.example.web.services;

import org.example.web.controllers.LoginController;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginController.class);
    public boolean authenticate(LoginForm loginForm) {
        logger.info("auth with user-form: " + loginForm);
        return loginForm.getLogin().equals("root") && loginForm.getPassword().equals("111");
    }
}
