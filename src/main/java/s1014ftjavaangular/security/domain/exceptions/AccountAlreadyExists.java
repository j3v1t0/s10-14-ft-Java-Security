package s1014ftjavaangular.security.domain.exceptions;

public class AccountAlreadyExists extends RuntimeException{

    public AccountAlreadyExists(String message){
        super(message);
    }
}
