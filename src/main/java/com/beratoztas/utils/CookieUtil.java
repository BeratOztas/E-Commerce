package com.beratoztas.utils;

import java.time.Duration;

import org.springframework.http.ResponseCookie;

public class CookieUtil {
	public static ResponseCookie createAccessTokenCookie(String accessToken) {
        return ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(false) // HTTPS için Yayın ortamına geçtiğinde .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Lax") // Yayın ortamına geçtiğinde .sameSite("Strict") 
                .build();
    }

    public static ResponseCookie clearAccessTokenCookie() {
        return ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
    }
}
