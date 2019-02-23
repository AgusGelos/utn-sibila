package web;


import conceptmanager.ConceptManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PruebaController {
    @RequestMapping("/hola")
    @ResponseBody
    public String helloHandler () {

        return "<h1>Hello World!</h1>";
    }
}