package fr.univrouen.tirageausort.services.interfaces;

import fr.univrouen.tirageausort.dtos.TaskDTO;
import fr.univrouen.tirageausort.dtos.UserDTO;

import java.util.List;
import java.util.UUID;

public interface ITaskService {
    TaskDTO findTaskById(UUID idTask);

    List<TaskDTO> findAllTasks();

    List<TaskDTO> findFreeTasks();

    List<TaskDTO> findFinishedTasks();

    List<TaskDTO> findInProgressTasks();

    int countTasks();

    TaskDTO addTask(TaskDTO taskDTO);

    Boolean deleteTaskById(UUID idTask);

    TaskDTO updateTask(TaskDTO taskDTO, String id);

    // task finished  :
    /**
     * Une tâche finie
     * @param taskId tâche DTO ou nous avons vérifier que l'id existe déja
     * @return la tâche DTO aprés le mise à jour si la tâche exite sinon retourner une valeur null
     */
    TaskDTO finishedTask(UUID taskId);

    TaskDTO assignedTask(UUID uuidTask, UserDTO userDTO);
}
