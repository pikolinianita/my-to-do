/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.lcc.todo.db;

import java.util.Set;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pl.lcc.todo.entities.ProjectReq;

/**
 *
 * @author piko
 */
@DataJpaTest
@Import(RepoService.class)
public class RepoServiceTest {
    
    @Autowired
    private ProjectRepository PRepo;
    
    @Autowired
    private TagRepository TRepo;
    
    @Autowired
    private RepoService service;

    @Test
    public void testInsertTwice() {
       SoftAssertions softly = new SoftAssertions();
       var input = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
       
       var trueResult = service.createProject(input);
       
       var falseResult = service.createProject(input);

       softly.assertThat(trueResult).as("Created").isTrue();
       softly.assertThat(falseResult).as("Not created, Exists").isFalse();
       
       softly.assertAll();
    }
        
    @Test
    public void testInsertTwoDifferent() {
       SoftAssertions softly = new SoftAssertions();
       var input1 = new ProjectReq("First Project", Set.of("tag 1", "tag 2"), "some reward", "some icon");
       var input2 = new ProjectReq("Sec Project", Set.of("tag 1a", "tag 2a", "tag 4a"), "some reward 2", "some icon 2");
       
       var trueResult = service.createProject(input1);
       
       var secondResult = service.createProject(input2);

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
       
       var trueResult = service.createProject(input1);
       
       var secondResult = service.createProject(input2);

       softly.assertThat(trueResult).as("Created").isTrue();
       softly.assertThat(secondResult).as("Created").isTrue();
       softly.assertThat(PRepo.count()).as("2 should be").isEqualTo(2);
       softly.assertThat(TRepo.count()).as("5 should be").isEqualTo(3);
       
       softly.assertAll();
    }
    
}
