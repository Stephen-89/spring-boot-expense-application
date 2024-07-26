package com.stephen.expense.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stephen.expense.entity.Expense;
import com.stephen.expense.service.ExpenseService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;
	
	@GetMapping("/expenses")
	public Page<Expense> getAllExpenses(Pageable page) {
		return expenseService.getAllExpenses(page);
	}
	
	@GetMapping("/expenses/{id}")
	public Expense getExpenseById(@PathVariable Long id){
		return expenseService.getExpenseById(id);
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expense")
	public void deleteExpenseById(@RequestParam Long id) {
		expenseService.deleteExpenseById(id);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses")
	public void deleteExpenseByIds(@RequestParam Set<Long> ids) {
		expenseService.deleteExpenseByIds(ids);
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/expenses")
	public Expense saveExpenseDetails(@Valid @RequestBody Expense expense) {
		return expenseService.saveExpenseDetails(expense);
	}
	
	@PutMapping("/expenses/{id}")
	public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable Long id){
		return expenseService.updateExpenseDetails(id, expense);
	}
	
	@GetMapping("/expenses/name")
	public Page<Expense> getExpensesByName(@RequestParam String keyword, Pageable page) {
		return expenseService.readByName(keyword, page);
	}
	
	@GetMapping("/expenses/date")
	public Page<Expense> getExpensesByDates(@RequestParam(required = false) Date startDate,
											@RequestParam(required = false) Date endDate,
											Pageable page) {
		return expenseService.readByDate(startDate, endDate, page);
	}

	@GetMapping("/expenses/type")
	public Page<Expense> getExpensesByType(@RequestParam Long expenseTypeId, Pageable page) {
		return expenseService.readByType(expenseTypeId, page);
	}

	@GetMapping("/expenses-total")
	public BigDecimal totalExpenses() {
		return expenseService.totalExpenses();
	}

	@GetMapping("/expenses-type-total")
	public BigDecimal totalTypeExpenses(@RequestParam Long expenseTypeId) {
		return expenseService.totalTypeExpenses(expenseTypeId);
	}
	
}






















