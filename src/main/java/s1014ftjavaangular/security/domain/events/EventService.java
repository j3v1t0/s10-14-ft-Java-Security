package s1014ftjavaangular.security.domain.events;

import s1014ftjavaangular.security.domain.model.events.Event;

public interface EventService<T extends Event> {
    Boolean excute(T event);
}
