package fr.univrouen.tirageausort.services;

import fr.univrouen.tirageausort.converter.EntityConverter;
import fr.univrouen.tirageausort.dtos.TaskDTO;
import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.model.Task;
import fr.univrouen.tirageausort.model.Users;
import fr.univrouen.tirageausort.repositories.TaskRepository;
import fr.univrouen.tirageausort.repositories.UserRepository;
import fr.univrouen.tirageausort.services.interfaces.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityConverter entityConverter;

    @Override
    public TaskDTO findTaskById(UUID idTask) {
        Optional<Task> task = taskRepository.findById(idTask);
        if (task.isPresent()){
            Task task1 = task.get();
            return entityConverter.TaskEntityToDto(task1);
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
        List<TaskDTO> freeTasksDtoList = new ArrayList<>();
        for (Task entityTask: taskList) {
            if (!entityTask.isFinished() && entityTask.getUsers() == null){
                // si la tâche est finis non
                freeTasksDtoList.add(entityConverter.TaskEntityToDto(entityTask));
            }
        }
        return freeTasksDtoList;
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
        if (taskDTO != null && !taskRepository.findById(taskDTO.getIdTask()).isEmpty()){
            Task entityTask = entityConverter.TaskDtoToEntity(taskDTO);
            return entityConverter.TaskEntityToDto(taskRepository.save(entityTask));
        }

        return null;
    }

    // task finished  :
    @Override
    /**
     * Une tâche finie
     * @param une tâche DTO ou nous avons vérifier que l'id existe déja
     * @return la tâche DTO aprés le mise à jour si la tâche exite sinon retourner une valeur null
     */
    public TaskDTO finishedTask(UUID taskId) {
        // chercher la tâche :
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()){
            // faire la mise à jour et changer isFinished
            Task task = taskOptional.get();
            task.setFinished(true);
            return entityConverter.TaskEntityToDto(taskRepository.save(task));
        }
        return null;
    }
    @Override
    public TaskDTO assignedTask(UUID uuidTask, UserDTO userDTO) {
        // chercher la tâche :
        Optional<Task> taskOptional = taskRepository.findById(uuidTask);
        Optional<Users> userOptional = userRepository.findById(userDTO.getIdUser());
        if (taskOptional.isPresent() && userOptional.isPresent()){
            // faire la mise à jour et attribué à un utilisateur
            Task task = taskOptional.get();
            task.setUsers(userOptional.get());
            return entityConverter.TaskEntityToDto(taskRepository.save(task));
        }
        return null;
    }


    // Task occupée par un utilisateur
}
