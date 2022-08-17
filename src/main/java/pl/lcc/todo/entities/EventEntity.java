package pl.lcc.todo.entities;


import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 *
 * @author piko
 */
@Entity
@Getter
@ToString
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NonNull
    String name;

    @NonNull
    LocalDateTime timeFrom;

    @NonNull
    LocalDateTime timeTo;

    @Nullable
    String remarks;
    
    @ManyToOne
    ProjectEntity project;

    public EventEntity setProject(ProjectEntity project){
        this.project = project;
        return this;
    }
    
    protected EventEntity() {
        //for Hibernate
    }
    
    public EventEntity(EventReq req) {
        this.name = req.name();
        this.remarks = req.remarks();
        this.timeFrom = req.from();
        this.timeTo = req.to();
    }

}
