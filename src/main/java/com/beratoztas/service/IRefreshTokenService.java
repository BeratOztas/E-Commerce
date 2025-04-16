package com.beratoztas.service;

public interface IRefreshTokenService {

	public Long getUserIdFromRefreshToken(String refreshToken);
	
	public void saveRefreshToken(Long userId,String refreshToken);
	
	public void deleteRefreshToken(String refreshToken);
	
	public boolean isValidRefreshToken(String refreshToken);
}
