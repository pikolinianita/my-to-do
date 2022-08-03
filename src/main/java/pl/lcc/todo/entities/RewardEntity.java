
package pl.lcc.todo.entities;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NaturalId;
import org.springframework.lang.NonNull;

/**
 *
 * @author piko
 */

@Slf4j
@Entity
@Getter
@ToString
public class RewardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NonNull
    @NaturalId
    String name;

    public RewardEntity(String name) {
        this.name = name;
    }

    protected RewardEntity() {
        log.info("Constructor for Hibernate - tag");
    }
    
     @Override
    public int hashCode() {
       
        return name.hashCode();
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
        final RewardEntity other = (RewardEntity) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    
    
}
