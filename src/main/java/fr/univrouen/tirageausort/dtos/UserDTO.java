package fr.univrouen.tirageausort.dtos;

import java.util.List;
import java.util.UUID;

public class UserDTO {
    private UUID idUser;
    private String name;
    private boolean free;
    private boolean archived;
    private List<TaskDTO> tasks;

    public UserDTO() {

    }


    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}
