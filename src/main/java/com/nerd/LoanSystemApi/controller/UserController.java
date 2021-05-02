package com.nerd.LoanSystemApi.controller;

import com.nerd.LoanSystemApi.CustomException.UserNotFoundException;
import com.nerd.LoanSystemApi.model.contract.UserResponseBody;
import com.nerd.LoanSystemApi.model.provider.User;
import com.nerd.LoanSystemApi.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserDao dao;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<UserResponseBody>> getAllUsers() {
        List<User> users = dao.findAll();
        List<UserResponseBody> userResponseBodies = new ArrayList<>();

        users.forEach(user -> userResponseBodies.add(new UserResponseBody(user)));
        return new ResponseEntity<>(userResponseBodies, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    ResponseEntity<UserResponseBody> getUserById(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(
                new UserResponseBody(dao.findById(userId).orElseThrow(UserNotFoundException::new)),
                HttpStatus.OK
        );
    }

}
