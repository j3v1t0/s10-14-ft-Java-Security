package s1014ftjavaangular.security.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import s1014ftjavaangular.security.domain.repository.AccountRepositoryPort;
import s1014ftjavaangular.security.domain.utils.SecurityUtils;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomAccountDetailsService {
    @Autowired
    private AccountRepositoryPort repository;

    @Bean
    public UserDetailsService userDetailsService() throws RuntimeException {
        return email -> Optional.of(
                repository.findByEmail(email).get()
        ).map(account -> AccountPrincipal.builder()
                .account(account)
                .id(account.getAccountUuid())
                .user(account.getEmail())
                .password(account.getPassword())
                .authorities(Set.of(SecurityUtils.convertToAuthority(account.getRol().name())))
                .build()
        ).get();
    }
}
