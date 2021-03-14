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
public class UserController {
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

    @GetMapping(value = "/allactive")
    public ResponseEntity<List<UserDTO>> getAllActiveUsers(){
        try {
            List<UserDTO> allUsers = userService.findAllActiveUsers();
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
            if (!userList.isEmpty())
                return ResponseEntity.status(HttpStatus.OK).body(userList);
            else
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userList);
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
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(dto));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PutMapping(value = "/save/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO dto){
        try{
            UUID idUser = UUID.fromString(id);
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(dto, idUser));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable UUID id){
        try{
            Integer result = userService.deleteUserById(id);
            switch (result) {
                case 0: return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.findUserById(id));
                case 1: return ResponseEntity.status(HttpStatus.OK).body(null);
                case 2: return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
                default: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Map<String, Integer>> countUsers(){
        try{
            int nbUsers = userService.getNbUsers();
            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("count",nbUsers));
        }
        catch (ExceptionInInitializerError e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
