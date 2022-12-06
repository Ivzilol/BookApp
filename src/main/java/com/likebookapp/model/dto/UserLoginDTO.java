package com.likebookapp.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginDTO {

    private Long id;
    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 symbols")
    @NotNull
    private String username;
    @Size(min = 3, max = 20, message = "Password should be between 3 and 20 symbols")
    @NotNull
    private String password;

    public UserLoginDTO() {
    }

    public Long getId() {
        return id;
    }

    public UserLoginDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserLoginDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
