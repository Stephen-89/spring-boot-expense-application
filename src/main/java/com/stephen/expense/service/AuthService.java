package com.stephen.expense.service;

import org.springframework.http.ResponseEntity;

import com.stephen.expense.dto.AuthModel;
import com.stephen.expense.entity.User;

public interface AuthService {

	ResponseEntity<User> loginUser(AuthModel authModel) throws Exception;

}
