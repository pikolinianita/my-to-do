
package pl.lcc.todo.entities;

import java.util.Collection;
import java.util.List;


/**
 *
 * @author piko
 */
public record UserDTO (long id, String name, List<ProjectStub> projects){

     public UserDTO(UserEntity user){
        this(user.getId(), user.getName(), user.getProjects().stream().map(entity -> new ProjectStub(entity.getId(), entity.getName())).toList());
    }
}

record ProjectStub(long id, String name){}