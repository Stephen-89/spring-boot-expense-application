package com.stephen.login.service;

import com.stephen.login.dto.MfaDto;

public interface TotpService {
	
	String generateUriForImage();
	
	Object verifyCode(MfaDto mfaDto);

	void disableMfa();
	
}
