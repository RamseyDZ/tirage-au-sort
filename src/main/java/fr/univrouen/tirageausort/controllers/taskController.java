package fr.univrouen.tirageausort.controllers;

import fr.univrouen.tirageausort.dtos.TaskDTO;
import fr.univrouen.tirageausort.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value="/task")
public class taskController {

    @Autowired
    private TaskService taskService;

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
     * @param TaskDTO une instance d'une tâche à enregistré
     */
    @PostMapping(value = "/save")
    public ResponseEntity<TaskDTO> addUser(@RequestBody TaskDTO dto){
        try{
            TaskDTO dtoAdded = taskService.addTask(dto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtoAdded);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable UUID id){


        return null;
    }

    @GetMapping(value = "/all/free")
    public ResponseEntity<List<TaskDTO>> getAllFreeTasks(){
        try{
            List<TaskDTO> taskDTOFreeList =  taskService.findFreeTasks();

            if (taskDTOFreeList == null)
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

            return ResponseEntity.status(HttpStatus.OK).body(taskDTOFreeList);
        }catch (ExceptionInInitializerError e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



}
