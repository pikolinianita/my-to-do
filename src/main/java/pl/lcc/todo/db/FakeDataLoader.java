
package pl.lcc.todo.db;

import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lcc.todo.entities.EventReq;
import pl.lcc.todo.entities.ProjectReq;
import pl.lcc.todo.entities.UserReq;

/**
 *
 * @author piko
 */
@Component
public class FakeDataLoader implements CommandLineRunner{

    private RepoService service;

    public FakeDataLoader(RepoService service) {
        this.service = service;
    }
    
    @Override
    public void run(String... args) throws Exception {
        var user = service.createUser(new UserReq("Worker")).orElseThrow();
        var unusedUser = service.createUser(new UserReq("Lazy Guy")).orElseThrow();

        var project1 = service.createProject(user.getId(), new ProjectReq("Program", Set.of(), "It reward", "It icon")).orElseThrow();
        var project2 = service.createProject(user.getId(), new ProjectReq("Read", Set.of(), "Book reward", "Book icon")).orElseThrow();
        var project3 = service.createProject(user.getId(), new ProjectReq("Not Started", Set.of(), "No Rewards", "? icon")).orElseThrow();

        service.createEvent(user.getId(), project1.getId(), new EventReq("Back End", LocalDateTime.now().minusHours(4), LocalDateTime.now().minusHours(3), "Java"));
        service.createEvent(user.getId(), project1.getId(), new EventReq("Front End", LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2), "JS"));
        service.createEvent(user.getId(), project1.getId(), new EventReq("Testing", LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1), "success"));
        service.createEvent(user.getId(), project2.getId(), new EventReq("SICP", LocalDateTime.now().minusHours(6), LocalDateTime.now().minusHours(5), "chapter 5"));
        service.createEvent(user.getId(), project2.getId(), new EventReq("Cook book", LocalDateTime.now().minusHours(1), LocalDateTime.now(), "yummy"));
    }

}
