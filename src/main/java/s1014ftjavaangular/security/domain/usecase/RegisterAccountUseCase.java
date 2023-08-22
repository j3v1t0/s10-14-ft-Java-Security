package s1014ftjavaangular.security.domain.usecase;

import s1014ftjavaangular.security.domain.model.dto.RegisterCustomer;
import s1014ftjavaangular.security.domain.model.entities.Account;

public interface RegisterAccountUseCase {
    Account createCustomer(RegisterCustomer registerCustomer);
}
