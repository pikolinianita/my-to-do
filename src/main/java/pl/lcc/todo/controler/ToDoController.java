/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.lcc.todo.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nauczyciel
 */
@RestController
@RequestMapping("/api")
public class ToDoController {
    
    @PostMapping
   
    String createProject(){
        return "post Project";
    }
    
    @PostMapping
    
    String createTask(){
        return "post Task";
    }
    
    @PostMapping
    
    String createEvent(){
        return "post Event";
    }
    
    @GetMapping
    
    String getProject(){
        return "Projects";
    }
    
    @GetMapping
    
    String getTask(){
        return "Tasks";
    }
    
    @GetMapping
         
    String getEvent(){
        return "['Events']";
    }
}

//fetch('https://jsonplaceholder.typicode.com/posts',{method: 'POST', headers: {'test': 'TestPost'} })
//  .then(response => response.json())
//  .then(json => console.log(json))