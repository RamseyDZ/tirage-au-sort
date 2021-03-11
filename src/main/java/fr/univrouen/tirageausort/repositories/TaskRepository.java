package fr.univrouen.tirageausort.repositories;

import fr.univrouen.tirageausort.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<Task, UUID> {
}
