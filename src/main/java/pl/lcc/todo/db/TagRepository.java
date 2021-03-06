/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pl.lcc.todo.db;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import pl.lcc.todo.entities.TagEntity;

/**
 *
 * @author piko
 */
public interface TagRepository extends CrudRepository<TagEntity, Long>{
    
    public Optional<TagEntity> findByName(String name);
    
}
