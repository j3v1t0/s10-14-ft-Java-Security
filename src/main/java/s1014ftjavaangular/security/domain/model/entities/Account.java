package s1014ftjavaangular.security.domain.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import s1014ftjavaangular.security.domain.enums.Rol;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Account {
    private String accountUuid;
    private String email;
    private String password;
    private LocalDate createAt;
    private LocalDate lastSession;
    private Rol rol;
}
