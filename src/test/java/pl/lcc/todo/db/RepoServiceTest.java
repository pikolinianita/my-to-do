/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.lcc.todo.db;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import pl.lcc.todo.entities.ProjectEntity;
import pl.lcc.todo.entities.ProjectReq;
import pl.lcc.todo.entities.UserReq;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import pl.lcc.todo.entities.EventEntity;
import pl.lcc.todo.entities.EventReq;

/**
 *
 * @author piko
 */
@DataJpaTest
@Import({RepoService.class, ReposUtils.class})
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class RepoServiceTest {

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
        
    }

    @Test
    public void testInsertTwice() {
        SoftAssertions softly = new SoftAssertions();

        service.createUser(new UserReq("Worker"));
        var user = service.findUser("Worker").get();

        var input = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");

        var trueResult = service.createProject(user.getId(), input);
        em.flush();
        System.out.println("-------------true------------" + trueResult.get());

        var falseResult = service.createProject(user.getId(), input);
        em.flush();
        //  System.out.println("-------------false------------" + falseResult.get());

        softly.assertThat(trueResult).as("Created").isNotEmpty();
        softly.assertThat(falseResult).as("Not created, Exists").isEmpty();

        softly.assertAll();
    }

    @Test
    public void testInsertTwoDifferent() {
        service.createUser(new UserReq("Worker"));
        var user = service.findUser("Worker").get();

        SoftAssertions softly = new SoftAssertions();
        var input1 = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
        var input2 = new ProjectReq("Sec Project", Set.of("tag 1a", "tag 2a", "tag 4a"), "some reward 2", "some icon 2");

        var trueResult = service.createProject(user.getId(), input1);

        var secondResult = service.createProject(user.getId(), input2);

        softly.assertThat(trueResult).as("Created").isNotEmpty();
        softly.assertThat(secondResult).as("Created").isNotEmpty();
        softly.assertThat(PRepo.count()).as("2 should be").isEqualTo(2);
        softly.assertThat(TRepo.count()).as("5 should be").isEqualTo(5);

        softly.assertAll();
    }

    @Test
    public void testInsertDifferentWithSameTags() {
        service.createUser(new UserReq("Worker"));
        var user = service.findUser("Worker").get();

        SoftAssertions softly = new SoftAssertions();
        var input1 = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
        var input2 = new ProjectReq("Sec Project", Set.of("tag 1", "tag 2", "tag 4a"), "some reward 2", "some icon 2");

        var trueResult = service.createProject(user.getId(), input1);

        var secondResult = service.createProject(user.getId(), input2);

        softly.assertThat(trueResult).as("Created").isNotEmpty();
        softly.assertThat(secondResult).as("Created").isNotEmpty();
        softly.assertThat(PRepo.count()).as("2 should be").isEqualTo(2);
        softly.assertThat(TRepo.count()).as("3 should be").isEqualTo(3);

        softly.assertAll();
    }

    @Test
    public void testCreateUsers() {
        var user1 = new UserReq("Worker");
        var user2 = new UserReq("Shmorker");

        SoftAssertions softly = new SoftAssertions();

        var resultOK = service.createUser(user2);
        var resultOK2 = service.createUser(user1);

        softly.assertThat(resultOK.get()).hasFieldOrPropertyWithValue("name", "Shmorker");
        softly.assertThat(resultOK2.get()).hasFieldOrPropertyWithValue("name", "Worker");

        var resultFail = service.createUser(user2);

        softly.assertThat(resultFail).isEmpty();

        var correctEntity = service.findUser("Worker");
        var incorrectEntity = service.findUser("Jenny");

        softly.assertThat(correctEntity.get()).isEqualTo(URepo.findByName("Worker").get());
        softly.assertThat(incorrectEntity).isEmpty();

        var resultList = em.createNativeQuery("SCRIPT TO 'backup.dmp'").getResultList();
        System.out.println(resultList.toString());

        softly.assertAll();

    }

    @Test
    public void testCreateUsersAndProjects() {
        System.out.println("-----------------#######!!!!########----------------");
        System.out.println("UR: " + URepo.count());
        System.out.println("PR: " + PRepo.count());
        System.out.println("--------------##########!!!!!!!!!#########---------------");

        var user1 = new UserReq("Worker");

        var project1 = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
        var project2 = new ProjectReq("Sec Project", Set.of("tag 1a", "tag 2a", "tag 4a"), "some reward 2", "some icon 2");

        SoftAssertions softly = new SoftAssertions();

        var user = service.createUser(user1).orElseThrow();

        user.addProject(new ProjectEntity(project1)).addProject(new ProjectEntity(project2));
        URepo.save(user);

        //softly.assertThat(resultOK2).isTrue();
        System.out.println("-------------------Flush--------");

        em.flush();

        System.out.println("--------------###################---------------");
        System.out.println("UR: " + URepo.count());
        System.out.println("PR: " + PRepo.count());
        System.out.println("--------------###################---------------");

        

        softly.assertAll();
    }

    @Test
    void testRepoByNameAndId() {
        var user1 = new UserReq("Worker");
        var project1 = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
        service.createUser(user1);
        var user = URepo.findAll().iterator().next();
        user.addProject(new ProjectEntity(project1));
        URepo.save(user);

        System.out.println("#################################");
        System.out.println(PRepo.findByOwnerAndName(user, "First Project"));
        assertThat(PRepo.findByOwnerAndName(user, "First Project")).isNotEmpty();
        System.out.println("#################################");
        System.out.println(PRepo.findByOwnerAndName(user, "Second Project"));
        assertThat(PRepo.findByOwnerAndName(user, "Second Project")).isEmpty();
        System.out.println("#################################");
        System.out.println(PRepo.findByOwner_IdAndName(user.getId(), "First Project"));
        assertThat(PRepo.findByOwner_IdAndName(user.getId(), "First Project")).isNotEmpty();
    }
    
    @Test
    void addEventToProjectWithUser(){
        SoftAssertions softly = new SoftAssertions();
        var user1 = new UserReq("Worker");
        var project1 = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
        var event1 = new EventReq("some Event",LocalDateTime.now(), LocalDateTime.now().minusMinutes(30), "Comment" );

        var user = service.createUser(user1).orElseThrow();//.addProject(project);
        URepo.save(user);
        var project = service.createProject(user.getId(), project1).orElseThrow();        
        em.flush();

        service.createEvent(user.getId(), project.getId(), event1);
        
        softly.assertThat(ERepo.count()).isEqualTo(1);
        softly.assertThat(service.getProjectByName(user.getId(), project.getName()).get().getEvents().size()).isEqualTo(1);
        softly.assertThat(ERepo.findAll().iterator().next().getProject() ).isNotNull();
        softly.assertAll();
    }
    
    @Test
    void addManyEventsToProjectWithUser(){
        SoftAssertions softly = new SoftAssertions();
        var user1 = new UserReq("Worker");
        var project1 = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
        var project2 = new ProjectReq("Sec Project", Set.of("tag 1a", "tag 2a", "tag 4a"), "some reward 2", "some icon 2");
        var user = service.createUser(user1).orElseThrow();
        user.addProject(new ProjectEntity(project1)).addProject(new ProjectEntity(project2));
        URepo.save(user);
        
        softly.assertAll();
    }
}
