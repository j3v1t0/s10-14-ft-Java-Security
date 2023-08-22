package s1014ftjavaangular.security.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import s1014ftjavaangular.security.domain.model.dto.ExceptionDTO;
import s1014ftjavaangular.security.domain.exceptions.AccountAlreadyExists;

@RestControllerAdvice
public class GlobalExceptionHandlers {

    //Este metodo responde a excepciones de tipo "Exception", es decir excepciones genericas
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionDTO> handlerExceptions(Exception exception){
        var exceptionDto = ExceptionDTO.builder()
                                .detail(exception.getMessage())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .type(exception.getClass().getTypeName())
                                .build();

        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ExceptionHandler(value = AccountAlreadyExists.class)
    public ResponseEntity<ExceptionDTO> handlerConlictException(AccountAlreadyExists exception){
        var exceptionDto = ExceptionDTO.builder()
                .detail(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .type(exception.getClass().getTypeName())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionDto);
    }
}
