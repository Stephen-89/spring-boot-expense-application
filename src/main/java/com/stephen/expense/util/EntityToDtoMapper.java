package com.stephen.expense.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.stephen.expense.dto.ExpenseTypesTotalsDto;

public class EntityToDtoMapper {

	public static List<ExpenseTypesTotalsDto> convertToExpenseTypesTotalsDto(List<Object[]> resultList) {
		List<ExpenseTypesTotalsDto> dtos = new ArrayList<>();
        for(Object[] result: resultList) {
        	ExpenseTypesTotalsDto dto = new ExpenseTypesTotalsDto();
        	dto.setName((String)result[0]);
        	dto.setTotal((BigDecimal)result[1]);
        	dtos.add(dto);
        }
		return dtos;
    }
	
}
