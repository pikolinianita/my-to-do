/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.lcc.todo.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lcc.todo.db.RepoService;
import pl.lcc.todo.entities.ProjectDTO;
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

    public ToDoController(RepoService repos) {
        this.repos = repos;
        System.out.println("Controller Constructor");
        System.out.println(repos);
    }

    @PostMapping(value = "/project")
    boolean createProject(@RequestBody ProjectReq req) {
        log.info("project created: " + req);
        var result = repos.createProject(req);
        System.out.println(result);
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
    ProjectDTO getProject(@PathVariable String name) {

        log.info("project get");
        var result = new ProjectDTO (repos.getProjectByName(name));
        log.info(result.toString());
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

record TestR(String name) {

};
