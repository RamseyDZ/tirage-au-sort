package fr.univrouen.tirageausort.controllers;

import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.services.IUserService;
import fr.univrouen.tirageausort.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value="/user")
public class userController {
    @Autowired
    private IUserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        // appeler le service puis retourner les utilisateurs
        try {
            List<UserDTO> allUsers = userService.findAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(allUsers);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping(value="/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id){
        try {
            UUID userId = UUID.fromString(id);
            System.out.println(userId);
            UserDTO user = userService.findUserById(userId);
            if (user!=null)
                return ResponseEntity.status(HttpStatus.OK).body(user);
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (ExceptionInInitializerError e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(value="/find")
    public ResponseEntity<List<UserDTO>> getUserByName(@RequestParam String name){
        try {


            List<UserDTO> userList = userService.findUserByName(name);
            if (userList!=null)
                return ResponseEntity.status(HttpStatus.OK).body(userList);
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (ExceptionInInitializerError e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO dto){
        try{
            userService.addUser(dto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

    }

}
