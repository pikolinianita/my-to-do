
package pl.lcc.todo.entities;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 *
 * @author piko
 */
public class TaskEntity {

     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    
    @NonNull
    String name;
    
    Set<String> tags;
    
    @Nullable
    String reward;
    
    @NonNull
    String icon; 
    
    @NonNull
    LocalTime from;
    
    @NonNull
    LocalTime to;
    
    @Nullable
    String remarks;
    
    Set<EventEntity> events;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaskEntity other = (TaskEntity) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
    
}
