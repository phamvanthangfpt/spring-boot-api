package com.fpt.thang.model.repository.user;

import com.fpt.thang.model.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    // You can add custom query methods here if needed
}
