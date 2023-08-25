package s1014ftjavaangular.security.infrastructure.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1014ftjavaangular.security.domain.model.dto.LoginDTO;
import s1014ftjavaangular.security.domain.model.dto.LoginResponse;
import s1014ftjavaangular.security.domain.usecase.LoginUseCase;
@Slf4j
@RestController
@RequestMapping("/api/accounts/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginUseCase loginUseCase;
    //@CircuitBreaker(name = "mysqlCR", fallbackMethod = "fallBackLogin")
    @PostMapping()
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginDTO loginDto){
        var response = loginUseCase.login(loginDto.getEmail(), loginDto.getPassword());

        return ResponseEntity.ok(response);
    }

/*    public ResponseEntity<?> fallBackLogin(@RequestBody @Valid LoginDTO loginDto, RuntimeException exception){
        var response = ResponseEntity.ok("At the moment there is a problem in the login");
        log.error("Exception = {}", exception.getMessage());
        return response;
    }*/
}
