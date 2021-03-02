package fr.univrouen.tirageausort.services;

import fr.univrouen.tirageausort.converter.EntityConverter;
import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.model.Users;
import fr.univrouen.tirageausort.repositories.UserRepository;
import fr.univrouen.tirageausort.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityConverter entityConverter;
    @Override
    public UserDTO findUserById(UUID userId) {
        Optional<Users> user = userRepository.findById(userId);
        System.out.println("UUID utilisateur : "+userId);
        if (user.isPresent()){
            System.out.println("Utilisateur trouvé !!!!! ");
            Users users = user.get();
            UserDTO userDto = entityConverter.UserEntityToDto(users);
            return userDto;
        }
        else {
            System.out.println("Rieeeeen !!!!! ");
            return null;
        }
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<Users> allUsers = userRepository.findAll();
        if (!allUsers.isEmpty()) {
            List<UserDTO> allUsersDto = new ArrayList<>();

            for (Users user : allUsers) {
                UserDTO userDto = entityConverter.UserEntityToDto(user);
                allUsersDto.add(userDto);
                System.out.println(user.getId());

            }
            return allUsersDto;
        }
        else
            return null;
    }

    @Override
    public int getNbUsers() {
        return userRepository.findAll().size();
    }

    @Override
    public UserDTO addUser(UserDTO userDto) {
        try {
            Users users = entityConverter.UserDtoToEntity(userDto);
            Users result = userRepository.save(users);
            return entityConverter.UserEntityToDto(result);
        }
        catch(ExceptionInInitializerError e) {
            return null;
        }
    }

    @Override
    public void deleteUserById(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDTO> findUserByName(String name) {
        List<Users> userList = userRepository.getByName(name);
        if(userList!=null){
            System.out.println("Utilisateur trouvé !!!!! ");
            List<UserDTO> listUserDto = new ArrayList<>();
            for (Users user:userList) {
                listUserDto.add(entityConverter.UserEntityToDto(user));
            }
            return listUserDto;
        }
        else {
            System.out.println("Rieeeeen !!!!! ");}
            return null;
        }
}
