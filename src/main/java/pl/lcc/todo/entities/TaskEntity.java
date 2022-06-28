
package pl.lcc.todo.entities;

import java.time.Duration;
import java.time.LocalTime;
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
    
}
