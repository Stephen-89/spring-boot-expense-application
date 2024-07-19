package com.stephen.comparator;

import java.util.Comparator;

import com.stephen.login.entity.Expense;
import com.stephen.login.entity.User;

public class Comparators {

	// USER
	public static final Comparator<User> FIRST_NAME = (User o1, User o2) -> o1.getFirstName()
			.compareTo(o2.getFirstName());
	public static final Comparator<User> SECOND_NAME = (User o1, User o2) -> o1.getSecondName()
			.compareTo(o2.getSecondName());
	public static final Comparator<User> USER_NAME = (User o1, User o2) -> o1.getUsername().compareTo(o2.getUsername());

	// Expense
	public static final Comparator<Expense> EXPENSE_NAME = (Expense o1, Expense o2) -> o1.getName()
			.compareTo(o2.getName());
	public static final Comparator<Expense> EXPENSE_TYPES = (Expense o1, Expense o2) -> Long
			.compare(o1.getExpenseType().getId(), o2.getExpenseType().getId());

}