package com.beratoztas.handler;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.beratoztas.exception.BaseException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Uygulamada Fırlatılan Exception Handler
		@ExceptionHandler(value = { BaseException.class })
		public ResponseEntity<ApiError<?>> handleBaseException(BaseException ex, WebRequest request) {
			return ResponseEntity.badRequest().body(createApiError(ex.getMessage(), request));
		}

		public <E> ApiError<E> createApiError(E message, WebRequest request) {
			ApiError<E> apiError = new ApiError<>();
			apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

			Exception<E> exception = new Exception<>();
			exception.setPath(request.getDescription(false).substring(4));
			exception.setCreateTime(new Date());
			exception.setHostName(getHostName());
			exception.setMessage(message);

			apiError.setException(exception);
			return apiError;

		}

		private String getHostName() {
			try {
				return Inet4Address.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return null;
		}
	
	
}
