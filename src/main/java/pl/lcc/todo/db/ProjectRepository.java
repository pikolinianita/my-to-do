/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pl.lcc.todo.db;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import pl.lcc.todo.entities.ProjectEntity;
import pl.lcc.todo.entities.UserEntity;

/**
 *
 * @author piko
 */
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

    public Optional<ProjectEntity> findByName(String name);    
   
    public List<ProjectEntity> findByOwnerAndName(UserEntity user, String name);
 
    public Optional<ProjectEntity> findByOwner_IdAndName(long userId, String name);
    
    public Optional<ProjectEntity> findByOwner_IdAndId(long userId, long id);
}
