package pl.lcc.todo.entities;

import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 *
 * @author piko
 */
@Getter
@Entity
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NonNull
    String name;

    @Nullable
    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "tag")
    Set<String> tags;
   
    @Nullable
    String reward;

    @NonNull
    String icon; 

    public ProjectEntity(ProjectReq source) {
        this.name = source.name();
        this.reward = source.reward();
        if (source.icon() == null) {
            this.icon = "default";
        } else {
            this.icon = source.icon();
        }
        this.tags = source.tags();

    }

    protected ProjectEntity() {
        System.out.println("Constructor for Hibernate - project");
    }

    @Override
    public String toString() {
        return "ProjectEntity: {" + "id=" + id + ", name=" + name + ", tags=" + tags + ", reward=" + reward + ", icon=" + icon + '}';
    }
    
    

}
