package com.beratoztas.service.impl;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.beratoztas.exception.BaseException;
import com.beratoztas.exception.ErrorMessage;
import com.beratoztas.exception.MessageType;
import com.beratoztas.service.IRefreshTokenService;

import io.micrometer.common.util.StringUtils;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

	private RedisTemplate<String, String> redisTemplate;

	private static final String PREFIX = "refresh_token:";

	@Value("${e-commerce.app.refresh.token.expires.in}")
	private Long REFRESH_TOKEN_EXPIRES_MILISECONDS;

	public RefreshTokenServiceImpl(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Long getUserIdFromRefreshToken(String refreshToken) {
		validateRefreshTokenInput(refreshToken);
		String userId = redisTemplate.opsForValue().get(PREFIX + refreshToken);
		if (userId == null) {
			throw new BaseException(new ErrorMessage(MessageType.INVALID_REFRESH_TOKEN, "Refresh token not found."));
		}
		try {
			return Long.parseLong(userId);
		} catch (NumberFormatException e) {
			throw new BaseException(new ErrorMessage(MessageType.INVALID_REFRESH_TOKEN, "Stored userId is not valid."));
		}
	}

	@Override
	public void saveRefreshToken(Long userId, String refreshToken) {
		validateRefreshTokenInput(refreshToken);
		String key = PREFIX + refreshToken;
		redisTemplate.opsForValue().set(key, userId.toString(), Duration.ofMillis(REFRESH_TOKEN_EXPIRES_MILISECONDS));
	}

	@Override
	public void deleteRefreshToken(String refreshToken) {
		validateRefreshTokenInput(refreshToken);
		redisTemplate.delete(PREFIX + refreshToken);
	}

	@Override
	public boolean isValidRefreshToken(String refreshToken) {
		validateRefreshTokenInput(refreshToken);
		String userId = redisTemplate.opsForValue().get(PREFIX + refreshToken);
		if (userId == null) {
			throw new BaseException(new ErrorMessage(MessageType.INVALID_REFRESH_TOKEN, "Refresh token not found."));
		}
		String key = PREFIX + refreshToken;
		Boolean exists = redisTemplate.hasKey(key);
		if (exists == null || !exists) {
			throw new BaseException(
					new ErrorMessage(MessageType.INVALID_REFRESH_TOKEN, "Refresh token is invalid or expired."));
		}
		return true;

	}

	public String generateRefreshToken() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] randomBytes = new byte[64];
		secureRandom.nextBytes(randomBytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
	}

	private void validateRefreshTokenInput(String token) {
		if (StringUtils.isBlank(token)) {
			throw new BaseException(new ErrorMessage(MessageType.INVALID_REFRESH_TOKEN, "Refresh token is blank."));
		}
	}

}
