package com.nerd.LoanAPI.controller;

import com.nerd.LoanAPI.CustomException.UserNotFoundException;
import com.nerd.LoanAPI.model.Loan;
import com.nerd.LoanAPI.model.User;
import com.nerd.LoanAPI.repository.LoanDao;
import com.nerd.LoanAPI.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserDao dao;

    @GetMapping("")
    public List<User> getAll() {
        return dao.findAll();
    }

    @GetMapping("/{userId}")
    User getById(@PathVariable("userId") Integer userId) {
        return dao.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
