package fr.univrouen.tirageausort.controllers;

import fr.univrouen.tirageausort.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Controller
public class tirageController {

    @RequestMapping(value="/getUser")
    public Users getRandomUser(List<Users> listUsers){
        var String = "user name";
        // mélanger la liste des utilsateur
        Collections.shuffle(listUsers);
        Random random = new Random();
        int choix = random.nextInt(listUsers.size());
        return listUsers.get(choix);
    }
}
