package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;

import configmanager.ConfigManager;

@SpringBootApplication
public class MainWeb {
    public static void main(String [] args){
        ConfigManager config = new ConfigManager(args);
        if (config.validConfig()) {
            // Colocar los valores configurados en el objeto Config compartido
            web.Config.HOST_ORIENTDB = config.getODBHost();
            web.Config.HOST_SPACY = config.getSpacyHost();
            // Arrancar la aplicación servidor
            ApplicationContext ctx = SpringApplication.run(MainWeb.class, args);
        } else {
            System.err.println ("La configuración del servidor es incorrecta.\nEl servidor no se iniciará.");
            System.exit(-1); 
        }
    }
}

