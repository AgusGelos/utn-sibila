package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication

public class MainWeb {

    public static void main(String [] args){
        ApplicationContext ctx = SpringApplication.run(MainWeb.class, args);
    }
}

