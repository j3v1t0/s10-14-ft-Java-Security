package s1014ftjavaangular.security.domain.usecase;


import s1014ftjavaangular.security.domain.model.dto.LoginResponse;

public interface GetCurrentSessionUse{
    LoginResponse getCurrentSession(String email);
}
