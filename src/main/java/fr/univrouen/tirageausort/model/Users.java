package fr.univrouen.tirageausort.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    private Boolean free;

    private Boolean archived;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL,
                fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Task> tasks;

    // Construcotr
    public Users(){
        // null constructor
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID idUser) {
        this.id = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTaskList() {
        return tasks;
    }

    public void setTaskList(List<Task> taskList) {
        this.tasks = taskList;
    }

    public Boolean isFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }
}
