package com.geelaro.register.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RoomNum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer userNum;   //房间人数

    @Column
    private Integer oneNum;   //平民数量

    @Column
    private Integer twoNum;   //卧底数量

    @Column
    private Integer threeNum;   //白板数量
}
