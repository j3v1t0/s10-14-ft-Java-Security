package s1014ftjavaangular.security.domain.events;


public interface EventMessageSender<T> {
    Boolean sendMessage(T messageModel);
}
