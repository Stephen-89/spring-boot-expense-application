package com.stephen.expense.service;

import com.stephen.expense.dto.MfaDto;

public interface TotpService {
	
	String generateUriForImage();
	
	Object verifyCode(MfaDto mfaDto);

	void disableMfa();
	
}
