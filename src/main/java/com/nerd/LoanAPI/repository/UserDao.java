package com.nerd.LoanAPI.repository;

import com.nerd.LoanAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

}
