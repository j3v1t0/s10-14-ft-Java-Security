package s1014ftjavaangular.security.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import s1014ftjavaangular.security.domain.service.AuthService;


@Service
@RequiredArgsConstructor
public class SpringAuthService implements AuthService {
    private final AuthenticationManager authenticationManager;
    //private final UserDetailsService service;

    @Override
    public AccountPrincipal authenticate(String email, String credential) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, credential)
        );

        return (AccountPrincipal) authentication.getPrincipal();
    }
}
