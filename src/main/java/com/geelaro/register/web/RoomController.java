package com.geelaro.register.web;

import com.geelaro.register.domain.entity.Room;
import com.geelaro.register.domain.entity.RoomNum;
import com.geelaro.register.repository.RoomNumRepository;
import com.geelaro.register.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RoomController {
    @Autowired
    private IGameService gameService;

    @Autowired
    private RoomNumRepository roomNumRepository;

    @PostMapping("/create")
    Map create(@RequestBody Map<String, Object> params){
        Room room=new Room();
        room.setId(gameService.GenerateRoomId());
        room.setRoom_name((String) params.get("room_name"));
        Integer num=Integer.parseInt((String) params.get("room_num"));
        if (num==0){
            room.setMax_user_num(num);
            room.setPlay_one_num((Integer) params.get("one_num"));
            room.setPlay_two_num((Integer) params.get("two_num"));
            room.setPlay_three_num((Integer) params.get("three_num"));
        }else {
            RoomNum roomNum=roomNumRepository.findByUserNum(num);
            room.setMax_user_num(roomNum.getUserNum());
            room.setPlay_one_num(roomNum.getOneNum());
            room.setPlay_two_num(roomNum.getTwoNum());
            room.setPlay_three_num(roomNum.getThreeNum());
        }
        if ((Boolean) params.get("need_pass")){
            room.setPassword((String) params.get("room_pass"));
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        room.setRoommate_name(userDetails.getUsername());
        gameService.AddRoom(room);
        Map map=new HashMap();
        map.put("username",userDetails.getUsername());
        map.put("room_id",room.getId());
        return map;
    }

    @PostMapping("/alluser")
    List getAllUsername(@RequestBody Map<String, Object> params){
        Integer room_id=null;
        if (params.get("room_id")!=null){
            room_id = (Integer)params.get("room_id");
        }
        Room room = gameService.GetRoom(room_id);
        return room.getAllUsername();
    }

    @GetMapping("/allroom")
    List getAllRoom(){
        return gameService.getAllRoom();
    }
}
