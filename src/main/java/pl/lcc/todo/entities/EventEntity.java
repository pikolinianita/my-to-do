
package pl.lcc.todo.entities;

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
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    
    Set<String> tags;
    
    @NonNull
    LocalTime from;
    
    @NonNull
    LocalTime to;
    
    @Nullable
    String remarks;   
    
}
