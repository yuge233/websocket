package com.geelaro.register.service;

import com.geelaro.register.domain.entity.Room;

import java.util.List;

public interface IGameService {
    boolean ContainRoom(Integer room_id);
    void AddRoom(Room room);
    void ChangeRoom(Room room);
    void DelRoom(Room room);
    Room GetRoom(Integer room_id);
    int GenerateRoomId();
    List getAllRoom();
}
