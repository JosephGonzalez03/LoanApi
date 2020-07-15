package com.nerd.LoanApi.repository;

import com.nerd.LoanApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

}
