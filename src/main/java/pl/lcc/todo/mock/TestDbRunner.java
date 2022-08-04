/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.lcc.todo.mock;

import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lcc.todo.db.RepoService;
import pl.lcc.todo.entities.EventReq;
import pl.lcc.todo.entities.ProjectReq;
import pl.lcc.todo.entities.UserReq;

/**
 *
 * @author Nauczyciel
 */
@Component
public class TestDbRunner implements CommandLineRunner {

    @Autowired
    private RepoService service;

    @Override
    public void run(String... args) throws Exception {
        var user = service.createUser(new UserReq("Worker")).orElseThrow();
        System.out.println(user);
        var project1 = service.createProject(user.getId(), new ProjectReq("Program", Set.of(), "It reward", "It icon")).orElseThrow();
        var project2 = service.createProject(user.getId(), new ProjectReq("Read", Set.of(), "Book reward", "Book icon")).orElseThrow();
        System.out.println(project1);
        System.out.println(project2);
        service.createEvent(user.getId(), project1.getId(), new EventReq("Back End", LocalDateTime.now().minusHours(4), LocalDateTime.now().minusHours(3), "Java"));
        service.createEvent(user.getId(), project1.getId(), new EventReq("Front End", LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2), "JS"));
        service.createEvent(user.getId(), project1.getId(), new EventReq("Testing", LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1), "success"));
        service.createEvent(user.getId(), project2.getId(), new EventReq("SICP", LocalDateTime.now().minusHours(6), LocalDateTime.now().minusHours(5), "chapter 5"));
        service.createEvent(user.getId(), project2.getId(), new EventReq("Cook book", LocalDateTime.now().minusHours(1), LocalDateTime.now(), "yummy"));
    }

}
