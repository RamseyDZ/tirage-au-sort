package fr.univrouen.tirageausort.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDTO {
    private UUID idUser;
    private String name;
    private boolean status;
    private List<TaskDTO> tasks;

    public UserDTO() {

    }

    public UserDTO(UUID idUser, String name, boolean status, List<TaskDTO> tasks) {
        this.idUser = idUser;
        this.name = name;
        this.status = status;
        this.tasks = tasks;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

        public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}
