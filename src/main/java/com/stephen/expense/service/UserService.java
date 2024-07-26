package com.stephen.expense.service;

import com.stephen.expense.dto.UserDto;
import com.stephen.expense.entity.User;

public interface UserService {
	
	User createUser(UserDto user);
	
	User updateUser(UserDto user);
	
	void deleteUser();
	
	User getLoggedInUser();
	
}
