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
            return entityConverter.taskEntityToDto(task1);
        }
        return null;
    }

    @Override
    public List<TaskDTO> findAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        return entityConverter.taskEntityToDto(taskList);
    }

    @Override
    public List<TaskDTO> findFreeTasks() {
        List<Task> taskList = taskRepository.findAll();
        List<TaskDTO> freeTasksDtoList = new ArrayList<>();
        for (Task entityTask: taskList) {
            if (!entityTask.isFinished() && entityTask.getUsers() == null){
                // si la tâche est finis non
                freeTasksDtoList.add(entityConverter.taskEntityToDto(entityTask));
            }
        }
        return freeTasksDtoList;
    }

    @Override
    public List<TaskDTO> findFinishedTasks() {
        List<Task> taskList = taskRepository.findAllByIsFinishedIsTrue();
        List<TaskDTO> finishedTaskDto = new ArrayList<>();
        for (Task entityTask: taskList) {
            finishedTaskDto.add(entityConverter.taskEntityToDto(entityTask));
        }
        return finishedTaskDto;
    }

    @Override
    public List<TaskDTO> findInProgressTasks() {
        List<Task> taskList = taskRepository.findAll();
        List<TaskDTO> finishedTaskDto = new ArrayList<>();
        for (Task entityTask: taskList) {
            if(!entityTask.isFinished() && entityTask.getUsers()!=null)
            {
                finishedTaskDto.add(entityConverter.taskEntityToDto(entityTask));
            }
        }
        return finishedTaskDto;
    }


    @Override
    public int countTasks() {
        List<Task> taskList = taskRepository.findAll();
        return taskList.size();
    }

    @Override
    public TaskDTO addTask(TaskDTO taskDTO) {
        Task taskEntity= entityConverter.taskDtoToEntity(taskDTO);
        return entityConverter.taskEntityToDto(taskRepository.save(taskEntity));
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
    public TaskDTO updateTask(TaskDTO taskDTO, String id) {
        // vérifier ici ou bien au controller
        if (taskDTO != null ){
            Optional<Task> task = taskRepository.findById(UUID.fromString(id));
            if (task.isPresent()) {
                Task entityTask = task.get();
                if(taskDTO.getDescription()!=null) {
                    entityTask.setDescription(taskDTO.getDescription());
                }
                if(taskDTO.getDuration()>0) {
                    entityTask.setDuration(taskDTO.getDuration());
                }
                return entityConverter.taskEntityToDto(taskRepository.save(entityTask));
            }
        }

        return new TaskDTO();
    }

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
            return entityConverter.taskEntityToDto(taskRepository.save(task));
        }
        return new TaskDTO();
    }
    @Override
    public TaskDTO assignedTask(UUID uuidTask, UserDTO userDTO) {
        Optional<Task> taskOptional = taskRepository.findById(uuidTask);
        Optional<Users> userOptional = userRepository.findById(userDTO.getIdUser());
        if (taskOptional.isPresent() && userOptional.isPresent()){
            Task task = taskOptional.get();
            if(!task.isFinished()) {
                task.setUsers(userOptional.get());
            }
            return entityConverter.taskEntityToDto(taskRepository.save(task));
        }
        return null;
    }


    // Task occupée par un utilisateur
}
