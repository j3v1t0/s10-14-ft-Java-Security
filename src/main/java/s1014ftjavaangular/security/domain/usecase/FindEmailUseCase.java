package s1014ftjavaangular.security.domain.usecase;

import s1014ftjavaangular.security.domain.model.entities.Account;

public interface FindEmailUseCase {
    Account findEmail(String email);
}
