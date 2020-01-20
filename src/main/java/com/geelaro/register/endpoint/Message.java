package com.geelaro.register.endpoint;

import lombok.Data;

import java.io.Serializable;
import java.util.Vector;

@Data
class Message implements Serializable {
    private String message;
    private String username;
    private int mode;

    public Message(String message, String username, int mode) {
        this.message = message;
        this.username = username;
        this.mode = mode;
    }
}
