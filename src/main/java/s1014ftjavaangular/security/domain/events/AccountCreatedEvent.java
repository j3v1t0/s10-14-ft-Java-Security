package s1014ftjavaangular.security.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import s1014ftjavaangular.security.domain.model.events.AccountCreatedDTO;
import s1014ftjavaangular.security.infrastructure.message.TransactionMessagePublish;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountCreatedEvent implements EventService<AccountCreatedDTO>{
    private final TransactionMessagePublish publisher;

    @Override
    public Boolean excute(AccountCreatedDTO event) {
        try {
            log.info("Post: AccountId {} - Name {} - Lastname {} - Type {}", event.getAccountUuid(),
                    event.getName(), event.getLastname(), event.getAccountRol());
            publisher.sendAccountEvent(event);

            log.info("Publique el mensaje");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
