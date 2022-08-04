package pl.lcc.todo.entities;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author piko
 */
public record ProjectDTO(String name, Set<String> tags, List<EventStub> events, String icon, String reward) {

    public ProjectDTO(ProjectEntity s) {
        this(s.getName(),
                s.getTags().stream().map(TagEntity::getName).collect(Collectors.toSet()),
                s.getEvents().stream().map(event -> new EventStub(event.getId(), event.getName())).toList(),
                s.getIcon(), s.getReward());
    }
}

record EventStub(long id, String name) {};
