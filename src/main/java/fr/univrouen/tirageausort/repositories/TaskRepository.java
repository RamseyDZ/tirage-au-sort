package fr.univrouen.tirageausort.repositories;

import fr.univrouen.tirageausort.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<Task, UUID> {
    //List<Task> getTaskByIsFinished(boolean isFinished);

    //@Query(value = "SELECT T FROM Task T WHERE T.users.id = "" ")
    //List<Task> getTaskFreeUsers();
}
