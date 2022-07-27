/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.lcc.todo.entities;

import java.util.Collection;
import java.util.List;

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