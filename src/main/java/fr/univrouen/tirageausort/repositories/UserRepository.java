package fr.univrouen.tirageausort.repositories;

import fr.univrouen.tirageausort.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    List<Users> getByName(String name);
}


