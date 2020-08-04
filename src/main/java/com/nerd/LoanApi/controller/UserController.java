package com.nerd.LoanApi.controller;

import com.nerd.LoanApi.CustomException.UserNotFoundException;
import com.nerd.LoanApi.model.contract.UserResponseBody;
import com.nerd.LoanApi.model.provider.User;
import com.nerd.LoanApi.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "users", consumes = "application/json", produces = "application/json")
public class UserController {
    @Autowired
    private UserDao dao;

    @GetMapping("")
    public ResponseEntity<List<UserResponseBody>> getAll() {
        List<User> users = dao.findAll();
        List<UserResponseBody> userResponseBodies = new ArrayList<>();

        users.forEach(user -> userResponseBodies.add(new UserResponseBody(user)));
        return new ResponseEntity<>(userResponseBodies, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserResponseBody> getById(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(
                new UserResponseBody(dao.findById(userId).orElseThrow(UserNotFoundException::new)),
                HttpStatus.OK
        );
    }

}
