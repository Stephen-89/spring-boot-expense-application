package com.stephen.login.service;

import java.util.List;

import com.stephen.login.entity.ExpenseType;

public interface ExpenseTypeService {
	
	List<ExpenseType> getAllExpenseTypes();
	
	List<Object[]> getAllExpenseTotalsByTypes();
	
}
