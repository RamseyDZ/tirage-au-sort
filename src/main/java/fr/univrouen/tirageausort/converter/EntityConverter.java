package fr.univrouen.tirageausort.converter;

import fr.univrouen.tirageausort.dtos.TaskDTO;
import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.model.Task;
import fr.univrouen.tirageausort.model.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntityConverter {


    public UserDTO UserEntityToDto(Users users){
        UserDTO dto = new UserDTO();
        dto.setIdUser(users.getId());
        dto.setName(users.getName());
        dto.setStatus(users.getStatus());
        List<TaskDTO> tasksDto = new ArrayList<>();
        for (Task task: users.getTaskList()) {
            tasksDto.add(TaskEntityToDto(task));
        }
        dto.setTasks(tasksDto);

        return dto;
    }

    public TaskDTO TaskEntityToDto(Task task){
        TaskDTO dto = new TaskDTO();
        dto.setIdTask(task.getIdTask());
        dto.setDescription(task.getDescription());
        dto.setDuration(task.getDuration());
        dto.setFinished(task.isFinished());

        return dto;
    }

    public Users UserDtoToEntity(UserDTO dto){
        Users users = new Users();
        users.setId(dto.getIdUser());
        users.setName(dto.getName());
        users.setStatus(dto.isStatus());
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
        task.setIdTask(dto.getIdTask());
        task.setDescription(dto.getDescription());
        task.setDuration(dto.getDuration());
        task.setFinished(dto.isFinished());

        return task;
    }
}
