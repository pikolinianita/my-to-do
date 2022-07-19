
package pl.lcc.todo.entities;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.lang.NonNull;

/**
 *
 * @author piko
 */

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
        System.out.println("Constructor for Hibernate - tag");
    }
    
     @Override
    public int hashCode() {
       
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        return Objects.nonNull(obj) && name.equals(obj);

    }
    
}
