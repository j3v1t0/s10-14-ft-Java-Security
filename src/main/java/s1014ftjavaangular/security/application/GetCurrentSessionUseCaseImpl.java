package s1014ftjavaangular.security.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import s1014ftjavaangular.security.domain.enums.Rol;
import s1014ftjavaangular.security.domain.model.dto.LoginDTO;
import s1014ftjavaangular.security.domain.model.dto.LoginResponse;
import s1014ftjavaangular.security.domain.model.entities.Account;
import s1014ftjavaangular.security.domain.repository.AccountRepositoryPort;
import s1014ftjavaangular.security.domain.service.JwtProvider;
import s1014ftjavaangular.security.domain.usecase.GetCurrentSessionUse;

@Component
@RequiredArgsConstructor
public class GetCurrentSessionUseCaseImpl implements GetCurrentSessionUse {

    private final UserDetailsService service;
    //Aca no especifico el Generic
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponse getCurrentSession(String email) {

        var account = service.loadUserByUsername(email);

        //Esto explota
        String token = jwtProvider.generateToken(account);
        //ROLE_CUSTOMER y no CUSTOMER, se utilizo substring
        return LoginResponse.builder()
                .email(email)
                .token(token)
                .rol(Rol.valueOf(account.getAuthorities().stream().findFirst().get().getAuthority().substring(5)))
                .build();
    }
}
