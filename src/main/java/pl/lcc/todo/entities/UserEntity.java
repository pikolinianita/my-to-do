package pl.lcc.todo.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NonNull
    String name;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "owner_id")
    Set<ProjectEntity> projects;

    public UserEntity(UserReq name) {
        this.name = name.name();
        projects = new HashSet<>();
    }

    public UserEntity() {
        System.out.println("Constructor for Hibernate - user");
    }

    public UserEntity addProject(ProjectEntity project) {

        projects.add(project);
        project.setOwner(this);
        return this;
    }
    
    public UserEntity removeProject(ProjectEntity project){
        projects.remove(project);
        project.setOwner(null);
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", name=" + name + ", projects=" + projects.size() + '}';
    }

    
    
}
