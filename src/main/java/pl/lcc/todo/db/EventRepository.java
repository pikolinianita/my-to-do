/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pl.lcc.todo.db;

import org.springframework.data.repository.CrudRepository;
import pl.lcc.todo.entities.EventEntity;

/**
 *
 * @author piko
 */
public interface EventRepository extends CrudRepository<EventEntity, Long>{
    
}
