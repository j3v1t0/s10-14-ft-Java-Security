package s1014ftjavaangular.security.infrastructure.persistence.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import s1014ftjavaangular.security.domain.enums.Rol;
import s1014ftjavaangular.security.domain.exceptions.AccountAlreadyExists;
import s1014ftjavaangular.security.domain.model.entities.Account;
import s1014ftjavaangular.security.domain.model.events.AccountCreatedDTO;
import s1014ftjavaangular.security.domain.repository.AccountRepositoryPort;
import s1014ftjavaangular.security.infrastructure.message.TransactionMessagePublish;
import s1014ftjavaangular.security.infrastructure.persistence.entities.AccountEntity;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountJpaRepository jpaRepository;

    private final PasswordEncoder passwordEncoder;

    private final TransactionMessagePublish transactionMessagePublish;

    private final Function<AccountEntity, Account> entityToModel = (entity)-> new Account(
            entity.getAccountUuid(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getCreatedAt(),
            entity.getLastSession(),
            entity.getRol()
        );


    @Override
    public Optional<Account> findByEmail(String email) {
            if(!StringUtils.hasText(email)){
                throw new IllegalArgumentException("Email cannot be empty");
            }
            var account = jpaRepository.findByEmail(email);

            return account.isEmpty() ? Optional.empty() : account.map(entityToModel::apply);
    }

    @Override
    public Account registerAccount(String email, String password, Rol rol) {
        log.info("ENTRE: {}, {}. {}", email, password, rol);
        //Validar que el email no este registrado
        if (this.findByEmail(email).isPresent()){
             throw new AccountAlreadyExists("This account already exists");
        }

        String encryptPassword = passwordEncoder.encode(password);
        //Se guarda en la base de datos
        var account = jpaRepository.save(new AccountEntity(email, encryptPassword, rol));

        return entityToModel.apply(account);
    }
}
