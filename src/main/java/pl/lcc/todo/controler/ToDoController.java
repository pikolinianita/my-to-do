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
import pl.lcc.todo.entities.ProjectDTO;
import pl.lcc.todo.entities.ProjectEntity;
import pl.lcc.todo.entities.ProjectReq;

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
        System.out.println("Controller Constructor");
        
    }

    @PostMapping(value = "/project")
    ResponseEntity<?> createProject(@RequestBody ProjectReq req) {
        timeMeasure.start();
        ResponseEntity<?> result;
        log.info("project: " + req);
        if (repos.createProject(1, req).isEmpty()) {
            result = ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            result = ResponseEntity.status(HttpStatus.CONFLICT).body("Project with this name alreadye exist");
        }
        timeMeasure.stop();
        System.out.println(result);
        System.out.println("project created in " + timeMeasure.getLastTaskTimeMillis() + "ms");
        return result;
    }

    @PostMapping(value = "/task")
    String createTask() {
        log.info("task created");
        return "post Task";
    }

    @PostMapping(value = "/event")
    String createEvent() {
        log.info("event created");
        return "post Event";
    }

    @GetMapping(value = "/project/{name}")
    ResponseEntity<ProjectEntity> getProject(@PathVariable String name) {
        timeMeasure.start();
        log.info("project get" +  name);
        
        //TODO getProjectByName
        
        var result = repos.getProjectByName(0, name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        log.info(result.toString());
        timeMeasure.stop();
        System.out.println("project retrieved in " + timeMeasure.getLastTaskTimeMillis() + "ms");
        return result;
    }

    @GetMapping(value = "/task")
    String getTask() {
        log.info("task get");
        return "Tasks";
    }

    @GetMapping(value = "/event")
    String getEvent() {
        log.info("event get");
        return "['Events']";
    }
}

//record TestR(String name) {}
