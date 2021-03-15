package fr.univrouen.tirageausort.services;

import fr.univrouen.tirageausort.converter.EntityConverter;
import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.model.Task;
import fr.univrouen.tirageausort.model.Users;
import fr.univrouen.tirageausort.repositories.UserRepository;
import fr.univrouen.tirageausort.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityConverter entityConverter;
    @Override
    public UserDTO findUserById(UUID userId) {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()){
            Users users = user.get();
            return entityConverter.userEntityToDto(users);
        }
        else {
            return null;
        }
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<Users> allUsers = userRepository.findAll();
        if (!allUsers.isEmpty()) {
            List<UserDTO> allUsersDto = new ArrayList<>();

            for (Users user : allUsers) {
                UserDTO userDto = entityConverter.userEntityToDto(user);
                allUsersDto.add(userDto);
            }
            return allUsersDto;
        }
        else
            return Collections.emptyList();
    }

    @Override
    public List<UserDTO> findAllActiveUsers() {
        List<UserDTO> allUsers = findAllUsers();
        if (!allUsers.isEmpty()) {
            allUsers.removeIf(UserDTO::isArchived);
        }
        return allUsers;
    }

    @Override
    public int getNbUsers() {
        return userRepository.findAll().size();
    }

    @Override
    public UserDTO addUser(UserDTO userDto) {
        try {
            Users users = entityConverter.userDtoToEntity(userDto);
            Users result = userRepository.save(users);
            return entityConverter.userEntityToDto(result);
        }
        catch(ExceptionInInitializerError e) {
            return null;
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDto, UUID idUser) {
        try {
            Optional<Users> userById = userRepository.findById(idUser);
            if(userById.isPresent()) {
                Users users = userById.get();

                if (userDto.getName()!=null)
                    users.setName(userDto.getName());
                Users result = userRepository.save(users);
                return entityConverter.userEntityToDto(result);
            }
            return null;
        }
        catch(ExceptionInInitializerError e) {
            return null;
        }
    }

    /**
     * a function for deleting a user
     * @param userId
     * @return -1 if user don't exist or not free, 0 if user archived, 1 if user is deleted
     */
    @Override
    public Integer deleteUserById(UUID userId) {
        Optional<Users> usersOptional = userRepository.findById(userId);
        if(usersOptional.isPresent()) {
            Users users = usersOptional.get();
            if (!users.getTaskList().isEmpty()) {
                boolean free = true;
                for (Task task : users.getTaskList()) {
                    if (!task.isFinished())
                    {
                        free = false;
                    }
                }
                if (free) {
                    users.setArchived(true);
                    userRepository.save(users);
                    return 0;
                } else
                    return 2;
            }
            // sinon supprimer
            else {
                userRepository.deleteById(userId);
                return 1;
            }
        }
        return -1;
    }

    @Override
    public List<UserDTO> findUserByName(String name) {
        List<Users> userList = userRepository.getByNameContaining(name);
        if(userList!=null){
            List<UserDTO> listUserDto = new ArrayList<>();
            for (Users user:userList) {
                listUserDto.add(entityConverter.userEntityToDto(user));
            }
            return listUserDto;
        }
        return Collections.emptyList();
    }
}
