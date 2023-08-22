package s1014ftjavaangular.security.domain.usecase;

import s1014ftjavaangular.security.domain.model.dto.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(final String email, final String password);
}
