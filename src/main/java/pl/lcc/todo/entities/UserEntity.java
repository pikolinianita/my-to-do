
package pl.lcc.todo.entities;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 *
 * @author piko
 */
@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    long id;
    
    @NonNull
    String name;
    
    @OneToMany
    Set<ProjectEntity> projects;
    
}
