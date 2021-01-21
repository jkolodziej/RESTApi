package com.pas.rest.model;

import com.pas.rest.SignableEntity;
import com.pas.rest.adapters.SerializeStringToEmptyAdapter;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements SignableEntity {

    private String id;
    private String login;
    private String password;
    private String accessLevel;
    private boolean active;

    public User() {
        this.active = true;
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
