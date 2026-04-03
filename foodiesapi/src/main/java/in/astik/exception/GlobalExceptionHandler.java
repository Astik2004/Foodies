package in.astik.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<ApiError> handleFileStorageException(FileStorageException ex){
		ApiError apiError = ApiError.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error("File Storage Error")
				.message(ex.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	@ExceptionHandler({FoodNotFoundException.class, ResourceNotFoundException.class})
	public ResponseEntity<ApiError> handleNotFoundException(RuntimeException ex){
		ApiError apiError = ApiError.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.error("Not Found")
				.message(ex.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiError> handleDuplicateResourceException(DuplicateResourceException ex){
		ApiError apiError = ApiError.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.CONFLICT.value())
				.error("Conflict")
				.message(ex.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGlobalException(Exception ex){
		ApiError apiError = ApiError.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error("Server Error")
				.message(ex.getMessage())
				.build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
	}
}
