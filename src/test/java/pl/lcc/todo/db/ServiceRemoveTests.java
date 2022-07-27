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
@Transactional(Transactional.TxType.NOT_SUPPORTED)
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
        var unusedUser = service.createUser(new UserReq("Lazy Guy")).orElseThrow();
        System.out.println(user);
        var project1 = service.createProject(user.getId(), new ProjectReq("Program", Set.of(), "It reward", "It icon")).orElseThrow();
        var project2 = service.createProject(user.getId(), new ProjectReq("Read", Set.of(), "Book reward", "Book icon")).orElseThrow();
        var project3 = service.createProject(user.getId(), new ProjectReq("Not Started", Set.of(), "No Rewards", "? icon")).orElseThrow();
        em.flush();
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
        assertThat(URepo.count()).isEqualTo(2);
        assertThat(PRepo.count()).isEqualTo(3);
        assertThat(ERepo.count()).isEqualTo(5);
        util.dumpDB("ServiceRemoveTests1.txt");
    }
    
    @Test
    void removeUnusedUser(){
        SoftAssertions softly = new SoftAssertions();
        var unusedUserId = URepo.findByName("Lazy Guy").orElseThrow().getId();
        service.removeUser(unusedUserId);
        em.flush();
        softly.assertThat(URepo.count()).isEqualTo(1);
        softly.assertThat(PRepo.count()).isEqualTo(3);
        softly.assertThat(ERepo.count()).isEqualTo(5);
        softly.assertAll();
    }
    
    @Test
    void removeActiveUser(){
        SoftAssertions softly = new SoftAssertions();
        var unusedUserId = URepo.findByName("Worker").orElseThrow().getId();
        service.removeUser(unusedUserId);
        em.flush();
        softly.assertThat(URepo.count()).isEqualTo(1);
        softly.assertThat(PRepo.count()).isEqualTo(0);
        softly.assertThat(ERepo.count()).isEqualTo(0);
        softly.assertAll();
    }
    
    @Test
    void removeActiveProject(){
        SoftAssertions softly = new SoftAssertions();
        var UserId = URepo.findByName("Worker").orElseThrow().getId();
        var usedProjectId = PRepo.findByName("Read").orElseThrow().getId();
        service.removeProject(UserId, usedProjectId);
        em.flush();
        //util.dumpDB("ServiceRemoveTests2.txt");
        softly.assertThat(URepo.count()).as("users").isEqualTo(2);
        softly.assertThat(PRepo.count()).as("Projects").isEqualTo(2);
        softly.assertThat(ERepo.count()).as("Events").isEqualTo(3);
        softly.assertAll();
    
    }
    
    @Test
    void removeNotActiveProject(){
        SoftAssertions softly = new SoftAssertions();
        var UserId = URepo.findByName("Worker").orElseThrow().getId();
        var unusedProjectId = PRepo.findByName("Not Started").orElseThrow().getId();
        System.out.println("service run!!!!");
        util.dumpDB("ServiceRemoveTestsBefore.txt");
        service.removeProject(UserId, unusedProjectId);
        em.flush();
        
        System.out.println("service run!!!!");
        util.dumpDB("ServiceRemoveTestsAfter.txt");
        softly.assertThat(URepo.count()).as("users").isEqualTo(2);
        softly.assertThat(PRepo.count()).as("Projects").isEqualTo(2);
        softly.assertThat(ERepo.count()).as("Events").isEqualTo(5);
        softly.assertAll();
    }
    
    @Test
    void getUserTest(){
        var result = service.getUserByName("Worker");
        var result2 = service.getUserByName("Lazy Guy");
        var resultEmpty = service.getUserByName("Not Exist");
        assertThat(resultEmpty).isEmpty();
        assertThat(result).isNotEmpty();
        assertThat(result.get().name()).isEqualTo("Worker");
        assertThat(result.get().projects()).hasSize(3);
        assertThat(result2.get().name()).isEqualTo("Lazy Guy");
        assertThat(result2.get().projects()).hasSize(0);
    }
}
