/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.lcc.todo.db;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.SoftAssertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import pl.lcc.todo.entities.EventReq;
import pl.lcc.todo.entities.ProjectReq;
import pl.lcc.todo.entities.UserReq;

/**
 *
 * @author piko
 */
@DataJpaTest
@Import({RepoService.class, ReposUtils.class})
//@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class ServiceRemoveTests {
    
  @Autowired
    private ProjectRepository PRepo;

    @Autowired
    private TagRepository TRepo;

    @Autowired
    private UserRepository URepo;

    @Autowired
    private RepoService service;

    @Autowired
    private EventRepository ERepo;
    
    @Autowired
    EntityManager em;

    @Autowired
    ReposUtils util;

    @BeforeEach
    void clearDB() {

        URepo.deleteAll();
        PRepo.deleteAll();
        TRepo.deleteAll();
        ERepo.deleteAll();
     
        createDummyData();
    }
    
    void createDummyData(){
        var user = service.createUser(new UserReq("Worker")).orElseThrow();
        System.out.println(user);
        var project1 = service.createProject(user.getId(), new ProjectReq("Program", Set.of(), "It reward", "It icon")).orElseThrow();
        var project2 = service.createProject(user.getId(), new ProjectReq("Read", Set.of(), "Book reward", "Book icon")).orElseThrow();
        System.out.println(project1);
        System.out.println(project2);
        service.createEvent(user.getId(), project1.getId(), new EventReq("Back End",LocalDateTime.now().minusHours(4), LocalDateTime.now().minusHours(3), "Java" ));
        service.createEvent(user.getId(), project1.getId(), new EventReq("Front End",LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2), "JS" ));
        service.createEvent(user.getId(), project1.getId(), new EventReq("Testing",LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1), "success" ));
        service.createEvent(user.getId(), project2.getId(), new EventReq("SICP",LocalDateTime.now().minusHours(6), LocalDateTime.now().minusHours(5), "chapter 5" ));
        service.createEvent(user.getId(), project2.getId(), new EventReq("Cook book",LocalDateTime.now().minusHours(1), LocalDateTime.now(), "yummy" ));
    }
    
    @Test
    void testingSetup(){
        assertThat(URepo.count()).isEqualTo(1);
        assertThat(PRepo.count()).isEqualTo(2);
        assertThat(ERepo.count()).isEqualTo(5);
    }
}
