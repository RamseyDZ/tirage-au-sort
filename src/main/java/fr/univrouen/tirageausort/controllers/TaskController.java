package fr.univrouen.tirageausort.controllers;

import fr.univrouen.tirageausort.dtos.TaskDTO;
import fr.univrouen.tirageausort.dtos.UserDTO;
import fr.univrouen.tirageausort.services.TaskService;
import fr.univrouen.tirageausort.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value="/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    /**
     * Fonction pour chercher tous les taches enregistrés
     * @return List of TaskDTO
     */

    @GetMapping(value = "/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks(){
        try{
            List<TaskDTO> taskDTOList =  taskService.findAllTasks();
            if (taskDTOList == null)
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

            return ResponseEntity.status(HttpStatus.OK).body(taskDTOList);
        }catch (ExceptionInInitializerError e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    /**
     * Enregistrer une tâche en passant les information en body
     * @param taskDTO une instance d'une tâche à enregistré
     */
    @PostMapping(value = "/save")
    public ResponseEntity<TaskDTO> addUser(@RequestBody TaskDTO taskDTO) {
        try {
            TaskDTO dtoAdded = taskService.addTask(taskDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtoAdded);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

    }

    /**
     * Supprimer une tâche en passant son id en params
     *
     * @param id identifiant d'une tâche à enregistré
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable UUID id) {
        try {
            Boolean isDeleted = taskService.deleteTaskById(id);
            if (Boolean.TRUE.equals(isDeleted)) {
                return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(isDeleted);
        } catch (ExceptionInInitializerError exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Récupérer les tâches libres
     */
    @GetMapping(value = "/all/free")
    public ResponseEntity<List<TaskDTO>> getAllFreeTasks() {
        try {
            List<TaskDTO> taskDTOFreeList = taskService.findFreeTasks();

            if (taskDTOFreeList == null)
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());

            return ResponseEntity.status(HttpStatus.OK).body(taskDTOFreeList);
        } catch (ExceptionInInitializerError e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Récupérer les tâches finis
     */
    @GetMapping(value = "/all/finished")
    public ResponseEntity<List<TaskDTO>> getAllFinishedTasks() {
        try {
            List<TaskDTO> taskDTOFreeList = taskService.findFinishedTasks();
            if (taskDTOFreeList.isEmpty())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(taskDTOFreeList);
            return ResponseEntity.status(HttpStatus.OK).body(taskDTOFreeList);
        } catch (ExceptionInInitializerError e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Récupérer les tâches en cours
     */
    @GetMapping(value = "/all/progress")
    public ResponseEntity<List<TaskDTO>> getAllInProgressTasks() {
        try {
            List<TaskDTO> taskDTOFreeList = taskService.findInProgressTasks();
            if (taskDTOFreeList.isEmpty())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(taskDTOFreeList);
            return ResponseEntity.status(HttpStatus.OK).body(taskDTOFreeList);
        } catch (ExceptionInInitializerError e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    @PutMapping(value = "/save/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String id,@RequestBody TaskDTO taskDTO){
        try{
            TaskDTO resultDto = taskService.updateTask(taskDTO, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultDto);
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PutMapping(value = "/finished/{id}")
    public ResponseEntity<TaskDTO> finishedTask(@PathVariable String id){
        try{
            UUID uuidTask = UUID.fromString(id);
            // à modifier en ajoutant la valeur retourner lors la mise à jour
            TaskDTO resultDto = taskService.finishedTask(uuidTask);
            if(resultDto!=null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultDto);
            }
            else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PutMapping(value = "/assign/{id}")
    public ResponseEntity<TaskDTO> assignTask(@PathVariable String id, @RequestBody UserDTO userDTO){
        try{
            UUID uuidTask = UUID.fromString(id);
            // à modifier en ajoutant la valeur retourner lors la mise à jour
            TaskDTO taskDTO = taskService.assignedTask(uuidTask, userDTO);
            if( userDTO==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            else if (taskDTO!=null  && userService.findUserById(userDTO.getIdUser())!=null)
            {
                if(!taskDTO.isFinished() && taskDTO.getUserId()!=null) {
                    return ResponseEntity.status(HttpStatus.CREATED).body(taskDTO);
                } // if task has a user and finished we will not update it directly
                else {
                    return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PutMapping(value = "/unassign/{id}")
    public ResponseEntity<TaskDTO> unAssignTask(@PathVariable String id) {
        try {
            UUID uuidTask = UUID.fromString(id);
            // à modifier en ajoutant la valeur retourner lors la mise à jour
            TaskDTO taskDTO = taskService.unAssignedTask(uuidTask);
            if (taskDTO != null) {
                return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



}
