package fr.univrouen.tirageausort.services;

import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.model.Users;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    public UserDTO findUserById(UUID userId);

    public List<UserDTO> findAllUsers();

    public int getNbUsers();

    public void addUser(UserDTO user);

    public void deleteUserById(UUID userId);


    List<UserDTO> findUserByName(String name);
}
