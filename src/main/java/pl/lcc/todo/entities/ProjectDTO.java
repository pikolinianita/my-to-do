package pl.lcc.todo.entities;

import java.util.List;
<<<<<<< HEAD
import java.util.Set;
import java.util.stream.Collectors;
=======

>>>>>>> 168b214f8d2eb9d7205a7823b4a1a801cadfb428

/**
 *
 * @author piko
 */
<<<<<<< HEAD
public record ProjectDTO(String name, Set<String> tags, List<EventStub> events, String icon, String reward) {

    public ProjectDTO(ProjectEntity s) {
        this(s.getName(),
                s.getTags().stream().map(TagEntity::getName).collect(Collectors.toSet()),
                s.getEvents().stream().map(event -> new EventStub(event.getId(), event.getName())).toList(),
                s.getIcon(), s.getReward());
    }
}

record EventStub(long id, String name) {}
=======
public record ProjectDTO(String name, String icon, String reward, List<String> tags, List<EventStub> events) {

    public ProjectDTO(ProjectEntity s) {
        this(s.getName(), s.getIcon(), s.getReward(),
                s.getTags().stream().map(TagEntity::getName).toList(),
                s.getEvents().stream().map(EventStub::new).toList());
    }
}

record EventStub(String name, long Id) {
    public EventStub(EventEntity event) {
        this(event.getName(), event.getId());
    }

}
>>>>>>> 168b214f8d2eb9d7205a7823b4a1a801cadfb428
