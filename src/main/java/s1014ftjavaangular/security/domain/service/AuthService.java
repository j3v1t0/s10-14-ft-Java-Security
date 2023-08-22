package s1014ftjavaangular.security.domain.service;

import s1014ftjavaangular.security.infrastructure.security.AccountPrincipal;

public interface AuthService {
    AccountPrincipal authenticate(String email, String credential);
}
