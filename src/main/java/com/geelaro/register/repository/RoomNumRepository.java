package com.geelaro.register.repository;

import com.geelaro.register.domain.entity.RoomNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomNumRepository extends JpaRepository<RoomNum,Integer> {
    RoomNum findByUserNum(Integer userNum);
}
