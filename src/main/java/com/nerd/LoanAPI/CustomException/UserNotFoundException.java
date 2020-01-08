package com.nerd.LoanAPI.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "A user with the provided id was not found.")
public class UserNotFoundException extends RuntimeException {
}
