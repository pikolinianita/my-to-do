/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.lcc.todo.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lcc.todo.db.RepoService;
import pl.lcc.todo.entities.EventDTO;
import pl.lcc.todo.entities.EventEntity;
import pl.lcc.todo.entities.EventReq;
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
@CrossOrigin
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
    ResponseEntity<String> createProjectX(@RequestBody ProjectReq req) {
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

    @DeleteMapping(value = "/project/{userId}/{projectId}")
    ResponseEntity<Long> deleteProject(@PathVariable long userId, @PathVariable long projectId){
        return repos.removeProject(userId, projectId) ?               
               ResponseEntity.ok(projectId) :
               ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @DeleteMapping(value = "/user/{userId}")
    ResponseEntity<Long> deleteUser(@PathVariable long userId){
        return repos.removeUser(userId) ?               
               ResponseEntity.ok(userId) :
               ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
   
    @DeleteMapping(value = "/event/{userId}/{projectId}/{eventId}")
    ResponseEntity<Long> deleteEvent(@PathVariable long userId,@PathVariable long projectId,@PathVariable long eventId){
        return repos.deleteEvent(userId, projectId, eventId)
                .map(EventEntity::getId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(value = "/event/{userId}/{projectId}")
    ResponseEntity<Long> createEvent(@PathVariable long userId,@PathVariable long projectId, @RequestBody EventReq eventReq) {
        
        return repos.createEvent(userId, projectId, eventReq)
                .map(EventEntity::getId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @PostMapping(value = "/project/{id}")
    ResponseEntity<Long> createProject(@PathVariable long id, @RequestBody ProjectReq project){
        
        return repos.createProject(id, project)
                .map(ProjectEntity::getId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
    
    @GetMapping(value = "/user/name/{name}")
    ResponseEntity<UserDTO> getUser(@PathVariable String name){
        return repos.findUserWithProjects(name)
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
