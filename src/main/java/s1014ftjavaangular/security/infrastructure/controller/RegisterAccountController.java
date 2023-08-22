package s1014ftjavaangular.security.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1014ftjavaangular.security.domain.model.dto.RegisterCustomer;
import s1014ftjavaangular.security.domain.usecase.RegisterAccountUseCase;
@Slf4j
@RestController
@RequestMapping("/api/accounts/register")
@RequiredArgsConstructor
public class RegisterAccountController {
    private final RegisterAccountUseCase registerAccountUseCase;

    @PostMapping
    public ResponseEntity<?> registerAccount(@RequestBody @Valid RegisterCustomer registerCustomer){

        var savedAccount = registerAccountUseCase.createCustomer(registerCustomer);
        
        return ResponseEntity.ok().build();
    }
}
