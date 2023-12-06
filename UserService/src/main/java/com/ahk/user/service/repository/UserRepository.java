package com.ahk.user.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahk.user.service.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

}
