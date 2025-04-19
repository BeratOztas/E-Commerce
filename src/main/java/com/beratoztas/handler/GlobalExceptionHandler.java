package com.beratoztas.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

		//Validation Exception Handler
		@ExceptionHandler(value = { MethodArgumentNotValidException.class })
		public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
			Map<String, List<String>> errorsMap = new HashMap<>();

			for (ObjectError objError : ex.getBindingResult().getAllErrors()) {
				String fieldName = ((FieldError) objError).getField();
				String message = objError.getDefaultMessage();
				if (errorsMap.containsKey(fieldName)) {
					errorsMap.get(fieldName).add(message);
				} else {
					errorsMap.put(fieldName, new ArrayList<>(Arrays.asList(message)));
				}
			}

			return ResponseEntity.badRequest().body(createApiError(errorsMap, request));
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
				return InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return null;
		}


}
