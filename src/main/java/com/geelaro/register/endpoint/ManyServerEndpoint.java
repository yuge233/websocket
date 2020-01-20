package com.geelaro.register.endpoint;

import com.alibaba.fastjson.JSON;
import com.geelaro.register.domain.entity.Gamer;
import com.geelaro.register.domain.entity.Room;
import com.geelaro.register.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/game/{room_id}/{username}",encoders = {MessageEncoder.class})
public class ManyServerEndpoint {

    private static IGameService gameService;

    @Autowired
    public void setGameService(IGameService gameService){
        ManyServerEndpoint.gameService=gameService;
    }

    @OnOpen
    public void openSession(@PathParam("room_id") Integer room_id, @PathParam("username") String username, Session session){
//        if (!rooms.containsKey(room)){
//            rooms.put(room,new ConcurrentHashMap<>(20));
//            room_users.put(room,new Vector<>(20));
//        }
//        Map<String, Session> sessions=rooms.get(room);
//        Vector<String> user=room_users.get(room);
//        sessions.put(session.getId(),session);
//        user.add(username);
//        Message message=new Message("欢迎用户["+username+"]",1);
//        message.setUsers(user);
//        sendTextAll(room,message);
        Room room=gameService.GetRoom(room_id);
        room.addGamer(new Gamer(room.generateGamerId(),username),session);
        sendTextAll(room_id,new Message("欢迎"+username+"进入房间","system",1));
    }

    public void sendText(Session session, Message message){
        RemoteEndpoint.Basic basic=session.getBasicRemote();
        try {
            basic.sendObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTextAll(Integer room_id, Message message){
        Room room=gameService.GetRoom(room_id);
        List<Session> sessions = room.getSessions();
        sessions.forEach(session -> {
            if (session!=null&&session.isOpen())
                sendText(session,message);
        });
    }

    @OnMessage
    public void message(@PathParam("room_id") Integer room_id, @PathParam("username") String username, String message){
        Integer mode = 0;
        String mess=null,user=null;
        Map<String, Object> map= (Map) JSON.parse(message);
        if (map.get("mode")!=null){
            mode=(Integer) map.get("mode");
        }
        if (map.get("message")!=null){
            mess=(String) map.get("message");
        }
        if (map.get("username")!=null){
            user=(String) map.get("username");
        }
        Message message_obj=new Message(mess,user,mode);
        sendTextAll(room_id,message_obj);
    }

    @OnClose
    public void close(@PathParam("room_id") Integer room_id, @PathParam("username") String username, Session session){
//        Map<String, Session> sessions=rooms.get(room);
//        Vector<String> users=room_users.get(room);
//        sessions.remove(session.getId());
//        users.remove(username);
//        if (sessions.isEmpty()){
//            rooms.remove(room);
//            room_users.remove(room);
//        }
//        Message message=new Message("["+username+"]离开了聊天室!",1);
//        message.setUsers(users);
//        sendTextAll(room,message);
        Room room=gameService.GetRoom(room_id);
        if (username == room.getRoommate_name()){//房主退出

        }else {
            room.removeGamer(username);
            sendTextAll(room_id,new Message(username+"退出了房间","system",1));
        }

    }
}