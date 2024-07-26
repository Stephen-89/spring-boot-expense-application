package com.stephen.expense.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stephen.expense.entity.ExpenseType;
import com.stephen.expense.repository.ExpenseTypeRepository;

@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

	@Autowired
	EntityManager em;

	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;

	@Override
	public List<ExpenseType> getAllExpenseTypes() {
		return expenseTypeRepository.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllExpenseTotalsByTypes() {
		try {
			return em.createNativeQuery("SELECT et.expense_type_name AS name, SUM(e.expense_amount) AS total "
					+ "FROM expenses_tracker.expense e JOIN expenses_tracker.expense_type et ON e.expense_type_id = et.id GROUP BY et.id")
					.getResultList();
		} catch (Exception e) {
			// If an exception is thrown in this try block, you are ignoring it.
		}
		return null;
	}

}