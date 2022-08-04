<<<<<<< HEAD

=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
>>>>>>> 168b214f8d2eb9d7205a7823b4a1a801cadfb428
package pl.lcc.todo.entities;

import java.util.Collection;
import java.util.List;

<<<<<<< HEAD

/**
 *
 * @author piko
 */
public record UserDTO (long id, String name, List<ProjectStub> projects){

     public UserDTO(UserEntity user){
        this(user.getId(), user.getName(), user.getProjects().stream().map(entity -> new ProjectStub(entity.getId(), entity.getName())).toList());
    }
}

record ProjectStub(long id, String name){}
=======
/**
 *
 * @author Nauczyciel
 */
public record UserDTO (String name,long id, List<ProjectStub> projects) {

    public UserDTO(String name, long id, Collection<ProjectEntity> projects) {
        this(name, id, projects.stream().map(ProjectStub::new).toList());
    }
}

record ProjectStub (String name, long id){

    public ProjectStub(ProjectEntity project) {
        this(project.getName(), project.getId());
    }

}
>>>>>>> 168b214f8d2eb9d7205a7823b4a1a801cadfb428
