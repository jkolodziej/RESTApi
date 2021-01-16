package com.pas.rest.model;

import com.pas.rest.adapters.SerializeStringToEmptyAdapter;
import javax.json.bind.annotation.JsonbTypeAdapter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String id;
    private String login;
    private String password;
    private boolean active;

    public User() {
        this.active = true;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.active = true;
    }

    public void setActivity(boolean active) {
        this.active = active;
    }
    
    @JsonbTypeAdapter(SerializeStringToEmptyAdapter.class)
    public String getPassword(){
        return password;
    }
}
