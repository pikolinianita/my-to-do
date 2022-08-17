/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.lcc.todo.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import pl.lcc.todo.entities.UserReq;
import static org.assertj.core.api.Assertions.assertThat;
import pl.lcc.todo.entities.EventReq;
import pl.lcc.todo.entities.ProjectReq;

/**
 *
 * @author piko
 */
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void happyTest() throws Exception {

        final var NAME = "Aha";
        final var PROJ1NAME = "IT Stuff";
        final var PROJ2NAME = "Food Stuff";
        final var EVENT1NAME = "Javving";
        final var EVENT2NAME = "Clojuring";
        final var EVENT3NAME = "Baking";
        final var EVENT4NAME = "Simmering";

    //create User and get it  
        var userPostRequesrBody = objectMapper.writeValueAsString(new UserReq(NAME));

        var projectPostRequestBody1 = objectMapper.writeValueAsString(
                new ProjectReq(PROJ1NAME, Set.of(), "some reward", "some icon"));
        var projectPostRequestBody2 = objectMapper.writeValueAsString(
                new ProjectReq(PROJ2NAME, Set.of(), "some food reward", "food icon"));

        var eventPostRequestBody1 = objectMapper.writeValueAsString(
                new EventReq(EVENT1NAME, LocalDateTime.now().minusHours(4), LocalDateTime.now().minusHours(3), "Java"));
        var eventPostRequestBody2 = objectMapper.writeValueAsString(
                new EventReq(EVENT2NAME, LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2), "JS"));
        var eventPostRequestBody3 = objectMapper.writeValueAsString(
                new EventReq(EVENT3NAME, LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1), "success"));
        var eventPostRequestBody4 = objectMapper.writeValueAsString(
                new EventReq(EVENT4NAME, LocalDateTime.now().minusHours(6), LocalDateTime.now().minusHours(5), "chapter 5"));

        var resultUserPost = mockMvc.perform(post("/api/user")
                .contentType("application/json")
                .content(userPostRequesrBody))
                .andExpect(status().isOk()).andReturn();

        var userIdAsString = resultUserPost.getResponse().getContentAsString();

        var resultStringGet1 = mockMvc.perform(get("/api/user/" + userIdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(resultStringGet1).as("should be empty user with name" + NAME)
                .contains(NAME);

    //create Two Projects and get them
        var resultProject1Post = mockMvc.perform(post("/api/project/" + userIdAsString)
                .contentType("application/json")
                .content(projectPostRequestBody1))
                .andExpect(status().isOk()).andReturn();

        var resultProject2Post = mockMvc.perform(post("/api/project/" + userIdAsString)
                .contentType("application/json")
                .content(projectPostRequestBody2))
                .andExpect(status().isOk()).andReturn();

        var project1IdAsString = resultProject1Post.getResponse().getContentAsString();
        var project2IdAsString = resultProject2Post.getResponse().getContentAsString();

        var resultStringGetProject1 = mockMvc.perform(get("/api/project/" + userIdAsString + "/" + project1IdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resultStringGetProject2 = mockMvc.perform(get("/api/project/" + userIdAsString + "/" + project2IdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resultStringGet2 = mockMvc.perform(get("/api/user/" + userIdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        assertThat(resultStringGetProject1).as("it project ").contains(PROJ1NAME);        
        assertThat(resultStringGetProject2).as("cooking project ").contains(PROJ2NAME);
        
        assertThat(resultStringGet2).as("should be user with name: " + NAME + " and two projects")
                .contains(NAME).contains(PROJ1NAME).contains(PROJ2NAME);
        
    //add two events to each and get them
        
         var resultEvent1Post = mockMvc.perform(post("/api/event/" + userIdAsString + "/" +  project1IdAsString)
                .contentType("application/json")
                .content(eventPostRequestBody1))
                .andExpect(status().isOk()).andReturn();
         
         var resultEvent2Post = mockMvc.perform(post("/api/event/" + userIdAsString + "/" +  project1IdAsString)
                .contentType("application/json")
                .content(eventPostRequestBody2))
                .andExpect(status().isOk()).andReturn();
         
         var resultEvent3Post = mockMvc.perform(post("/api/event/" + userIdAsString + "/" +  project2IdAsString)
                .contentType("application/json")
                .content(eventPostRequestBody3))
                .andExpect(status().isOk()).andReturn();
         
         var resultEvent4Post = mockMvc.perform(post("/api/event/" + userIdAsString + "/" +  project2IdAsString)
                .contentType("application/json")
                .content(eventPostRequestBody4))
                .andExpect(status().isOk()).andReturn();
        
        var event1IdAsString = resultEvent1Post.getResponse().getContentAsString();
        var event2IdAsString = resultEvent2Post.getResponse().getContentAsString();
        var event3IdAsString = resultEvent3Post.getResponse().getContentAsString();
        var event4IdAsString = resultEvent4Post.getResponse().getContentAsString();
        
        var resultStringGetEvent1 = mockMvc.perform(get("/api/event/" + userIdAsString + "/" + event1IdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
         var resultStringGetEvent2 = mockMvc.perform(get("/api/event/" + userIdAsString + "/" + event2IdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
         
        var resultStringGetEvent3 = mockMvc.perform(get("/api/event/" + userIdAsString + "/" + event3IdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
          
        var resultStringGetEvent4 = mockMvc.perform(get("/api/event/" + userIdAsString + "/" + event4IdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        var resultStringGetProjectWithEvent1 = mockMvc.perform(get("/api/project/" + userIdAsString + "/" + project1IdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        var resultStringGetProjectWithEvent2 = mockMvc.perform(get("/api/project/" + userIdAsString + "/" + project2IdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        assertThat(resultStringGetEvent1).contains(EVENT1NAME);
        assertThat(resultStringGetEvent2).contains(EVENT2NAME);
        assertThat(resultStringGetEvent3).contains(EVENT3NAME);
        assertThat(resultStringGetEvent4).contains(EVENT4NAME);
        
        assertThat(resultStringGetProjectWithEvent1).contains(EVENT1NAME).contains(EVENT2NAME).contains(PROJ1NAME);
        assertThat(resultStringGetProjectWithEvent2).contains(EVENT3NAME).contains(EVENT4NAME).contains(PROJ2NAME);
        
    //delete one task

         mockMvc.perform(delete("/api/event/" + userIdAsString + "/" + project1IdAsString + "/" + event1IdAsString)
                 .contentType("application/json"))
                .andExpect(status().isOk());
         
         mockMvc.perform(get("/api/event/" + userIdAsString + "/" + event1IdAsString)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
         
         var resultStringGetProjectAfterOneEventDeleted = mockMvc.perform(get("/api/project/" + userIdAsString + "/" + project1IdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
         
         assertThat(resultStringGetProjectAfterOneEventDeleted).doesNotContain(EVENT1NAME).contains(EVENT2NAME).contains(PROJ1NAME);
         
    //delete one project
         mockMvc.perform(delete("/api/project/" + userIdAsString + "/" + project2IdAsString)
                 .contentType("application/json"))
                .andExpect(status().isOk());
        
         mockMvc.perform(get("/api/project/" + userIdAsString + "/" + project2IdAsString)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
         
         mockMvc.perform(get("/api/event/" + userIdAsString + "/" + event3IdAsString)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
         
          var resultStringUserProjectDeleted = mockMvc.perform(get("/api/user/" + userIdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
          
          assertThat(resultStringUserProjectDeleted)
                  .contains(NAME).contains(PROJ1NAME).doesNotContain(PROJ2NAME);
          
    //delete user
        mockMvc.perform(delete("/api/user/" + userIdAsString)
                .contentType("application/json"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/user/" + userIdAsString)
                .contentType("application/json"))
                .andExpect(status().isNotFound());

         mockMvc.perform(get("/api/event/" + userIdAsString + "/" + event2IdAsString)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
         
         mockMvc.perform(get("/api/project/" + userIdAsString + "/" + project1IdAsString)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTest() throws Exception {

        mockMvc.perform(delete("/api/user/1")
                .contentType("application/json"))
                .andExpect(status().isOk());

    }
}
