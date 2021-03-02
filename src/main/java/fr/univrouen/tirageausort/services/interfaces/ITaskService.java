package fr.univrouen.tirageausort.services.interfaces;

import fr.univrouen.tirageausort.dtos.TaskDTO;
import java.util.List;
import java.util.UUID;

public interface ITaskService {
    TaskDTO findTaskById(UUID idTask);

    List<TaskDTO> findAllTasks();

    List<TaskDTO> findFreeTasks();

    int countTasks();

    TaskDTO addTask(TaskDTO taskDTO);

    Boolean deleteTaskById(UUID idTask);

    TaskDTO updateTask(TaskDTO taskDTO);
}
