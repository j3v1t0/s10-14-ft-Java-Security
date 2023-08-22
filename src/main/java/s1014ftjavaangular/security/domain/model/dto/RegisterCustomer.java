package s1014ftjavaangular.security.domain.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Data
public class RegisterCustomer {
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email cannot be empty")
    @Length(max = 128, min = 5, message = "5 Minimun and 128 Maximum Characters")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Length(max = 128, min = 8, message = "8 Minimun and 128 Maximum Characters")
    private String password;
    @NotEmpty(message = "Password cannot be empty")
    private String name;
    @NotEmpty(message = "Password cannot be empty")
    private String lastname;
}
