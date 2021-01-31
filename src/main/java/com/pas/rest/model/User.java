package com.pas.rest.model;

import com.pas.rest.SignableEntity;
import com.pas.rest.adapters.SerializeStringToEmptyAdapter;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements SignableEntity {

    private String id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String login;
    @NotNull
    @NotEmpty
    @Size(min = 8)
    @JsonbProperty
    private String password;
    private String accessLevel;
    private boolean active;

    public User() {
        this.active = true;
        this.accessLevel = this.getClass().getSimpleName().toUpperCase();
    }

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.active = true;
        this.accessLevel = this.getClass().getSimpleName().toUpperCase();
    }
    
    @JsonbTypeAdapter(SerializeStringToEmptyAdapter.class)
    public String getPassword() {
        return password;
    }
    
    @Override
    @JsonbTransient
    public String getSignablePayload() {
        return login;
    }
}
