package s1014ftjavaangular.security.infrastructure.controller;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1014ftjavaangular.security.domain.usecase.GetCurrentSessionUse;
import s1014ftjavaangular.security.infrastructure.security.AccountPrincipal;

@RestController
@RequestMapping("/api/accounts/current-session")
@RequiredArgsConstructor
public class GetCurrentSessionController {
    private final GetCurrentSessionUse currentSessionUseCase;

    @Retry(name = "securityRetry")
    @GetMapping()
    public ResponseEntity<?> getCurrentSession(@AuthenticationPrincipal AccountPrincipal accountPrincipal){

        var sesion = accountPrincipal.getUsername();

        return ResponseEntity.ok(currentSessionUseCase.getCurrentSession(sesion));
    }
}
