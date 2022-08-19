package pl.lcc.todo.controler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lcc.todo.entities.EventReq;
import pl.lcc.todo.entities.ProjectReq;
import pl.lcc.todo.entities.UserReq;

/**
 *
 * @author piko
 */
@Slf4j
@RestController
@RequestMapping("/")
public class InfoController {

    @GetMapping("/objects")
    Map <String, Object> getObjectsInfo() {
        return Map.of(
                "user", new UserReq("Killer"),
                "project", new ProjectReq("Waski", Set.of(), "some reward", "some icon"),
                "event", new EventReq("Boom", LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1), "some remark"),
                "user dto", "will show",
                "project dto", "will show",
                "event dto", "will show"
        );
    }
    
    @GetMapping("/ep")
    String getEndpoints(){
        return """
               @DeleteMapping(value = "/project/{userId}/{projectId}")
                   
                @DeleteMapping(value = "/user/{userId}")
                  
                  
                @DeleteMapping(value = "/event/{userId}/{projectId}/{eventId}")
                 
               
                @PostMapping(value = "/event/{userId}/{projectId}")
                  
                   
                @PostMapping(value = "/project/{userId}")
                   
                   
               @PostMapping(value = "/user")
                   
                   
                @GetMapping(value = "/user/{id}")
                 
                   
                @GetMapping(value = "/user/name/{name}")
                  
                   
               @GetMapping(value = "/project/{userId}/{projectId}")
                   
                    
                @GetMapping(value = "/event/{userId}/{eventId}")
               
               """;
    }
    
}
