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

    private Boolean status;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Task> taskList;

    public Users(UUID idUser, String name, Boolean status, List<Task> taskList) {
        this.id = idUser;
        this.name = name;
        this.status = status;
    }

    public Users(String name, List<Task> taskList) {
        this.status = false;
        this.name = name;
        this.taskList = taskList;
    }

    public Users(){}

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
