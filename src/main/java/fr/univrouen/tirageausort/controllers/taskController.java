package fr.univrouen.tirageausort.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/task")
public class taskController {

    @GetMapping(value = "/hello")
    public String getHello(){
        return "Hello task";
    }
}
