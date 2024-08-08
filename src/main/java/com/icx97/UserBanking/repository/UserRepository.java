package com.icx97.UserBanking.repository;

import com.icx97.UserBanking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
