package com.geelaro.register.domain.entity;

import lombok.Data;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Room {
    private int id;

    private String room_name;

    private int max_user_num;   //房间人数
    private int play_one_num;   //平民数量
    private int play_two_num;   //卧底数量
    private int play_three_num;   //白板数量

    private String password;    //房间密码

    private ConcurrentHashMap<Integer,Gamer> gamers;
    private String roommate_name;

    private ConcurrentHashMap<Integer, Session> sessions;

    private int state;  //房间状态
    //0未开始，1准备阶段，2发言阶段，3投票阶段
    public static int STATE_NOT_BEGIN = 0;
    public static int STATE_READY = 1;
    public static int STATE_SPEAK = 2;
    public static int STATE_VOTE = 3;

    public Room(){
        this.state=STATE_NOT_BEGIN;
        this.gamers=new ConcurrentHashMap<>();
        this.sessions=new ConcurrentHashMap<>();
    }

    public Room(int id, int max_user_num, int play_one_num, int play_two_num, int play_three_num,String password) {
        this.id = id;
        this.max_user_num = max_user_num;
        this.play_one_num = play_one_num;
        this.play_two_num = play_two_num;
        this.play_three_num = play_three_num;
        this.password=password;
        this.state=STATE_NOT_BEGIN;
        this.gamers=new ConcurrentHashMap<>();
        this.sessions=new ConcurrentHashMap<>();
    }

    public boolean addGamer(Gamer gamer,Session session){
        if (gamers.size()<max_user_num){
            gamers.put(gamer.getId(),gamer);
            sessions.put(gamer.getId(),session);
            return true;
        }
        return false;
    }

    public boolean removeGamer(Integer id){
        if (gamers.containsKey(id)){
            gamers.remove(id);
            sessions.remove(id);
            return true;
        }
        return false;
    }

    public boolean removeGamer(String username){
        for(Gamer gamer:gamers.values()){
            if (gamer.getName()==username){
                removeGamer(gamer.getId());
                return true;
            }
        }
        return false;
    }

    public int generateGamerId(){
        int i=1;
        while (gamers.containsKey(i))
            i++;
        return i;
    }

    public List getSessions(){
        Collection<Session> collection= sessions.values();
        List<Session> list=new ArrayList<>(collection);
        return list;
    }

    public List getAllUsername(){
        List<String> result=new ArrayList<>();
        for (Gamer gamer:gamers.values()){
            result.add(gamer.getName());
        }
        return result;
    }
}
