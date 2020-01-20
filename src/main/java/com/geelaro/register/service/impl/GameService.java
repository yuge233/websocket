package com.geelaro.register.service.impl;

import com.geelaro.register.domain.entity.Room;
import com.geelaro.register.service.IGameService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService implements IGameService {
    private ConcurrentHashMap<Integer,Room> rooms;

    @PostConstruct
    public void init(){
        rooms=new ConcurrentHashMap<>();
    }

    @Override
    public void AddRoom(Room room) {
        rooms.put(room.getId(),room);
    }

    @Override
    public void ChangeRoom(Room room) {
        rooms.replace(room.getId(),room);
    }

    @Override
    public void DelRoom(Room room) {
        rooms.remove(room.getId());
    }

    @Override
    public boolean ContainRoom(Integer room_id) {
        return rooms.containsKey(room_id);
    }

    @Override
    public Room GetRoom(Integer room_id) {
        return rooms.get(room_id);
    }

    @Override
    public int GenerateRoomId() {
        int id=rooms.size();
        while (rooms.containsKey(id))
            id++;
        return id;
    }

    @Override
    public List getAllRoom() {
        List<Room> result=new ArrayList<>();
        for (Room room:rooms.values()){
            result.add(room);
        }
        return result;
    }
}
