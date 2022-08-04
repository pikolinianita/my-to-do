
package pl.lcc.todo.entities;

import java.time.LocalDateTime;

/**
 *
 * @author piko
 */
public record EventDTO(long id, String name, LocalDateTime startTime, LocalDateTime endDate, String remarks) {

    public EventDTO (EventEntity event){
        this(event.getId(), event.getName(), event.getTimeFrom(), event.getTimeTo(), event.getRemarks());
        
    }
    
}
