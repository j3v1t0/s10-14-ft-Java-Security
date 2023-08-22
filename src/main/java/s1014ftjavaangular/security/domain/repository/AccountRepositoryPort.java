package s1014ftjavaangular.security.domain.repository;

import s1014ftjavaangular.security.domain.enums.Rol;
import s1014ftjavaangular.security.domain.model.entities.Account;

import java.util.Optional;

public interface AccountRepositoryPort {
    Optional<Account> findByEmail(final String email);
    Account registerAccount(final String email, final String password, final Rol rol);
}
