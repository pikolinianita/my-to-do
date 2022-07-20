/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.lcc.todo.db;

import java.util.Set;
import javax.persistence.EntityManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import pl.lcc.todo.entities.ProjectEntity;
import pl.lcc.todo.entities.ProjectReq;
import pl.lcc.todo.entities.UserEntity;
import pl.lcc.todo.entities.UserReq;

/**
 *
 * @author piko
 */
@DataJpaTest
@Import({RepoService.class,ReposUtils.class})
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
    EntityManager em;
    
   @Autowired
   ReposUtils util;

    @Test
    public void testInsertTwice() {

        service.createUser(new UserReq("Worker"));
        var userId = service.findUserID("Worker").get();

        SoftAssertions softly = new SoftAssertions();
        var input = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");

        var trueResult = service.createProject(userId, input);

        var falseResult = service.createProject(userId, input);

        softly.assertThat(trueResult).as("Created").isTrue();
        softly.assertThat(falseResult).as("Not created, Exists").isFalse();

        softly.assertAll();
    }

    @Test
    public void testInsertTwoDifferent() {
        SoftAssertions softly = new SoftAssertions();
        var input1 = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
        var input2 = new ProjectReq("Sec Project", Set.of("tag 1a", "tag 2a", "tag 4a"), "some reward 2", "some icon 2");

        var trueResult = service.createProject(0, input1);

        var secondResult = service.createProject(0, input2);

        softly.assertThat(trueResult).as("Created").isTrue();
        softly.assertThat(secondResult).as("Created").isTrue();
        softly.assertThat(PRepo.count()).as("2 should be").isEqualTo(2);
        softly.assertThat(TRepo.count()).as("5 should be").isEqualTo(5);

        softly.assertAll();
    }

    @Test
    public void testInsertDifferentWithSameTags() {
        SoftAssertions softly = new SoftAssertions();
        var input1 = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
        var input2 = new ProjectReq("Sec Project", Set.of("tag 1", "tag 2", "tag 4a"), "some reward 2", "some icon 2");

        var trueResult = service.createProject(0, input1);

        var secondResult = service.createProject(0, input2);

        softly.assertThat(trueResult).as("Created").isTrue();
        softly.assertThat(secondResult).as("Created").isTrue();
        softly.assertThat(PRepo.count()).as("2 should be").isEqualTo(2);
        softly.assertThat(TRepo.count()).as("5 should be").isEqualTo(3);

        softly.assertAll();
    }

    @Test
    public void testCreateUsers() {
        var user1 = new UserReq("Worker");
        var user2 = new UserReq("Shmorker");

        SoftAssertions softly = new SoftAssertions();

        var resultOK = service.createUser(user2);
        var resultOK2 = service.createUser(user1);

        softly.assertThat(resultOK).isTrue();
        softly.assertThat(resultOK2).isTrue();

        var resultFail = service.createUser(user2);

        softly.assertThat(resultFail).isFalse();

        var correctID = service.findUserID("Worker");
        var incorrectID = service.findUserID("Jenny");

        softly.assertThat(correctID.get()).isEqualTo(URepo.findByName("Worker").map(UserEntity::getId).get());
        softly.assertThat(incorrectID).isEmpty();

        var resultList = em.createNativeQuery("SCRIPT TO 'backup.txt'").getResultList();
        System.out.println(resultList.toString());

        softly.assertAll();

    }
    
     @Test
    public void testCreateUsersAndProjects() {
        var user1 = new UserReq("Worker");
        //var user2 = new UserReq("Shmorker");
        
        var project1 = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
        var project2 = new ProjectReq("Sec Project", Set.of("tag 1a", "tag 2a", "tag 4a"), "some reward 2", "some icon 2");
        
        SoftAssertions softly = new SoftAssertions();

        //var resultOK = service.createUser(user2);
        var resultOK2 = service.createUser(user1);
        var user = URepo.findAll().iterator().next();
        user.addProject(new ProjectEntity(project1)).addProject(new ProjectEntity(project2));
        URepo.save(user);
       // softly.assertThat(resultOK).isTrue();
        softly.assertThat(resultOK2).isTrue();

       // var resultFail = service.createUser(user2);

      //  softly.assertThat(resultFail).isFalse();

       // var correctID = service.findUserID("Worker");
        
        System.out.println(URepo.count());
        
        System.out.println(util.dumpDB("delete.me"));
    }
}
