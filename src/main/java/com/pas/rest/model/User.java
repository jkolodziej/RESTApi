package com.pas.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.pas.rest.SignableEntity;
import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.AssertTrue;
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
    private String login;
    @NotNull
    @NotEmpty
    @Size(min = 8)
    private String password;
    private String accessLevel;
    @AssertTrue
    private boolean active;

    public User() {
        this.active = true;
        this.accessLevel = this.getClass().getSimpleName().toUpperCase();
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.active = true;
        this.accessLevel = this.getClass().getSimpleName().toUpperCase();
    }

    public void setActivity(boolean active) {
        this.active = active;
    }
    
//    @JsonbTypeAdapter(SerializeStringToEmptyAdapter.class)
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    
    @Override
    @JsonbTransient
    public String getSignablePayload() {
        return login;
    }
}
