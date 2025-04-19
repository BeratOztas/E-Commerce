package com.beratoztas.utils;

import java.time.Duration;

import org.springframework.http.ResponseCookie;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CookieUtil {
	public ResponseCookie createAccessTokenCookie(String accessToken) {
        return ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(false) // HTTPS için Yayın ortamına geçtiğinde .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Lax") // Yayın ortamına geçtiğinde .sameSite("Strict")
                .build();
    }

    public ResponseCookie clearAccessTokenCookie() {
        return ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
    }
}
