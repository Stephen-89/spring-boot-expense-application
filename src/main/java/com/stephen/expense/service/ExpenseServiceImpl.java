package com.stephen.expense.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stephen.expense.entity.Expense;
import com.stephen.expense.exceptions.ResourceNotFoundException;
import com.stephen.expense.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserService userService;

	@Override
	public Page<Expense> getAllExpenses(Pageable page) {
		return expenseRepository.findByUserId(userService.getLoggedInUser().getId(), page);
	}

	@Override
	public Expense getExpenseById(Long id) {
		Optional<Expense> expense = expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
		if (expense.isPresent()) {
			return expense.get();
		}
		throw new ResourceNotFoundException("Expense is not found for the id " + id);
	}

	@Override
	public void deleteExpenseById(Long id) {
		Expense expense = getExpenseById(id);
		expenseRepository.delete(expense);
	}

	@Override
	public void deleteExpenseByIds(Set<Long> ids) {
		List<Expense> expenses = expenseRepository.findAllById(ids);
		expenseRepository.deleteAll(expenses);
	}

	@Override
	public Expense saveExpenseDetails(Expense expense) {
		expense.setUser(userService.getLoggedInUser());
		return expenseRepository.save(expense);
	}

	@Override
	public Expense updateExpenseDetails(Long id, Expense expense) {
		Expense existingExpense = getExpenseById(id);
		existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
		existingExpense.setDescription(
				expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
		existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
		existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
		existingExpense.setExpenseType(
				expense.getExpenseType() != null ? expense.getExpenseType() : existingExpense.getExpenseType());
		return expenseRepository.save(existingExpense);
	}

	@Override
	public Page<Expense> readByName(String keyword, Pageable page) {
		return expenseRepository.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page);
	}

	@Override
	public Page<Expense> readByDate(Date startDate, Date endDate, Pageable page) {

		if (startDate == null) {
			startDate = new Date(0);
		}

		if (endDate == null) {
			endDate = new Date(System.currentTimeMillis());
		}

		return expenseRepository.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate,
				page);
	}

	@Override
	public Page<Expense> readByType(Long expenseTypeId, Pageable page) {
		return expenseRepository.findByUserIdAndExpenseTypeId(userService.getLoggedInUser().getId(), expenseTypeId,
				page);
	}

	@Override
	public BigDecimal totalExpenses() {
		List<BigDecimal> expenses = expenseRepository.findByUserId(userService.getLoggedInUser().getId()).stream()
				.map(Expense::getAmount).collect(Collectors.toList());
		return expenses.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public BigDecimal totalTypeExpenses(Long expenseTypeId) {
		List<BigDecimal> expenses = expenseRepository
				.findByUserIdAndExpenseTypeId(userService.getLoggedInUser().getId(), expenseTypeId).stream()
				.map(Expense::getAmount).collect(Collectors.toList());
		return expenses.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
