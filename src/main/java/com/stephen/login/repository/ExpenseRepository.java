package com.stephen.login.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stephen.login.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	Page<Expense> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);
	
	Page<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);
	
	Page<Expense> findByUserId(Long userId, Pageable page);
	
	Optional<Expense> findByUserIdAndId(Long userId, Long expenseId);

	Page<Expense> findByUserIdAndExpenseTypeId(Long userId, Long expenseTypeId, Pageable page);

	List<Expense> findByUserId(Long userId);

	List<Expense> findByUserIdAndExpenseTypeId(Long userId, Long expenseTypeId);

}
