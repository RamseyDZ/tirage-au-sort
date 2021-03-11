package fr.univrouen.tirageausort.converter;

import fr.univrouen.tirageausort.dtos.TaskDTO;
import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.model.Task;
import fr.univrouen.tirageausort.model.Users;
import fr.univrouen.tirageausort.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntityConverter {
    @Autowired
    private UserService userService;

    public UserDTO UserEntityToDto(Users users){
        UserDTO dto = new UserDTO();
        dto.setIdUser(users.getId());
        dto.setName(users.getName());
        dto.setFree(users.isFree());
        List<TaskDTO> tasksDto = new ArrayList<>();
        List<Task> taskList = users.getTaskList();
        if(taskList!=null) {
            for (Task task : taskList) {
                tasksDto.add(TaskEntityToDto(task));
            }
        }
        dto.setTasks(tasksDto);

        return dto;
    }

    public TaskDTO TaskEntityToDto(Task task){
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

    public Users UserDtoToEntity(UserDTO dto){
        Users users = new Users();
        users.setId(dto.getIdUser());
        users.setName(dto.getName());
        users.setFree(dto.isFree());
        List<TaskDTO> tasksDto = dto.getTasks();
        if (tasksDto!=null) {
            List<Task> tasks = new ArrayList<>();
            for (TaskDTO tDto : tasksDto) {
                tasks.add(TaskDtoToEntity(tDto));
            }
            users.setTaskList(tasks);
        }
        return users;
    }

    public Task TaskDtoToEntity(TaskDTO dto){
        Task task = new Task();
        task.setId(dto.getIdTask());
        task.setDescription(dto.getDescription());
        task.setDuration(dto.getDuration());
        task.setFinished(dto.isFinished());
        if(dto.getUserId()!=null)
        {
            UserDTO userDto = userService.findUserById(dto.getUserId());
            task.setUsers(UserDtoToEntity(userDto));
        }
        return task;
    }

    public List<TaskDTO> TaskEntityToDto (List<Task> taskList) {

        if(!taskList.isEmpty()){
            List<TaskDTO> listDto = new ArrayList<>();
            for (Task taskEntity: taskList) {
                listDto.add(TaskEntityToDto(taskEntity));
            }
            return listDto;

        }
        return null;
    }
}
