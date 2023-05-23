package org.example.web;

import org.example.web.config.WebContextConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

    private final Logger logger = Logger.getLogger(WebAppInitializer.class.getName());
    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) {
        logger.info("loading app-config");
        XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
        applicationContext.setConfigLocation("classpath:app-config.xml");
        servletContext.addListener(new ContextLoaderListener(applicationContext));

        logger.info("loading web-config");
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(WebContextConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        logger.info("dispatcher ready...");

    }
}
