package com.geelaro.register.domain.entity;

import lombok.Data;

import javax.websocket.Session;

@Data
public class Gamer {
    private Integer id;
    private String name;
    private boolean alive;  //存活

    public Gamer(Integer id,String name) {
        this.id=id;
        this.name = name;
        this.alive=true;
    }
}
