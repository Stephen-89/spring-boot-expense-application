package com.stephen.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stephen.login.dto.ExpenseTypesTotalsDto;
import com.stephen.login.entity.ExpenseType;
import com.stephen.login.service.ExpenseTypeService;
import com.stephen.login.util.EntityToDtoMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ExpenseTypesController {

	@Autowired
	private ExpenseTypeService expenseTypeService;
	
	@GetMapping("/expense-type")
	public List<ExpenseType> getAllExpenseTypes() {
		return expenseTypeService.getAllExpenseTypes();
	}

	@GetMapping("/expense-type-totals")
	public List<ExpenseTypesTotalsDto> getAllExpenseTotalsByTypes() {
		List<Object[]> expenseTotalsByTypes = expenseTypeService.getAllExpenseTotalsByTypes();
		return EntityToDtoMapper.convertToExpenseTypesTotalsDto(expenseTotalsByTypes);
	}
	
}






















