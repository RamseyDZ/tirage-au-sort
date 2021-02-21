package fr.univrouen.tirageausort.dtos;

import fr.univrouen.tirageausort.model.Users;

import java.util.UUID;

public class TaskDTO {
    private UUID idTask;
    private String description;
    private boolean isFinished;
    private int duration;
    private Users users;

    public TaskDTO(){

    }

    public TaskDTO(UUID idTask, String description, boolean isFinished, int duration,Users users) {
        this.idTask = idTask;
        this.description = description;
        this.isFinished = isFinished;
        this.duration = duration;
        this.users = users;
    }

    public TaskDTO(String description, int duration) {
        this.description = description;
        this.duration = duration;
        this.isFinished = false;
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
}
