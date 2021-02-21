package fr.univrouen.tirageausort.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID idTask;

    String description;

    boolean isFinished;

    int duration;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate = new Date();

    @ManyToOne
    @JoinColumn(name="user_id")
    private Users users;


    public Task() {
    }


    public UUID getIdTask() {
        return idTask;
    }

    public void setIdTask(UUID idTask) {
        this.idTask = idTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
