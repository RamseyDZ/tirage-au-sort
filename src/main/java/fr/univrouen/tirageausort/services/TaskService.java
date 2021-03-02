package fr.univrouen.tirageausort.services;

import fr.univrouen.tirageausort.converter.EntityConverter;
import fr.univrouen.tirageausort.dtos.TaskDTO;
import fr.univrouen.tirageausort.model.Task;
import fr.univrouen.tirageausort.repositories.TaskRepository;
import fr.univrouen.tirageausort.services.interfaces.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EntityConverter entityConverter;

    @Override
    public TaskDTO findTaskById(UUID idTask) {
        Optional<Task> task = taskRepository.findById(idTask);
        if (task.isPresent()){
            Task task1 = task.get();
            TaskDTO taskDto = entityConverter.TaskEntityToDto(task1);
            return taskDto;
        }
        return null;
    }

    @Override
    public List<TaskDTO> findAllTasks() {
        List<Task> taskList = (List<Task>) taskRepository.findAll();
        return entityConverter.TaskEntityToDto(taskList);
    }

    @Override
    public List<TaskDTO> findFreeTasks() {
        List<Task> taskList = (List<Task>) taskRepository.findAll();
        for (Task entityTask: taskList) {
            if (!entityTask.isFinished()){
                // si la tâche est finis non
            }
        }
        return null;
    }

    @Override
    public int countTasks() {

        List<Task> taskList = (List<Task>) taskRepository.findAll();
        return taskList.size();
    }

    @Override
    public TaskDTO addTask(TaskDTO taskDTO) {
        Task taskEntity= entityConverter.TaskDtoToEntity(taskDTO);
        return entityConverter.TaskEntityToDto(taskRepository.save(taskEntity));
    }

    @Override
    public Boolean deleteTaskById(UUID idTask) {
        if (taskRepository.existsById(idTask))
        {
            taskRepository.deleteById(idTask);
            return true;
        }
        return false;
    }

    @Override
    /**
     * Mettre à jour une task
     * @param une tâche DTO ou nous avons vérifier que l'id existe déja
     * @return la tâche DTO aprés le mise à jour si la tâche exite sinon retourner une valeur null
     */
    public TaskDTO updateTask(TaskDTO taskDTO) {
        // vérifier ici ou bien au controller
        if (taskDTO != null){
            Task entityTask = entityConverter.TaskDtoToEntity(taskDTO);
            return entityConverter.TaskEntityToDto(taskRepository.save(entityTask));
        }

        return null;
    }
}
