package fr.univrouen.tirageausort.dtos;

import java.util.UUID;

public class TaskDTO {
    private UUID idTask;
    private String description;
    private boolean isFinished;
    private int duration;
    private UUID userId;
    private String userName;


    public TaskDTO(){
        // Constructor qui retourne rien
        // utilisé pour initialisation
    }

    public TaskDTO(UUID idTask, String description, boolean isFinished, int duration) {
        this.idTask = idTask;
        this.description = description;
        this.isFinished = isFinished;
        this.duration = duration;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
