package com.stephen.expense.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stephen.expense.entity.Expense;

public interface ExpenseService {
	
	Page<Expense> getAllExpenses(Pageable page);
	
	Expense getExpenseById(Long id);
	
	void deleteExpenseById(Long id);
	
	void deleteExpenseByIds(Set<Long> ids);
	
	Expense saveExpenseDetails(Expense expense);
	
	Expense updateExpenseDetails(Long id, Expense expense);
	
	Page<Expense> readByName(String keyword, Pageable page); 
	
	Page<Expense> readByDate(Date startDate, Date endDate, Pageable page);

	Page<Expense> readByType(Long expenseTypeId, Pageable page); 

	BigDecimal totalExpenses(); 
	
	BigDecimal totalTypeExpenses(Long expenseTypeId);
	
}
