package com.stephen.expense.service;

import java.util.List;

import com.stephen.expense.entity.ExpenseType;

public interface ExpenseTypeService {
	
	List<ExpenseType> getAllExpenseTypes();
	
	List<Object[]> getAllExpenseTotalsByTypes();
	
}
