
package pl.lcc.todo.entities;

import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author piko
 */
public record ProjectDTO(String name, Set<String> tags, String icon, String reward ) {
    
    public ProjectDTO(ProjectEntity s){
        this(s.getName(), s.getTags().stream().map(TagEntity::getName).collect(Collectors.toSet()), s.getIcon(), s.getReward());
    }
}
