package com.rea.cajaya.repository;

import com.rea.cajaya.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
