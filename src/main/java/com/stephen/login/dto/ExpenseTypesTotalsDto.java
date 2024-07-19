package com.stephen.login.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ExpenseTypesTotalsDto {
	
	private String name;
	private BigDecimal total;

}
