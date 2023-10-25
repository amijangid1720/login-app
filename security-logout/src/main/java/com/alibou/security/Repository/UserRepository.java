package com.alibou.security.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alibou.security.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
