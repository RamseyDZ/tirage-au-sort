package fr.univrouen.tirageausort.services.interfaces;

import fr.univrouen.tirageausort.dtos.UserDTO;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    UserDTO findUserById(UUID userId);

    List<UserDTO> findAllUsers();

    List<UserDTO> findAllActiveUsers();

    int getNbUsers();

    UserDTO addUser(UserDTO user);

    UserDTO updateUser(UserDTO userDto, UUID idUser);

    Integer deleteUserById(UUID userId);


    List<UserDTO> findUserByName(String name);
}
