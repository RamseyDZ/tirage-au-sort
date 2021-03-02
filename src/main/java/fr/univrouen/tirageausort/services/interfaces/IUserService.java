package fr.univrouen.tirageausort.services.interfaces;

import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.model.Users;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    UserDTO findUserById(UUID userId);

    List<UserDTO> findAllUsers();

    int getNbUsers();

    UserDTO addUser(UserDTO user);

    void deleteUserById(UUID userId);


    List<UserDTO> findUserByName(String name);
}
