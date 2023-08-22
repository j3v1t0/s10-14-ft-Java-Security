package s1014ftjavaangular.security.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1014ftjavaangular.security.domain.model.dto.LoginDTO;
import s1014ftjavaangular.security.domain.model.dto.LoginResponse;
import s1014ftjavaangular.security.domain.usecase.LoginUseCase;

@RestController
@RequestMapping("/api/accounts/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginUseCase loginUseCase;

    @PostMapping()
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginDTO loginDto){

        var response = loginUseCase.login(loginDto.getEmail(), loginDto.getPassword());

        return ResponseEntity.ok(response);
    }
}
