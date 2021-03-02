package fr.univrouen.tirageausort.controllers;

import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.addUser(dto));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable UUID id){
        try{
            UserDTO dto = userService.findUserById(id);
            if (dto!=null) {
                userService.deleteUserById(id);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
            }
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Map<String, Integer>> countUsers(){
        try{
            int nbUsers = userService.getNbUsers();
            System.out.println(nbUsers);
            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("count",nbUsers));
        }
        catch (ExceptionInInitializerError e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
