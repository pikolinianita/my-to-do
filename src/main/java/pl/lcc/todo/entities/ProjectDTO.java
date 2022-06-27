
package pl.lcc.todo.entities;

import java.util.Set;

/**
 *
 * @author piko
 */
public record ProjectDTO(String name, Set<String> tags, String icon, String reward ) {
    
    public ProjectDTO(ProjectEntity s){
       this(s.getName(), s.getTags(), s.getIcon(), s.getReward());
    }
}
