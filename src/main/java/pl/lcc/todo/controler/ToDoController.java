/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.lcc.todo.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lcc.todo.db.RepoService;
import pl.lcc.todo.entities.EventDTO;
import pl.lcc.todo.entities.ProjectDTO;
import pl.lcc.todo.entities.UserDTO;
import pl.lcc.todo.entities.ProjectEntity;
import pl.lcc.todo.entities.ProjectReq;
import pl.lcc.todo.entities.UserReq;
import pl.lcc.todo.entities.UserEntity;

/**
 *
 * @author Nauczyciel
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ToDoController {

RepoService repos;
    
StopWatch timeMeasure;

    public ToDoController(RepoService repos) {
        this.repos = repos;
        timeMeasure = new StopWatch();
        log.info("ToDoController Constructor");
        
    }

    @PostMapping(value = "/projectX")
    ResponseEntity<String> createProject(@RequestBody ProjectReq req) {
        timeMeasure.start();
        ResponseEntity<String> result;
        log.info("project: " + req);
        if (repos.createProject(1, req).isEmpty()) {
            result = ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            result = ResponseEntity.status(HttpStatus.CONFLICT).body("Project with this name alreadye exist");
        }
        timeMeasure.stop();
        log.info("Project created in " + timeMeasure.getLastTaskTimeMillis() + "ms");
        return result;
    }

    @GetMapping(value = "/task")
    String getTask() {
        log.info("task get");
        return "Tasks";
    }

     @PostMapping(value = "/task")
    String createTask() {
        log.info("task created");
        return "Not Implemented yet - post Task";
    }

    @PostMapping(value = "/event")
    String createEvent() {
        log.info("event created");
        return "Not Implemented yet - post Event";
    }
    
    @PostMapping(value = "/user")
    ResponseEntity<Long> createUser(@RequestBody UserReq user){
       return repos.createUser(user)
               .map(UserEntity::getId)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping(value = "/user/{id}")
    ResponseEntity<UserDTO> getUser(@PathVariable long id){
        return repos.findUserWithProjects(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping(value = "/project/{userId}/{projectId}")
     ResponseEntity<ProjectDTO> getProject(@PathVariable long userId, @PathVariable long projectId){
         return repos.getProject(userId, projectId)
                  .map(ResponseEntity::ok)
                  .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
     }
     
    @GetMapping(value = "/event/{userId}/{eventId}")
    ResponseEntity<EventDTO> getEvent(@PathVariable long userId, @PathVariable long eventId) {
        
        return repos.getEvent(userId, eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
