package pl.lcc.todo.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
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

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    //@Cascade()
    Set<TagEntity> tags;

    @Nullable
    String reward;

    @NonNull
    String icon;

    @ManyToOne
    UserEntity owner;
    
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
    mappedBy = "project")
   // @JoinColumn(name = "owner_id")
    Set<EventEntity> events;

    public ProjectEntity addEvent(EventEntity event){
        events.add(event);
        event.setProject(this);
        return this;
    }
    
    public ProjectEntity removeEvent(EventEntity event){
        events.remove(event);
        event.setProject(null);
        return this;
    }    
    
    public ProjectEntity(ProjectReq source) {
        this.name = source.name();
        this.reward = source.reward();
        if (source.icon() == null) {
            this.icon = "default";
        } else {
            this.icon = source.icon();
        }
        events = new HashSet<>();
    }

    protected ProjectEntity() {
        System.out.println("Constructor for Hibernate - project Entity");
    }    
    
    @Override
    public String toString() {
        return "ProjectEntity{" + "id=" + id + ", name=" + name + ", tags=" + tags + ", reward=" + reward + ", icon=" + icon + ", owner= N/A"  + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final ProjectEntity other = (ProjectEntity) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    
    
}
