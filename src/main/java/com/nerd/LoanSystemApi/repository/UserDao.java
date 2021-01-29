package com.nerd.LoanSystemApi.repository;

import com.nerd.LoanSystemApi.model.provider.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

}
