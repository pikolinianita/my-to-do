/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pl.lcc.todo.db;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import pl.lcc.todo.entities.RewardEntity;

/**
 *
 * @author piko
 */
public interface RewardRepository extends CrudRepository<RewardEntity, Long>{

    @Override
    List<RewardEntity> findAll();

    Optional<RewardEntity> findByName(String name);
}
