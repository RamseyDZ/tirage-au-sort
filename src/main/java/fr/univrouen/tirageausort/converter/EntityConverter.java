package fr.univrouen.tirageausort.converter;

import fr.univrouen.tirageausort.dtos.TaskDTO;
import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.model.Task;
import fr.univrouen.tirageausort.model.Users;
import fr.univrouen.tirageausort.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class EntityConverter {
    @Autowired
    private UserService userService;

    public UserDTO userEntityToDto(Users users){
        UserDTO dto = new UserDTO();
        dto.setIdUser(users.getId());
        dto.setName(users.getName());
        dto.setFree(users.isFree());
        dto.setArchived(users.getArchived());
        List<TaskDTO> tasksDto = new ArrayList<>();
        List<Task> taskList = users.getTaskList();
        if(taskList!=null) {
            for (Task task : taskList) {
                tasksDto.add(taskEntityToDto(task));
            }
        }
        dto.setTasks(tasksDto);

        return dto;
    }

    public TaskDTO taskEntityToDto(Task task){
        TaskDTO dto = new TaskDTO();
        dto.setIdTask(task.getId());
        dto.setDescription(task.getDescription());
        dto.setDuration(task.getDuration());
        dto.setFinished(task.isFinished());
        if(task.getUsers()!=null) {
            dto.setUserId(task.getUsers().getId());
        }
        return dto;
    }

    public Users userDtoToEntity(UserDTO dto){
        Users users = new Users();
        users.setId(dto.getIdUser());
        users.setName(dto.getName());
        users.setFree(dto.isFree());
        List<TaskDTO> tasksDto = dto.getTasks();
        if (tasksDto!=null) {
            List<Task> tasks = new ArrayList<>();
            for (TaskDTO tDto : tasksDto) {
                tasks.add(taskDtoToEntity(tDto));
            }
            users.setTaskList(tasks);
        }
        return users;
    }

    public Task taskDtoToEntity(TaskDTO dto){
        Task task = new Task();
        task.setId(dto.getIdTask());
        task.setDescription(dto.getDescription());
        task.setDuration(dto.getDuration());
        task.setFinished(dto.isFinished());
        if(dto.getUserId()!=null)
        {
            UserDTO userDto = userService.findUserById(dto.getUserId());
            task.setUsers(userDtoToEntity(userDto));
        }
        return task;
    }

    public List<TaskDTO> taskEntityToDto(List<Task> taskList) {

        if(!taskList.isEmpty()){
            List<TaskDTO> listDto = new ArrayList<>();
            for (Task taskEntity: taskList) {
                listDto.add(taskEntityToDto(taskEntity));
            }
            return listDto;

        }
        return Collections.emptyList();
    }
}
