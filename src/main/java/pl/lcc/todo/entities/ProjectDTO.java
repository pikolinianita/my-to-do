package pl.lcc.todo.entities;

import java.util.List;


/**
 *
 * @author piko
 */
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
