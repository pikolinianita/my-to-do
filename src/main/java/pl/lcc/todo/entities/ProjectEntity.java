package pl.lcc.todo.entities;

import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 *
 * @author piko
 */
@Getter
@Setter
@Entity
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NonNull
    String name;

//    @Nullable
//    @ElementCollection
//    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "id"))
//    @Column(name = "tag")
//    Set<String> tags;
    
    @ManyToMany
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    Set<TagEntity> tags;
   
    @Nullable
    String reward;

    @NonNull
    String icon; 
    
    @ManyToOne
    UserEntity owner;

    public ProjectEntity(ProjectReq source) {
        this.name = source.name();
        this.reward = source.reward();
        if (source.icon() == null) {
            this.icon = "default";
        } else {
            this.icon = source.icon();
        }

    }

    protected ProjectEntity() {
        System.out.println("Constructor for Hibernate - project Entity");
    }

    @Override
    public String toString() {
        return "ProjectEntity: {" + "id=" + id + ", name=" + name + ", tags=" + tags + ", reward=" + reward + ", icon=" + icon + '}';
    }
    
    

}
