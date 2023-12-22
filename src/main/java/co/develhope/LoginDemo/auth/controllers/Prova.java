package co.develhope.LoginDemo.auth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Prova {

    @GetMapping("/prova")
    public String prova() {
        return "Benvenuto";
    }

}
