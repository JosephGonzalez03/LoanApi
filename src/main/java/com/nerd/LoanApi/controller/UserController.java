package com.nerd.LoanApi.controller;

import com.nerd.LoanApi.CustomException.UserNotFoundException;
import com.nerd.LoanApi.model.User;
import com.nerd.LoanApi.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
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
